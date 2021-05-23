package spi;

public class TextSPI implements HelloSPI {
    @Override
    public void sayHello() {
        System.out.println("text hello");
    }
}
