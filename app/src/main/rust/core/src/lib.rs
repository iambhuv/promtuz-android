//! ## Creating JNI ready external functions
//!
//!
//! for eg.
//! `getStaticKey` method on class `Core` at package `com.promtuz.rust`
//! will have name `Java_com_promtuz_rust_Core_getStaticKey``
//!
//! for eg.
//! ```rs
//! #[jni(base = "com.promtuz.rust", class = "Core")]
//! pub extern "C" fn getStaticKey(
//!   mut env: JNIEnv,
//!   _class: JClass,
//!   // Further Arguments
//! ) {
//!   // Body
//! }
//! ```

use jni::JNIEnv;
use jni::objects::{JClass};
use jni::sys::jobject;

// use crate::utils::get_pair_object;
// pub mod crypto;
// pub mod insecure;
// pub mod quic;
pub mod utils;

use macros::jni;

/// for eg.
///
/// ```kotlin
/// package com.promtuz.rust
///
/// class Core {
///  external fun getStaticKeypair(): Pair
/// }
/// ```
///
/// Java_com_promtuz_rust_Core_
#[jni(base = "com.promtuz.rust", class = "Core")]
pub extern "C" fn getStaticKeypair(
    mut env: JNIEnv, 
    _class: JClass
) -> jobject {
    todo!("fixing in next commit")
    // let (secret, public) = get_static_keypair();

    // let secret_bytes = secret.to_bytes();
    // let public_bytes = public.to_bytes();

    // let secret_jarray = env.byte_array_from_slice(&secret_bytes).unwrap();
    // let public_jarray = env.byte_array_from_slice(&public_bytes).unwrap();

    // get_pair_object(
    //     &mut env,
    //     JValue::Object(&JObject::from(secret_jarray)),
    //     JValue::Object(&JObject::from(public_jarray)),
    // )
}
