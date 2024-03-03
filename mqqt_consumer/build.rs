fn main() {

    protobuf_codegen::Codegen::new()
        .include("src/protos")
        .input("src/protos/thermometer.proto")
        .cargo_out_dir("protos")
        .run_from_script();
}