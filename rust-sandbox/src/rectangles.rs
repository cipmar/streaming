pub(crate) fn main() {
    let width1 = 29;
    let height1 = 33;

    println!(
        "The area of the rectangle is {} in square pixels.",
        area(width1, height1)
    );

    println!(
        "The area of the rectangle is {} in square pixels (using tuples).",
        area_with_tuples((width1, height1))
    );

    let rect = Rectangle {
        width: width1,
        height: height1
    };

    println!("The rect print in debug mode is {:?}", rect);
    println!("The rect print in pretty mode is {:#?}", rect);

    println!(
        "The area of the rectangle is {} in square pixels (using struct).",
        area_with_struct(&rect)
    );
}

fn area(width: u32, height: u32) -> u32 {
    width * height
}

fn area_with_tuples(dimensions: (u32, u32)) -> u32 {
    dimensions.0 * dimensions.1
}

fn area_with_struct(rect: &Rectangle) -> u32 {
    rect.width * rect.height
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}
