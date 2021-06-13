package com.rsa;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;


public class RSATest {
    @Test
    public void gcdShouldWork() {
        runGCDTest(24, 12, 12);
        runGCDTest(36, 42, 6);
        runGCDTest(13, 24, 1);
    }

    private void runGCDTest(int num1, int num2, int result) {
        Assert.assertEquals(RSA.gcd(BigInteger.valueOf(num1), BigInteger.valueOf(num2)), BigInteger.valueOf(result));
    }

    @Test
    public void shouldEncryptAndDecryptForSingleChars() {
        RSA rsa = new RSA();
        String message = "h";
        testRsa(rsa, message);
    }

    @Test
    public void shouldEncryptAndDecryptForLongString() {
        RSA rsa = new RSA();
        String message = "Lorem ipsum dolor sit amet " +
                "consectetur adipisicing elit. In sit modi" +
                " cumque ratione aliquam laudantium qui officiis" +
                " porro! Rem placeat ducimus est, illum quasi blanditiis. " +
                "Recusandae velit ad maiores expedita!";
        testRsa(rsa, message);
    }


    private void testRsa(RSA rsa, String message) {
        ArrayList encrypted = rsa.encryptString(message);
        String decrypted = rsa.decrypt(encrypted);
        Assert.assertEquals(message, decrypted);
    }






}
