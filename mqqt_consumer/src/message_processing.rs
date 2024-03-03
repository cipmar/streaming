use std::{io::Write, net::TcpStream, process};

use paho_mqtt::Message;

pub fn send_message_to_socket(msg: &Message) -> bool {
    match TcpStream::connect("127.0.0.1:34000") {
        Ok(mut tcp_stream) => match tcp_stream.write(msg.payload_str().as_bytes()) {
            Ok(bytes_written) => {
                println!(
                    "Message successfully received and sent to socket, {:?} bytes written",
                    bytes_written
                );
                return true;
            }
            Err(err) => {
                println!("Error writing to the socket: {:?}", err);
                process::exit(1);
            }
        },
        Err(err) => {
            println!("Error connecting to the socket. Error: {}.", err);
        }
    }

    return false;
}
