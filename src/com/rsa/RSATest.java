package com.rsa;

import org.junit.Assert;
import org.junit.Ignore;
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
        int numberOfBits = 1024;
        runRsa(numberOfBits, "h");
    }

    @Test
    public void shouldEncryptAndDecryptForLongString() {
        String message = "Lorem ipsum dolor sit amet " +
                "consectetur adipisicing elit. In sit modi" +
                " cumque ratione aliquam laudantium qui officiis" +
                " porro! Rem placeat ducimus est, illum quasi blanditiis. " +
                "Recusandae velit ad maiores expedita!";
        runRsa(1024, message);
    }


    @Test
    public void shouldEncyptAndDecryptNumbersAndNonAlphabets() {
        int numberOfBits = 1024;
        runRsa(numberOfBits, "?");
        runRsa(numberOfBits, "1");
    }

    private void runRsa(int numberOfBits, String s) {
        RSA rsa = new RSA(numberOfBits);
        testRsa(numberOfBits,rsa, s);
    }

    @Ignore("Takes a  lot of time approx. 56 seconds")
    @Test
    public void shouldDoForLongBits() {
        int largeNoOfBits = 1024 * 10;
        runRsa(largeNoOfBits, "Lorem ipsum dolor sit amet " +
                "consectetur adipisicing elit. In sit modi");
    }


    private void testRsa(int numberOfBits, RSA rsa, String message) {
        ArrayList encrypted = rsa.encryptString(message);
        ensureTextIsDividedIntoBlocks(message, encrypted);
        ensureTextIsEncrypted(numberOfBits,encrypted);
        ensureTheMessageAndTheDecryptedTextAreTheSame(rsa, message, encrypted);
    }

    private void ensureTheMessageAndTheDecryptedTextAreTheSame(RSA rsa, String message, ArrayList encrypted) {
        String decrypted = rsa.decrypt(encrypted);
        Assert.assertEquals(message, decrypted);
    }

    private void ensureTextIsEncrypted(int numberOfBits, ArrayList encrypted) {
        //The firstCipherText is bigint even if there is casting, proof of some encryption.
        BigInteger firstCipherText = (BigInteger) encrypted.get(0);
        int length = firstCipherText.toString().length();
        //the first word of the test string is much less than minimalEncryptedLength  hence if the first produced cipher text
        //is greater than minimalEncryptedLength, this can be used as an indication of encryption.
        int expectedNumberOfDecimalDigits= (int) Math.round(Math.log10(2)*numberOfBits);
        Assert.assertEquals(expectedNumberOfDecimalDigits,length);
    }

    private void ensureTextIsDividedIntoBlocks(String message, ArrayList encrypted) {

        //The text should be broken into words  in which each word is encypted as  a block.
        //Words are separated by blank space.
        int expectedNumberOfBlocks = message.split(" ").length;

        int numberOfEncryptedBlocks = encrypted.size();

        Assert.assertEquals(numberOfEncryptedBlocks, expectedNumberOfBlocks);
    }


}
