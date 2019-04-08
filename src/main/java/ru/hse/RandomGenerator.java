package ru.hse;

import java.math.BigInteger;

import static ru.hse.BigInt.powMod;

public class RandomGenerator {
    private BigInteger pred = BigInteger.valueOf(1); // 1
    private static BigInteger m = BigInteger.valueOf((long) Math.pow(2, 1024)); // 2^number of bits (2^1024)
    private static final long a = 1664525; //6364136223846793005L; //6364136223846793005L; //0x5DEECE66DL;
    private static final long c = 1013904223; //1; //0xBL; //1442695040888963407L; //0xBL;
    private static final int K = 100; // кол-во итераций проверки на простоту
    private static final long mask = (1L << 48) - 1;


    BigInteger next() {
        pred = pred.multiply(BigInteger.valueOf(a)).add(BigInteger.valueOf(c))
                .mod(m);
        return pred;
    }

    BigInteger nextPrime() {
        BigInteger next = next();
        BigInteger first = next;
        int cnt = 0;
        while (!isPrime(next)) {
            cnt++;
            next = next();
            if (next.equals(first)) {
                System.out.println("No prime");
                break;
            }
        }
//        System.out.println(cnt);
        return next;
    }

    BigInteger nextPrime(int nBits) {
        BigInteger left = BigInteger.valueOf(2).pow(nBits - 1);
        BigInteger right = BigInteger.valueOf(2).pow(nBits);
        BigInteger next = next(left, right);
        BigInteger first = next;
        int cnt = 0;
        while (!isPrime(next)) {
            cnt++;
            next = next(left, right);
            if (next.equals(first)) {
                System.out.println("No prime");
                break;
            }
        }
        System.out.println(cnt);
        return next;
    }

    BigInteger next(BigInteger left, BigInteger right) {
        pred = next();
//        pred = pred.multiply(BigInteger.valueOf(a))
//                .add(BigInteger.valueOf(c)).mod(m);
//                .and(BigInteger.valueOf(mask));
        return left.add(pred.mod(right.subtract(left).add(BigInteger.ONE))); // left + pred%(right-left+1) -> [0;right]
    }

    boolean isPrime(BigInteger n) {
        n = n.abs();
        BigInteger bigTwo = BigInteger.valueOf(2);
        if (n.equals(bigTwo) || n.equals(BigInteger.valueOf(3)))
            return true;
        if (n.compareTo(bigTwo) < 0 || n.mod(bigTwo).equals(BigInteger.ZERO))
            return false;

        // n - 1 = 2^s * t
        int s = 0;
        BigInteger t = n.subtract(BigInteger.ONE);
        while (t.mod(bigTwo).equals(BigInteger.ZERO)) {
            t = t.divide(bigTwo);
            s++;
        }
        for (int i = 0; i < K; i++) {
            BigInteger a = next(bigTwo, n.subtract(bigTwo));
            BigInteger x = powMod(a, t, n);//a.modPow(t, n);
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;
            for (int j = 0; j < s - 1; j++) {
                x = powMod(x, bigTwo, n); //x.modPow(bigTwo, n);
                if (x.equals(BigInteger.ONE)) {
//                    System.out.println(a);
                    return false;
                }
                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }
            if (!x.equals(n.subtract(BigInteger.ONE))) {
//                System.out.println(a);
                return false;
            }
        }
        return true;
    }

    private int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
            419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
            547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
            661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809,
            811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
            947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069,
            1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223,
            1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373,
            1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511,
            1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657,
            1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811,
            1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987,
            1993, 1997, 1999};
}
