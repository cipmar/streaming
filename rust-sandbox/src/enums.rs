enum IpAddrKind {
    V4,
    V6
}

struct IpAddr {
    kind: IpAddrKind,
    address: String
}

enum IpAddrEnhanced {
    V4(String),
    V6(String),
}

#[derive(Debug)]
enum IpAddrEvenBetter {
    V4(u8, u8, u8, u8),
    V6(String),
}

#[derive(Debug)]
enum Message {
    Quit,                       // has no data associated with it
    Move { x: i32, y: i32},     // has named fields, like structs
    Write(String),              // includes a single String
    ChangeColor(i32, i32, i32), // includes 3 integers
}

impl Message {
    fn call(&self) {
        println!("message from: {:?}", self);
    }
}

pub(crate) fn main() {
    let _four = IpAddrKind::V4;
    let _six = IpAddrKind::V6;

    let _home = IpAddr {
        kind: IpAddrKind::V4,
        address: String::from("127.0.0.1")
    };

    let _loopback = IpAddr {
        kind: IpAddrKind::V6,
        address: String::from("::1"),
    };

    let _home = IpAddrEnhanced::V4(String::from("127.0.0.1"));
    let _loopback = IpAddrEnhanced::V6(String::from("::1"));

    let home = IpAddrEvenBetter::V4(127, 0, 0, 1);
    let loopback = IpAddrEvenBetter::V6(String::from("::1"));

    println!("home address {:?}", home);
    println!("loopback address {:?}", loopback);

    let m1 = Message::Quit;
    m1.call();

    let m2 = Message::Move { x: 1, y: 2 };
    m2.call();

    let m3 = Message::ChangeColor(233, 233, 177);
    m3.call();

    let m4 = Message::Write(String::from("hello"));
    m4.call();
}
