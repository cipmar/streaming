use std::{io::Write, net::TcpStream, process};

use protobuf::Message;
use serde::Deserialize;
use serde_json::from_str;

use crate::proto::thermometer::ThermometerData as ThermometerDataProto;

#[derive(Deserialize)]
struct ThermometerData {
    temperature: f32,
    humidity: f32,
    pressure: f32,
    battery: f32,
    link_quality: f32,
    power_outage_count: i32,
    voltage: i32,
}

pub fn send_message_to_socket(msg: &paho_mqtt::Message) -> bool {
    match from_str::<ThermometerData>(&*msg.payload_str()) {
        Err(err) => println!("invalid json: {}", err),
        Ok(data) => {
            let mut proto_data = ThermometerDataProto::new();

            proto_data.temperature = data.temperature;
            proto_data.humidity = data.humidity;
            proto_data.pressure = data.pressure;
            proto_data.battery = data.battery;
            proto_data.link_quality = data.link_quality;
            proto_data.power_outage_count = data.power_outage_count;
            proto_data.voltage = data.voltage;

            let result = proto_data.write_to_bytes().unwrap();

            match TcpStream::connect("127.0.0.1:34000") {
                Ok(mut tcp_stream) => match tcp_stream.write(&*result) {
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
        }
    }

    return false;
}
