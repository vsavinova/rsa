package ru.hse;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static ru.hse.BigInt.powMod;

public class TestArifhmetics {
    @Test
    public void testAddMinus() {
        System.out.println(new BigInt(1));
        System.out.println(new BigInt(2));
        BigInt a0 = new BigInt(3);
        System.out.println("a0: " + a0);
        BigInt a = new BigInt(6);
        System.out.println("a: " + a);
        System.out.println("a: " + a.convertToInt());
        BigInt b = new BigInt(-1);
        System.out.println(b);
        BigInt x = new BigInt(-10);
        System.out.println("x: " + x);
        System.out.println(x);
        BigInt sum = a.add(b); // 5
        System.out.println(sum);
        System.out.println(sum.convertToInt());

        BigInt sum2 = a0.add(a); // 9
        System.out.println(sum2);
        System.out.println(sum2.convertToInt());

        b.convertToInt();
        BigInt sum3 = b.add(x); // -11
        BigInt sum4 = x.add(b); // -11
        BigInt sum5 = b.add(b); // -2
        System.out.println(sum3);
        System.out.println(sum3.convertToInt());
        System.out.println(sum4);
        System.out.println(sum4.convertToInt());
        System.out.println(sum5);
        System.out.println(sum5.convertToInt());

        BigInt subtr = a.subtr(a0); // 3
        System.out.println(subtr);
        System.out.println(subtr.convertToInt());
        subtr = a0.subtr(a); // -3
        System.out.println(subtr);
        System.out.println(subtr.convertToInt());
        subtr = a.subtr(a); // 0
        System.out.println(subtr);
        System.out.println(subtr.convertToInt());
        subtr = a0.subtr(a0); // 0
        System.out.println(subtr);
        System.out.println(subtr.convertToInt());

        BigInt subtr2 = a.subtr(x); // 16
        System.out.println(subtr2);
        System.out.println(subtr2.convertToInt());
        subtr2 = x.subtr(a); // -16
        System.out.println(subtr2);
        System.out.println(subtr2.convertToInt());
        subtr2 = b.subtr(x); // 9
        System.out.println(subtr2);
        System.out.println(subtr2.convertToInt());
        subtr2 = x.subtr(b); // -9
        System.out.println(subtr2);
        System.out.println(subtr2.convertToInt());
    }

    @Test
    public void testAdd() {
        BigInt bigInt1 = new BigInt(-1000);
        BigInt bigInt2 = new BigInt(1);
        BigInt add = bigInt1.add(bigInt2);
        System.out.println(add);
        assertEquals(-999, add.convertToInt());
    }

    @Test
    public void testMult() {
        BigInt a = new BigInt(10);
        BigInt b = new BigInt(1);
        assertEquals(10, a.mult(b).convertToInt());

        a = new BigInt(5);
        b = new BigInt(7);
        assertEquals(35, a.mult(b).convertToInt());
        assertEquals(35, b.mult(a).convertToInt());


        a = new BigInt(100);
        b = new BigInt(7);
        assertEquals(700, a.mult(b).convertToInt());


        a = new BigInt(7);
        b = new BigInt(120);
        assertEquals(840, a.mult(b).convertToInt());


        a = new BigInt(-1);
        b = new BigInt(7);
        assertEquals(-7, a.mult(b).convertToInt());

        a = new BigInt(7);
        b = new BigInt(-1);
        assertEquals(-7, a.mult(b).convertToInt());

        a = new BigInt(-357);
        b = new BigInt(-17);
        assertEquals(357 * 17, a.mult(b).convertToInt());

        a = new BigInt(-357);
        b = new BigInt(0);
        assertEquals(0, a.mult(b).convertToInt());

    }

    @Test
    public void testComparison() {
        BigInt a = new BigInt(50);
        BigInt b = new BigInt(3);
        BigInteger a_ = BigInteger.valueOf(50);
        BigInteger b_ = BigInteger.valueOf(3);
        assertEquals(a_.compareTo(b_), a.compareTo(b));
        assertEquals(b_.compareTo(a_), b.compareTo(a));
        assertEquals(a_.compareTo(a_), a.compareTo(a));

        a = new BigInt(-50);
        b = new BigInt(3);
        a_ = BigInteger.valueOf(-50);
        b_ = BigInteger.valueOf(3);
        assertEquals(a_.compareTo(b_), a.compareTo(b));
        assertEquals(b_.compareTo(a_), b.compareTo(a));

        a = new BigInt(-1000);
        b = new BigInt(0);
        a_ = BigInteger.valueOf(-1000);
        b_ = BigInteger.valueOf(0);
        assertEquals(a_.compareTo(b_), a.compareTo(b));
        assertEquals(b_.compareTo(a_), b.compareTo(a));

        a = new BigInt(7575);
        b = new BigInt(0);
        a_ = BigInteger.valueOf(7575);
        b_ = BigInteger.valueOf(0);
        assertEquals(a_.compareTo(b_), a.compareTo(b));
        assertEquals(b_.compareTo(a_), b.compareTo(a));

        a = new BigInt(-1000);
        b = new BigInt(-4504);
        a_ = BigInteger.valueOf(-1000);
        b_ = BigInteger.valueOf(-4504);
        assertEquals(a_.compareTo(b_), a.compareTo(b));
        assertEquals(b_.compareTo(a_), b.compareTo(a));
    }

    @Test
    public void testGenerator() {
        RandomGenerator generator = new RandomGenerator();
        BigInteger next = generator.next();
        BigInteger first = next;
//        System.out.println("Start check");
        while (!generator.isPrime(next)) {
//            System.out.println(next);
//            System.out.println("Finish check");
            if (next.isProbablePrime(1000000))
                System.out.println("Prime: " + next);
            next = generator.next();
            if (next.equals(first))
                break;
//            System.out.println("Start prime check");
        }
        System.out.println(next);
        System.out.println(next.isProbablePrime(1000000));
        System.out.println(next.isProbablePrime(100000000));
        System.out.println(generator.isPrime(next));
    }

    @Test
    public void testPrime() {
        RandomGenerator generator = new RandomGenerator();
        System.out.println(generator.isPrime(BigInteger.valueOf(5)));
        System.out.println(generator.isPrime(BigInteger.valueOf(-1785360058218659843L)));
        BigInteger x = generator.nextPrime();
        System.out.println(x);
        System.out.println(x.isProbablePrime(1000));
        System.out.println(generator.isPrime(x));
    }

    @Test
    public void testExponent() {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        rsaKeyGenerator.genKeyPairs();
        BigInteger exponent = rsaKeyGenerator.getOpenExponent();
        System.out.println(exponent);
        String binStr = "";
        int bitCnt = 0;
        while (!exponent.equals(BigInteger.ZERO)) {
            BigInteger bigTwo = BigInteger.valueOf(2);
            BigInteger mod = exponent.mod(bigTwo);
            binStr += mod.toString();
            exponent = exponent.divide(bigTwo);
            if (mod.equals(BigInteger.ONE))
                bitCnt++;
        }

        System.out.println(new StringBuilder(binStr).reverse());
        System.out.println(bitCnt);
        System.out.println(binStr.length());
    }

    @Test
    public void testExtGC() {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        BigInteger a = BigInteger.valueOf(25);
        BigInteger b = BigInteger.valueOf(35);
        assertEquals(BigInteger.valueOf(5), rsaKeyGenerator.extendGCD(a, b));
        BigInteger x = rsaKeyGenerator.getX();
        System.out.println(x);
        BigInteger y = rsaKeyGenerator.getY();
        System.out.println(y);
        assertEquals(BigInteger.valueOf(5), a.multiply(x).add(b.multiply(y)));

        a = BigInteger.valueOf(41);
        b = BigInteger.valueOf(35);
        assertEquals(BigInteger.ONE, rsaKeyGenerator.extendGCD(a, b));
        x = rsaKeyGenerator.getX();
        System.out.println(x);
        y = rsaKeyGenerator.getY();
        System.out.println(y);
        assertEquals(BigInteger.ONE, a.multiply(x).add(b.multiply(y)));


    }

    @Test
    public void testMultInverse() {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        assertEquals(BigInteger.ZERO, rsaKeyGenerator
                .getMultInverse(BigInteger.valueOf(5), BigInteger.valueOf(35)));

        assertEquals(BigInteger.valueOf(3), rsaKeyGenerator
                .getMultInverse(BigInteger.valueOf(12), BigInteger.valueOf(35)));

        BigInteger mod = new RandomGenerator().nextPrime();
        BigInteger modPlus1 = mod.add(BigInteger.ONE);
        assertEquals(modPlus1.divide(BigInteger.valueOf(2)),
                rsaKeyGenerator.getMultInverse(BigInteger.valueOf(2), mod));
    }

    @Test
    public void testCript() {
        BigInteger message = BigInteger.valueOf(3);
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        rsaKeyGenerator.genKeyPairs();
        RSAKeyPair openKeyPair = rsaKeyGenerator.getOpenKeyPair();
        BigInteger c = message.modPow(openKeyPair.getKey(), openKeyPair.getModulus());

        RSAKeyPair secretKeyPair = rsaKeyGenerator.getSecretKeyPair();
        System.out.println(c.modPow(secretKeyPair.getKey(), secretKeyPair.getModulus()));
        assertEquals(openKeyPair.getModulus(), secretKeyPair.getModulus());
        System.out.println(openKeyPair.getKey().multiply(secretKeyPair.getKey()).mod(rsaKeyGenerator.getEulerN()));
    }

    @Test
    public void testEncrypt() {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        BigInteger message = BigInteger.valueOf(3);
        rsaKeyGenerator.genKeyPairs();
        BigInteger encrypt = rsaKeyGenerator.encrypt(message);
        BigInteger decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);

        message = BigInteger.valueOf(10);
        rsaKeyGenerator.genKeyPairs();
        encrypt = rsaKeyGenerator.encrypt(message);
        decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);

        message = BigInteger.valueOf(1030293);
        rsaKeyGenerator.genKeyPairs();
        encrypt = rsaKeyGenerator.encrypt(message);
        decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);

        message = BigInteger.valueOf(5);
        rsaKeyGenerator.genKeyPairs();
        encrypt = rsaKeyGenerator.encrypt(message);
        decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);

        message = BigInteger.valueOf(0);
        rsaKeyGenerator.genKeyPairs();
        encrypt = rsaKeyGenerator.encrypt(message);
        decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);

        message = new RandomGenerator().next();
        rsaKeyGenerator.genKeyPairs();
        encrypt = rsaKeyGenerator.encrypt(message);
        decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);
//        System.out.println(rsaKeyGenerator.getOpenKeyPair().getKey());
    }

    @Test
    public void test1() {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        BigInteger message = BigInteger.valueOf(394962394);
        rsaKeyGenerator.genKeyPairs();
        BigInteger encrypt = rsaKeyGenerator.encrypt(message);
        BigInteger decrypt = rsaKeyGenerator.decrypt(encrypt);
        assertEquals(message, decrypt);
    }

    @Test
    public void testPowMod() {
        BigInteger a = BigInteger.valueOf(2);
        BigInteger n = BigInteger.valueOf(6);
        BigInteger mod = BigInteger.valueOf(128);
        BigInteger powMod = powMod(a, n, mod);
        assertEquals(a.modPow(n, mod), powMod);

        a = BigInteger.valueOf(2);
        n = BigInteger.valueOf(6);
        mod = BigInteger.valueOf(32);
        powMod = powMod(a, n, mod);
        assertEquals(a.modPow(n, mod), powMod);

        a = BigInteger.valueOf(37);
        n = BigInteger.valueOf(63);
        mod = BigInteger.valueOf(35);
        powMod = powMod(a, n, mod);
        assertEquals(a.modPow(n, mod), powMod);

        a = BigInteger.valueOf(12380);
        n = BigInteger.valueOf(230);
        mod = BigInteger.valueOf(512090290);
        powMod = powMod(a, n, mod);
        assertEquals(a.modPow(n, mod), powMod);
        System.out.println("here");
    }
}
