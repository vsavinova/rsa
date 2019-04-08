package ru.hse;

import java.math.BigInteger;

import static ru.hse.BigInt.powMod;

public class RSAKeyGenerator {
    private static int N_BITS = 1024;

    private BigInteger p;
    private BigInteger q;
    private BigInteger exp;
    private BigInteger d;
    private BigInteger eulerN;
    private BigInteger modulus;
    private RandomGenerator generator;
    private BigInteger x;
    private BigInteger y;

    public RSAKeyGenerator() {
        generator = new RandomGenerator();
    }

    public void genKeyPairs() {
        p = generator.nextPrime(N_BITS); //generator.nextPrime();
        q = generator.nextPrime(N_BITS); //generator.nextPrime();
        eulerN = (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));
        modulus = p.multiply(q);
        exp = getOpenExponent();
        d = getSecretExponent();
//        p = BigInteger.probablePrime(1024 / 2, new Random());
//        q = BigInteger.probablePrime(1024 / 2, new Random());
//        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

//        modulus = p.multiply(q);
//        exp = new BigInteger("65537");
//        d = exp.modInverse(eulerN);
    }

    public BigInteger encrypt(BigInteger message) {
        return powMod(message, exp, modulus);
//        return message.modPow(exp, modulus);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return powMod(encrypted, d, modulus);
//        return encrypted.modPow(d, modulus);
    }

    public RSAKeyPair getOpenKeyPair() {
        return new RSAKeyPair(exp, modulus);
    }

    public RSAKeyPair getSecretKeyPair() {
        return new RSAKeyPair(d, modulus);
    }

    BigInteger getOpenExponent() {
//        BigInteger exp = BigInteger.probablePrime(10, random); //generator.next(BigInteger.valueOf(2), modulus.subtract(BigInteger.ONE));
        BigInteger exp = generator.next(BigInteger.valueOf(2), eulerN.subtract(BigInteger.ONE));
        int cnt = 1;
//        while (!eulerN.gcd(exp).equals(BigInteger.ONE))
        while (!generator.isPrime(exp)) {
            cnt++;
            exp = generator.next(BigInteger.valueOf(2), modulus.subtract(BigInteger.ONE));
//            exp = BigInteger.probablePrime(10, random);//generator.next(BigInteger.valueOf(2), modulus.subtract(BigInteger.ONE));
        }
        System.out.println(cnt);
        return exp;
    }


    BigInteger getSecretExponent() {
        return getMultInverse(exp, eulerN); // exp.modInverse(eulerN); //
    }

    // ax + by = gcd(a,b)
    BigInteger extendGCD(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            x = BigInteger.ZERO;
            y = BigInteger.ONE;
            return b;
        }
        BigInteger d = extendGCD(b.mod(a), a);
        BigInteger x1 = new BigInteger(x.toByteArray());
        x = y.subtract(b.divide(a).multiply(x));
        y = x1;
        return d;
    }

    BigInteger getMultInverse(BigInteger a, BigInteger m) {
        BigInteger g = extendGCD(a, m);
        if (g.compareTo(BigInteger.ONE) == 0) {
            x = (x.mod(m).add(m)).mod(m); // if x < 0
            return x;
        }
        return BigInteger.ZERO;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getEulerN() {
        return eulerN;
    }
}

class RSAKeyPair {
    private BigInteger key;
    private BigInteger modulus;

    public RSAKeyPair(BigInteger key, BigInteger modulus) {
        this.key = key;
        this.modulus = modulus;
    }

    public BigInteger getKey() {
        return key;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
