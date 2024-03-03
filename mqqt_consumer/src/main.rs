// mod thermometer_proto {
//     include!(concat!(env!("OUT_DIR"), "/protos/thermometer.rs"));
// }

mod message_processing;
mod mqtt_util;

const DEFAULT_BROKER: &str = "tcp://127.0.0.1:1883";
const DEFAULT_CLIENT: &str = "zigbee2mqqt";
const DEFAULT_TOPICS: &[&str] = &[
    "zigbee2mqtt/thermometer_bedroom",
    "zigbee2mqtt/thermometer_living",
    "zigbee2mqtt/thermometer_garden",
];

fn main() {
    let client = mqtt_util::connect_mqtt(DEFAULT_CLIENT, DEFAULT_BROKER);
    
    mqtt_util::consume_messages(
        &client,
        DEFAULT_TOPICS,
        message_processing::send_message_to_socket,
    );

    println!("Exiting...")
}
