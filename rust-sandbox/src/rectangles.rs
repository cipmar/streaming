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

    let rect1 = Rectangle {
        width: width1,
        height: height1
    };

    println!("The area of the rectangle is {} in square pixels (using methods).", rect1.area());

    let rect2 = Rectangle {
        width: 100,
        height: 55
    };

    println!("{}", rect2.can_hold(&rect1));

    let square = Rectangle::square(33);
    println!("This is a squaare of size {}", square.width);
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

impl Rectangle {
    fn area(&self) -> u32 {
        self.width * self.height
    }

    fn can_hold(&self, other: &Rectangle) -> bool {
        self.width > other.width && self.height > other.height
    }

    fn square(size: u32) -> Self {
        Self {
            width: size,
            height: size
        }
    }
}
