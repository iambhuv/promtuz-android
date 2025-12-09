use std::net::ToSocketAddrs;

use anyhow::Result;
use anyhow::anyhow;
use quinn::Connection;

use crate::data::ResolverSeed;

pub async fn connect_to_any_seed(
    endpoint: &quinn::Endpoint, seeds: &[ResolverSeed],
) -> Result<Connection> {
    // Collect errors to show if everything fails
    let mut last_err: Option<anyhow::Error> = None;

    for seed in seeds {
        let url = format!("{}:{}", seed.host, seed.port);

        // Resolve URL → socket addresses
        let addrs = match url.to_socket_addrs() {
            Ok(a) => a,
            _ => {
                last_err = Some(anyhow!("failed to resolve seed: {}", url));
                continue;
            },
        };

        // Try each resolved IP for this seed
        for addr in addrs {
            println!("RESOLVER: Trying to connect: {} ({})", url, addr);
            match endpoint.connect(addr, &seed.id.to_string())?.await {
                Ok(conn) => {
                    println!("RESOLVER: Connected to: {} ({})", url, addr);
                    return Ok(conn);
                },
                Err(err) => {
                    println!("ERROR: Failed to connect {} ({:?}): {}", url, addr, err);
                    last_err = Some(err.into());
                },
            }
        }
    }

    Err(last_err.unwrap_or_else(|| anyhow!("no resolver seed succeeded")))
}
