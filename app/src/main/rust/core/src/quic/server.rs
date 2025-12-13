use std::net::IpAddr;
use std::net::SocketAddr;
use std::str::FromStr;

use anyhow::Result;
use anyhow::anyhow;
use log::info;

use crate::EVENT_BUS;
use crate::ENDPOINT;
use crate::data::relay::Relay;
use crate::events::InternalEvent;
use crate::events::connection::ConnectionState;
use crate::utils::has_internet;

impl Relay {
    pub async fn connect(&self) -> Result<()> {
        let addr = SocketAddr::new(IpAddr::from_str(&self.host.clone())?, self.port);

        info!("RELAY({}): CONNECTING AT {}", self.id, addr);

        // Checking Internet Connectivity
        if !has_internet() {
          _ = EVENT_BUS.0.send(InternalEvent::Connection { state: ConnectionState::NoInternet });
          return Err(anyhow!("No Internet"));
        }

        _ = EVENT_BUS.0.send(InternalEvent::Connection { state: ConnectionState::Connecting });

        let conn = ENDPOINT.get().unwrap().connect(addr, &self.id)?.await?;

        _ = EVENT_BUS.0.send(InternalEvent::Connection { state: ConnectionState::Handshaking });

        let (mut send, mut recv) = conn.open_bi().await?;

        // STUCK

        info!("RELAY({}): CONNECTED", self.id);

        Ok(())
    }
}
