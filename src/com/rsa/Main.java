package com.rsa;


import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        RSA rsa = new RSA();
        String text = "I love mathematics";
        ArrayList cipherText = rsa.encryptString(text);
        System.out.println("This is the ciphertext:" + cipherText);
        System.out.println("This is the plain text:" + rsa.decrypt(cipherText));
    }
}

