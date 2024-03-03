use paho_mqtt as mqtt;
use std::{process, thread, time::Duration};
use mqtt::{Client, ConnectOptionsBuilder, Message};

pub fn connect_mqtt(clinet_name: &str, broker_url: &str) -> Client {

    let create_opts = mqtt::CreateOptionsBuilder::new()
        .server_uri(broker_url)
        .client_id(clinet_name)
        .finalize();

    let client = mqtt::Client::new(create_opts).unwrap_or_else(|err| {
        println!("Error creating the client {:?}", err);
        process::exit(1);
    });

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
        println!("Unable to connect to mqtt broker: {:?}", err);
        process::exit(1);
    }

    client

}

pub fn consume_messages<F>(client: &Client, topics: &[&str], process_message: F) where F: Fn(&Message) -> bool {

    let receiver = client.start_consuming();
    subscribe_topics(&client, topics);

    println!("Receiving messages...");

    for msq in receiver.iter() {
        if let Some(msg) = msq {
            retry(&process_message, &msg, 10);
        } else if !client.is_connected() {
            if try_reconnect(&client) {
                println!("Resubscribe to topics...");
                subscribe_topics(&client, topics);
            } else {
                break;
            }
        }
    }

    if client.is_connected() {
        println!("Disconnecting...");
        client.unsubscribe_many(topics).unwrap();
        client.disconnect(None).unwrap();
    }
}

fn retry<F>(process_message: F, msg: &Message, retries: u8) where F: Fn(&Message) -> bool {

    let mut connected = false;

    for i in 1..retries {

        if process_message(msg) {
            connected = true;
            break;
        } else {
            println!("Retry {}", i)
        }

        thread::sleep(Duration::from_secs(6));
    }

    if !connected {
        println!("Failed to write the message to the socket. Dropping the following message: {:?}", msg.payload_str());
    }
}

fn subscribe_topics(client: &mqtt::Client, topics: &[&str]) {

    if let Err(err) = client.subscribe_many(topics, &[mqtt::QOS_0, mqtt::QOS_1]) {
        println!("Error subscribing to topics: {:?}", err);
        process::exit(1);
    }
}

fn try_reconnect(client: &Client) -> bool {

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