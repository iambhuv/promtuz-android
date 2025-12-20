use jni::JNIEnv;
use jni::objects::JClass;
use jni::objects::JObject;
use log::debug;
use macros::jni;
use crate::events::CALLBACK;

#[jni(base = "com.promtuz.core", class = "API")]
pub extern "system" fn registerCallback(env: JNIEnv, _: JClass, callback: JObject) {
    let global_ref = env.new_global_ref(callback).unwrap();
    *CALLBACK.lock() = Some(global_ref);
    debug!("EVENTS: CALLBACK REGISTERED!");
}
