package spi;

import java.util.ServiceLoader;
//https://mp.weixin.qq.com/s/vpy5DJ-hhn0iOyp747oL5A
public class SPIDemo {
    public static void main(String[] args){
        ServiceLoader<HelloSPI> serviceLoader = ServiceLoader.load(HelloSPI.class);
        for(HelloSPI helloSPI : serviceLoader){
            helloSPI.sayHello();
        }
    }
}
