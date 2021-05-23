package spi;

public class ImageSPI implements  HelloSPI {
    @Override
    public void sayHello() {
        System.out.println("Image Hello");
    }
}
