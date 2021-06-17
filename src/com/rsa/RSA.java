package com.rsa;


import java.math.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class RSA {
    private BigInteger valueOf1 = BigInteger.ONE;

    private int numberOfBits;
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger modulus;
    private BigInteger n;
    private BigInteger d;

    public RSA(int numberOfBits) {
        this.numberOfBits = numberOfBits;
        setSetTheTwoBigPrimes();
        calculateN();
        calculateModulus();
        calculatePublicKeyE();
        calculatePrivateKeyD();

    }

    private void setSetTheTwoBigPrimes() {
        Random rng = new Random();
        int bitPerPrime = numberOfBits / 2;
        p = BigInteger.probablePrime(bitPerPrime, rng);
        q = BigInteger.probablePrime(bitPerPrime, rng);
    }

    public ArrayList encryptString(String message) {
        try {
            String[] words = message.split(" ");
            return encryptWords(words);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    private ArrayList encryptWords(String[] words) {
        ArrayList encrypted = new ArrayList<BigInteger>();
        for (String word : words
        ) {
            BigInteger encoded = encodeWordIntoHexNumber(word);
            encrypted.add(encryptNum(encoded));
        }
        return encrypted;
    }

    private BigInteger encodeWordIntoHexNumber(String word) {
        String base16String = "";
        byte[] bytes = word.getBytes(StandardCharsets.UTF_8);
        for (byte c : bytes
        ) {
            String binString = Integer.toHexString((int) c);
            base16String += binString;
        }
        return new BigInteger(base16String, 16);
    }


    public BigInteger encryptNum(BigInteger word) {
        return word.modPow(e, n);
    }

    public String decrypt(ArrayList encrypted) {
        String decrypted = "";
        BigInteger listSize = BigInteger.valueOf(encrypted.size());
        for (BigInteger i = BigInteger.ZERO; haveNotExhaustedList(listSize, i); i = i.add(valueOf1)) {
            String word = getDecrypedWord(encrypted, i);
            if (i.equals(listSize.subtract(valueOf1))) {
                decrypted += word;
            } else
                decrypted += (word + " ");

        }
        return decrypted;
    }

    private String getDecrypedWord(ArrayList encrypted, BigInteger i) {
        BigInteger value = parseCipherText(encrypted, i);
        String result = "";
        BigInteger decryptedWord = decryptNum(value);
        char[] wordToBase16 = decryptedWord.toString(16).toCharArray();
        for (int j = 0; j < wordToBase16.length; j += 2) {
            String st = "" + wordToBase16[j] + wordToBase16[j + 1];
            char ch = (char) Integer.parseInt(st, 16);
            result += ch;
        }
        return result;
    }

    private BigInteger parseCipherText(ArrayList encrypted, BigInteger i) {
        return (BigInteger) encrypted.get(i.intValue());
    }

    private boolean haveNotExhaustedList(BigInteger size, BigInteger i) {
        return i.compareTo(size) < 0;
    }

    public BigInteger decryptNum(BigInteger c) {
        return c.modPow(d, n);
    }

    public void calculateN() {
        n = p.multiply(q);
    }

    private void calculateModulus() {
        modulus = (p.subtract(valueOf1)).multiply(q.subtract(valueOf1));
    }

    public void calculatePublicKeyE() {
        for (BigInteger testE = BigInteger.valueOf(numberOfBits); testE.compareTo(modulus) < 0; testE = testE.add(valueOf1)) {
            if ((testE.gcd(modulus)).equals(BigInteger.ONE)) {
                e = testE;
                break;
            }
        }
    }


    public void calculatePrivateKeyD() {
        d = e.modInverse(modulus);
    }


    static BigInteger gcd(BigInteger e, BigInteger z) {
        if (e.compareTo(BigInteger.valueOf(0)) == 0)
            return z;
        else
            return gcd(z.mod(e), e);
    }

}




