//! ## Creating JNI ready external functions
//!
//!
//! for eg.
//! `getStaticKey` method on class `Crypto` at package `com.promtuz.core`
//! will have name `Java_com_promtuz_core_Crypto_getStaticKey``
//!
//! ```rs
//! #[jni(base = "com.promtuz.core", class = "Crypto")]
//! pub extern "system" fn getStaticKey(
//!   mut env: JNIEnv,
//!   _class: JClass,
//!   // Further Arguments
//! ) {
//!   // Body
//! }
//! ```

use common::msg::cbor::FromCbor;
use common::msg::cbor::ToCbor;
use common::msg::client::ClientRequest;
use common::msg::client::ClientResponse;
use jni::JNIEnv;
use jni::objects::JClass;
use jni::objects::JObject;
use log::info;
use macros::jni;
use once_cell::sync::Lazy;
use once_cell::sync::OnceCell;
use quinn::Connection;
use quinn::Endpoint;
use quinn::EndpointConfig;
use quinn::default_runtime;
use tokio::runtime::Runtime;

use crate::data::ResolverSeeds;
use crate::quic::dialer::connect_to_any_seed;
use crate::utils::ujni::get_package_name;
use crate::utils::ujni::get_raw_res_id;
use crate::utils::ujni::read_raw_res;

mod crypto;
mod data;
mod quic;
mod utils;

type JE<'local> = JNIEnv<'local>;
type JC<'local> = JClass<'local>;

/// Global Tokio Runtime
pub static RUNTIME: Lazy<Runtime> = Lazy::new(|| Runtime::new().unwrap());

pub static ENDPOINT: OnceCell<Endpoint> = OnceCell::new();

/// Connection to any relay server
pub static CONNECTION: OnceCell<Connection> = OnceCell::new();

/// Entry point for API
///
/// Initializes Endpoint
#[jni(base = "com.promtuz.core", class = "API")]
pub extern "system" fn initApi(_: JE, _: JC) {
    info!("API: INIT START");

    let rt = RUNTIME.handle().clone();
    let _guard = rt.enter();

    let socket = std::net::UdpSocket::bind("0.0.0.0:0").unwrap();

    let endpoint =
        Endpoint::new(EndpointConfig::default(), None, socket, default_runtime().unwrap()).unwrap();

    if let Ok(addr) = endpoint.local_addr() {
        info!("API: ENDPOINT BIND TO {}", addr);
    }

    ENDPOINT.set(endpoint).expect("init was ran twice");
}

/// Resolves Relays
///
/// Need Resolver Seeds
#[jni(base = "com.promtuz.core", class = "API")]
pub extern "system" fn resolve(mut env: JNIEnv, _: JC, context: JObject) {
    info!("API: RESOLVING");

    let package_name: String = jni_try!(env, get_package_name(&mut env, &context));
    info!("API: PACKAGE {}", package_name);

    let res_id = jni_try!(env, get_raw_res_id(&mut env, &context, "resolver_seeds"));
    info!("API: SEEDS RES ID : {:?}", res_id);
    let seeds = jni_try!(env, read_raw_res(&mut env, &context, res_id));

    let seeds = jni_try!(env, serde_json::from_slice::<ResolverSeeds>(&seeds)).seeds;

    RUNTIME.spawn(async move {
        // SET STATE = RESOLVING

        let conn = match connect_to_any_seed(ENDPOINT.get().unwrap(), &seeds).await {
            Ok(conn) => conn,
            Err(err) => {
                // SET STATE = FAILED
                info!("API: RESOLVER FAILED - {}", err);
                return;
            },
        };

        let req = ClientRequest::GetRelays().to_cbor().unwrap();

        if let Ok((mut send, mut recv)) = conn.open_bi().await {
            _ = send.write_all(&req).await;

            use CRes::*;
            use ClientResponse as CRes;

            if let Ok(res) = recv.read_to_end(64 * 1024).await
                && let Ok(GetRelays { relays: _ }) = CRes::from_cbor(&res)
            {
                /*
                  TODO: calls a func that updates entries of relays
                */
            }
        }
    });
}

#[jni(base = "com.promtuz.core", class = "Core")]
pub extern "system" fn initLogger(_: JE, _: JC) {
    android_logger::init_once(
        android_logger::Config::default().with_max_level(log::LevelFilter::Debug).with_tag("core"),
    );
}
