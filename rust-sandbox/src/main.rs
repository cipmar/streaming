mod rectangles;
mod socket_server;

fn main() {
    rectangles::main();
    socket_server::start();
}
