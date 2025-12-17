use std::net::IpAddr;
use std::net::SocketAddr;
use std::str::FromStr;
use std::time::Duration;

use log::info;
use quinn::ConnectionError;

use crate::ENDPOINT;
use crate::EVENT_BUS;
use crate::data::relay::Relay;
use crate::events::InternalEvent;
use crate::events::connection::ConnectionState;

pub enum RelayConnError {
    Continue,
    Error(anyhow::Error)
}

impl<E> From<E> for RelayConnError
where
    E: std::error::Error + Send + Sync + 'static,
{
    fn from(err: E) -> Self {
        RelayConnError::Error(err.into())
    }
}


impl Relay {
    pub async fn connect(&self) -> Result<(), RelayConnError> {
        let addr = SocketAddr::new(IpAddr::from_str(&self.host.clone())?, self.port);

        info!("RELAY({}): CONNECTING AT {}", self.id, addr);

        _ = EVENT_BUS.0.send(InternalEvent::Connection { state: ConnectionState::Connecting });

        // TODO: verifying if the host exists before trying udp based handshake
        // ping?

        match ENDPOINT.get().unwrap().connect(addr, &self.id)?.await {
            Ok(conn) => {
                _ = EVENT_BUS
                    .0
                    .send(InternalEvent::Connection { state: ConnectionState::Handshaking });

                let (mut send, mut recv) = conn.open_bi().await?;

                // STUCK

                /////////////////////////////////////////////
                //==============|| IMITATING ||============//
                /////////////////////////////////////////////

                tokio::time::sleep(Duration::from_millis(650)).await;

                _ = EVENT_BUS
                    .0
                    .send(InternalEvent::Connection { state: ConnectionState::Connected });

                info!("RELAY({}): CONNECTED", self.id);
            },
            Err(ConnectionError::TimedOut) => {
              // very probable that relay is not available now
              // tasks?
              // consider this relay "bad", basically downvote
              // return something like "Continue"
              _ = EVENT_BUS.0.send(InternalEvent::Connection { state: ConnectionState::Failed });

              _ = self.downvote().await;

              return Err(RelayConnError::Continue)
            },
            Err(err) => return Err(err.into())
        };

        Ok(())
    }
}
