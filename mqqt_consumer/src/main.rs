use paho_mqtt as mqtt;
use std::{io::Write, net::TcpStream, process, thread, time::Duration};
use mqtt::{Client, ConnectOptionsBuilder, Message};

const DEFAULT_BROKER:&str = "tcp://127.0.0.1:1883";
const DEFAULT_CLIENT:&str = "zigbee2mqqt";
const DEFAULT_TOPICS:&[&str] = &["zigbee2mqtt/thermometer_bedroom", "zigbee2mqtt/thermometer_living", "zigbee2mqtt/thermometer_garden"];

fn main() {

    let create_opts = mqtt::CreateOptionsBuilder::new()
        .server_uri(DEFAULT_BROKER)
        .client_id(DEFAULT_CLIENT)
        .finalize();

    let client = mqtt::Client::new(create_opts).unwrap_or_else(|err| {
        println!("Error creating the client {:?}", err);
        process::exit(1);
    });

    let receiver = client.start_consuming();

    let lwt = mqtt::MessageBuilder::new()
        .payload("This is the last will and testament if this clinet.
            Broker, please publish this message after I die!")
        .topic("lwt")
        .finalize();

    let conn_opts = ConnectOptionsBuilder::new()
        .keep_alive_interval(Duration::from_secs(20))
        .clean_session(false)
        .will_message(lwt)
        .finalize();

    if let Err(err) = client.connect(conn_opts) {
        println!("Unable to connect: {:?}", err);
        process::exit(1);
    }

    subscribe_topics(&client);

    println!("Receiving messages...");

    for msq in receiver.iter() {
        if let Some(msg) = msq {
            process_messages(&msg);
        } else if !client.is_connected() {
            if try_reconnect_mqtt(&client) {
                println!("Resubscribe to topics...");
                subscribe_topics(&client);
            } else {
                break;
            }
        }
    }

    if client.is_connected() {
        println!("Disconnecting...");
        client.unsubscribe_many(DEFAULT_TOPICS).unwrap();
        client.disconnect(None).unwrap();
    }

    println!("Exiting...")
}

fn subscribe_topics(cli: &mqtt::Client) {

    if let Err(err) = cli.subscribe_many(DEFAULT_TOPICS, &[mqtt::QOS_0, mqtt::QOS_1]) {
        println!("Error subscribing to topics: {:?}", err);
        process::exit(1);
    }
}

fn try_reconnect_mqtt(client: &Client) -> bool {

    let retries = 10;

    for i in 1..retries {

        thread::sleep(Duration::from_secs(10));
        println!("Connection lost, retrying to connect, try {:?}", i);

        if client.reconnect().is_ok() {
            println!("Successfully reconnecred.");
            return true;
        }
    }

    println!("Unable to reconnect after {:?} retries", retries);
    false
}

fn process_messages(msg: &Message) {

    let retries = 10;
    let mut connected = false;

    for i in 1..retries {

        match TcpStream::connect("127.0.0.1:34000") {
            Ok(mut tcp_stream) => {
                match tcp_stream.write(msg.payload_str().as_bytes()) {
                    Ok(bytes_written) => {
                        println!("Message successfully received and sent to socket, {:?} bytes written", bytes_written);
                        connected = true;
                        break;
                    },
                    Err(e) => {
                        println!("Error writing to the socket: {:?}", e);
                        process::exit(1);
                    }
                }
            },
            Err(err) => {
                println!("Error connecting to the socket. Error: {}. Retry {:?}", err, i);
            }
        }

        thread::sleep(Duration::from_secs(6));
    }

    if !connected {
        println!("Failed to write the message to the socket. Dropping the following message: {:?}", msg.payload_str());
    }
}
