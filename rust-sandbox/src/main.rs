// file:///Users/mariusropotica/.rustup/toolchains/stable-aarch64-apple-darwin/share/doc/rust/html/book/ch07-00-managing-growing-projects-with-packages-crates-and-modules.html
mod rectangles;
mod socket_server;
mod enums;
mod matches;

fn main() {

    rectangles::main();
    enums::main();
    matches::main();
    socket_server::start();
}
