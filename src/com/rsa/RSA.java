package com.rsa;


import java.math.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class RSA {
    private BigInteger valueOf1 = BigInteger.ONE;
    Random rng = new Random();
    int numberOfBits = 1024;
    int bitPerPrime = numberOfBits / 2;
    private BigInteger p = BigInteger.probablePrime(bitPerPrime, rng);
    private BigInteger q = BigInteger.probablePrime(bitPerPrime, rng);
    private BigInteger e;
    private BigInteger modulus;
    private BigInteger n;
    private BigInteger d;

    public RSA() {
        calculateN();
        calculateModulus();
        calculatePublicKeyE();
        calculatePrivateKeyD();

    }

    public ArrayList encryptString(String message) {
        try {

            ArrayList encrypted = new ArrayList<BigInteger>();

            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            for (byte num : messageBytes
            ) {
                encrypted.add(encryptNum(num));
            }
            return encrypted;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


    private BigInteger encryptNum(int msg) {
        BigInteger message = BigInteger.valueOf(msg);
        return message.modPow(e, n);
    }

    public String decrypt(ArrayList encrypted) {
        String decrypted = "";
        BigInteger listSize = BigInteger.valueOf(encrypted.size());
        for (BigInteger i = BigInteger.ZERO; haveNotExhaustedList(listSize, i); i = i.add(valueOf1)) {
            String ch = getDecryptedChar(encrypted, i);
            decrypted += ch;
        }
        return decrypted;
    }

    private String getDecryptedChar(ArrayList encrypted, BigInteger i) {
        return Character.toString((decryptNum(parseCipherText(encrypted, i))));
    }

    private BigInteger parseCipherText(ArrayList encrypted, BigInteger i) {
        return (BigInteger) encrypted.get(i.intValue());
    }

    private boolean haveNotExhaustedList(BigInteger size, BigInteger i) {
        return i.compareTo(size) < 0;
    }

    public int decryptNum(BigInteger c) {
        return (c.modPow(d, n)).intValue();
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




