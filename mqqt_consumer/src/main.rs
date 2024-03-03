use paho_mqtt as mqtt;
use std::{io::Write, net::TcpStream, process};
use mqtt::Message;

mod mqtt_util;

const DEFAULT_BROKER:&str = "tcp://127.0.0.1:1883";
const DEFAULT_CLIENT:&str = "zigbee2mqqt";
const DEFAULT_TOPICS:&[&str] = &["zigbee2mqtt/thermometer_bedroom", "zigbee2mqtt/thermometer_living", "zigbee2mqtt/thermometer_garden"];

fn main() {

    let client = mqtt_util::connect_mqtt(DEFAULT_CLIENT, DEFAULT_BROKER);
    mqtt_util::consume_messages(&client, DEFAULT_TOPICS, send_message_to_socket);

    println!("Exiting...")
}

fn send_message_to_socket(msg: &Message) -> bool {
    
    match TcpStream::connect("127.0.0.1:34000") {
        Ok(mut tcp_stream) => {
            match tcp_stream.write(msg.payload_str().as_bytes()) {
                Ok(bytes_written) => {
                    println!("Message successfully received and sent to socket, {:?} bytes written", bytes_written);
                    return true;
                },
                Err(err) => {
                    println!("Error writing to the socket: {:?}", err);
                    process::exit(1);
                }
            }
        },
        Err(err) => {
            println!("Error connecting to the socket. Error: {}.", err);
        }
    }

    return false;
}
