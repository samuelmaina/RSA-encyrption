package com.rsa;



import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        int numberOfbits= 1024;
        RSA rsa = new RSA(numberOfbits);

        String text = "Lorem ipsum dolor sit amet consectetur" +
                " adipisicing elit. Eligendi, necessitatibus" +
                " quis dignissimos maxime accusantium voluptate! " +
                "Asperiores excepturi, atque ea doloremque quo, consectetur " +
                "dolores aliquid error alias modi maxime mollitia consequatur.";

        ArrayList cipherText = rsa.encryptString(text);
        System.out.println("This is the plain text:" + rsa.decrypt(cipherText));
    }
}

