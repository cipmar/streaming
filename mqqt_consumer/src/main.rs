// file:///Users/mariusropotica/.rustup/toolchains/stable-aarch64-apple-darwin/share/doc/rust/html/book/ch04-03-slices.html

use std::{env, process, thread, time::Duration};

extern crate paho_mqtt as mqtt;

const DEFAULT_BROKER:&str = "tcp://192.168.100.45:1883";
const DEFAULT_CLIENT:&str = "zigbee2mqtt";
const DEFAULT_TOPICS:&[&str] = &["zigbee2mqtt/thermometer_bedroom", 
    "zigbee2mqtt/thermometer_living", 
    "zigbee2mqtt/thermometer_garden", 
    "zigbee2mqtt/thermometer_childroom"
];

fn main() {

    let host = env::args().nth(1).unwrap_or_else(|| {
        DEFAULT_BROKER.to_string()
    });

    let create_opts = mqtt::CreateOptionsBuilder::new()
        .server_uri(host)
        .client_id(DEFAULT_CLIENT)
        .finalize();
    
     let cli = mqtt::Client::new(create_opts).unwrap_or_else(|err| {
        println!("Error creating the client: {:?}", err);
        process::exit(1);
     });

     let receiver = cli.start_consuming();

     let lwt = mqtt::MessageBuilder::new()
        .payload("This is the last will and testament of this clinet.
            Broker, please publish this message after I die!")
        .topic("test")
        .finalize();

     let conn_opts = mqtt::ConnectOptionsBuilder::new()
        .keep_alive_interval(Duration::from_secs(10))
        .clean_session(false)
        .will_message(lwt)
        .finalize();

    if let Err(e) = cli.connect(conn_opts) {
        println!("Unable to connect: {:?}", e);
        process::exit(1);
    }

    subscribe_topics(&cli);

    println!("Processing messages ...");

    for msg in receiver.iter() {
        if let Some(msg) = msg {
            println!("Received message: {}", msg);
        } else if !cli.is_connected() {
            if try_reconnect(&cli) {
                println!("Resubscribe to topics...");
                subscribe_topics(&cli);
            } else {
                break;
            }
        }
    }

    if cli.is_connected() {
        println!("Disconnecting...");
        cli.unsubscribe_many(DEFAULT_TOPICS).unwrap();
        cli.disconnect(None).unwrap();
    }

    println!("Exiting");
}

fn try_reconnect(cli: &mqtt::Client) -> bool {

    for i in 1..10 {
        thread::sleep(Duration::from_millis(10000));

        println!("Connection lost, retrying to connect, try {} ...", i);

        if cli.reconnect().is_ok() {
            println!("Successfully reconnecred!");
            return true;
        }
    }

    println!("Unable to recconect after multiple attempts");
    false
}

fn subscribe_topics(cli: &mqtt::Client) {

    if let Err(e) = cli.subscribe_many(DEFAULT_TOPICS, &[0, 1]) {
        println!("Error subscribe to topics: {:?}", e);
        process::exit(1);
    }
    
}
