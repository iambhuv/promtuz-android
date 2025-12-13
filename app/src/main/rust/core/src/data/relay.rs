use anyhow::Result;
use common::PROTOCOL_VERSION;
use common::msg::client::RelayDescriptor;
use rusqlite::Row;

use crate::db::NETWORK_DB;
use crate::utils::systime;

/// Local Database Representation of Relay
#[derive(Debug)]
pub struct Relay {
    pub id: String,
    pub host: String,
    pub port: u16,
    last_avg_latency: Option<u64>,
    last_seen: u64,
    last_connect: Option<u64>,
    last_version: u16,
}

/// TODO: Create unit testing for this
impl Relay {
    /// "Best" how?
    ///
    /// - Must match current version
    /// - Lowest last avg latency if exists
    /// - Lowest last seen
    /// - Lowest last connect if exists
    pub fn fetch_best() -> rusqlite::Result<Self> {
        let conn = NETWORK_DB.lock();

        conn.query_row(
            "SELECT * FROM relays 
                  WHERE last_version = ?1 
                  ORDER BY 
                      last_seen DESC, 
                      last_connect DESC, 
                      last_avg_latency ASC 
                  LIMIT 1",
            [PROTOCOL_VERSION],
            Self::from_row,
        )
    }

    fn from_row(row: &Row) -> rusqlite::Result<Self> {
        Ok(Self {
            id: row.get("id")?,
            host: row.get("host")?,
            port: row.get("port")?,
            last_avg_latency: row.get("last_avg_latency")?,
            last_seen: row.get("last_seen")?,
            last_connect: row.get("last_connect")?,
            last_version: row.get("last_version")?,
        })
    }

    pub fn refresh(relays: &[RelayDescriptor]) -> Result<u8> {
        let conn = NETWORK_DB.lock();

        let mut stmt = conn.prepare(
            "INSERT INTO relays (
                    id, host, port, last_seen, last_version
                 )
                 VALUES (?1, ?2, ?3, ?4, ?5)
                 ON CONFLICT(id) DO UPDATE SET
                    host         = excluded.host,
                    port         = excluded.port,
                    last_seen    = excluded.last_seen,
                    last_version = excluded.last_version",
        )?;

        relays.iter().for_each(|r| {
            _ = stmt.execute((
                r.id.to_string(),
                r.addr.ip().to_string(),
                r.addr.port(),
                systime().as_millis() as u64,
                PROTOCOL_VERSION,
            ));
        });

        Ok(0)
    }
}
