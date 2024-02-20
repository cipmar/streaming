use std::{io::Read, net::TcpListener, process};

pub(crate) fn start() {
    let tcp_listener = TcpListener::bind("127.0.0.1:34000").unwrap_or_else(|err| {
        println!("Error starting socket: {}", err);
        process::exit(1);
    });

    println!("Waiting for data at port {} ...", 34000);

    for stream in tcp_listener.incoming() {
        match stream {
            Ok(mut stream) => {                
                let mut str = String::new();
                stream.read_to_string(&mut str).unwrap_or_else(|err| {
                    println!("Error reading the stream: {}", err);
                    process::exit(1);
                });

                println!("Data received: {}", str);
            }
            Err(e) => println!("Error: {}", e),
        }
    }
}
