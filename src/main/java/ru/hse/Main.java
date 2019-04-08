package ru.hse;

public class Main {

    public static void main(String[] args) {
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
}
