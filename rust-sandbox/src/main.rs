// file:///Users/mariusropotica/.rustup/toolchains/stable-aarch64-apple-darwin/share/doc/rust/html/book/ch06-00-enums.html

mod rectangles;
mod socket_server;
mod enums;

fn main() {

    rectangles::main();
    enums::main();
    socket_server::start();
}
