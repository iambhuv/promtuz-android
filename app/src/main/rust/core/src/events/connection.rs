use serde::Serialize;

#[derive(Serialize, Debug, Clone, PartialEq, Eq)]
#[allow(unused)]
pub enum ConnectionState {
    Offline,
    Idle,
    Resolving,
    Connecting,
    Handshaking,
    Connected,
    Failed,
    Reconnecting,
}