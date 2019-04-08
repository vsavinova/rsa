package ru.hse;

import java.math.BigInteger;
import java.util.Arrays;

public class BigInt {
    private static int MAX_DIGITS = 20; // TODO: 22.03.2019 replace with 1024

    private int[] digits = new int[MAX_DIGITS]; // младшие разряды в начале, т.е. digits[0] - младший разряд

    public BigInt() {
        Arrays.fill(digits, 0);
    }

    private BigInt(int[] digits) {
        this.digits = Arrays.copyOf(digits, MAX_DIGITS);
    }

    public BigInt(int i) {
        if (i >= 0) {
            Arrays.fill(digits, 0);
            convertIntToBinary(i, true);
        } else {
            Arrays.fill(digits, 1);
            convertIntToBinary(i, false);
        }
    }

    public static BigInteger powMod(BigInteger a, BigInteger n, BigInteger modulus) {
        if (n.equals(BigInteger.ZERO))
            return BigInteger.ONE;
        BigInteger bigTwo = BigInteger.valueOf(2);
        if (n.mod(bigTwo).equals(BigInteger.ONE))
            return a.multiply(powMod(a, n.subtract(BigInteger.ONE), modulus)).mod(modulus); // a * a^(n-1)
        else { // n % 2 == 0
            BigInteger a_ndiv2 = powMod(a, n.divide(bigTwo), modulus);
            return a_ndiv2.multiply(a_ndiv2).mod(modulus); // a^(n/2) * a^(n/2) = a^(2*n/2)
        }
    }

    private void convertIntToBinary(int i, boolean isPositive) {
        int cnt = 0;
        i = Math.abs(i);
        // прямой и обрат код
        while (i > 0) {
            int digit = i % 2;
            i = i / 2;
            if (isPositive) {
                digits[cnt++] = digit;
            } else
                digits[cnt++] = 1 - digit;
        }
        if (isPositive)
            return;

        // доп код для отриц
        digits = add(digits);
    }

    // TODO: 22.03.2019 Overflow
    private static int[] add(int[] digits) {
        for (int i = 0; i < MAX_DIGITS; i++) {
            if (digits[i] == 0) {
                digits[i] = 1;
                break;
            }
            digits[i] = 0;
        }
        return digits;
    }

    int convertToInt() {
        int integer;
        if (digits[MAX_DIGITS - 1] == 0) // positive
            return makeIntFromBitArray(digits);

        int[] new_digits = convertToAdditionalCode(digits);
        integer = -makeIntFromBitArray(new_digits);
        return integer;
    }

    private static int[] convertToAdditionalCode(int[] digits) {
        int[] new_digits = new int[MAX_DIGITS];
        for (int i = 0; i < MAX_DIGITS; i++) {
            new_digits[i] = 1 - digits[i];
        }
        new_digits = add(new_digits);
        return new_digits;
    }


    private int makeIntFromBitArray(int[] digits) {
        int integer = 0;
        for (int i = 0; i < MAX_DIGITS; i++) {
            integer += digits[i] * Math.pow(2, i);
        }
        return integer;
    }

    BigInt add(BigInt a) {
        int[] new_digits = new int[MAX_DIGITS];
        int carry_bit = 0;
        for (int i = 0; i < MAX_DIGITS; i++) {
            new_digits[i] = a.digits[i] + digits[i] + carry_bit;
            if (new_digits[i] > 1) {
                new_digits[i] = new_digits[i] % 2;
                carry_bit = 1;
            } else
                carry_bit = 0;
        }
        return new BigInt(new_digits);
    }

    BigInt subtr(BigInt a) {
        BigInt minus_a = new BigInt(convertToAdditionalCode(a.digits));
        return add(minus_a);
    }


    BigInt mult2(BigInt a) {
        boolean isPositive = a.digits[MAX_DIGITS - 1] == 0;
        int[] result = new int[MAX_DIGITS];
        for (int i = 0; i < MAX_DIGITS; i++) {
            result[i] = 0;
        }
        for (int i = 0; i < a.digits.length; i++) {
            if (a.digits[i] == 1) {
                int carry_bit = 0;
                for (int j = i; j < MAX_DIGITS; j++) {
                    result[j] += digits[j - i] + carry_bit;
                    if (result[j] > 1) {
                        result[j] = result[j] % 2;
                        carry_bit = 1;
                    } else
                        carry_bit = 0;
                }
            }
        }

        BigInt mult = new BigInt(result);
        if (isPositive) {
            return mult;
        }
        // Коррекция: псевдопроизведение - this
        return mult.subtr(this);
    }

    // TODO: 24.03.2019 Доделать
    BigInt mult(BigInt b) {
        BigInt mult;
        BigInt abs_a;
        BigInt abs_b;
        boolean resPositive = true;
        if (digits[MAX_DIGITS - 1] == 1) {
            abs_a = new BigInt(convertToAdditionalCode(digits));
            resPositive = false;
        } else
            abs_a = new BigInt(digits);
        if (digits[MAX_DIGITS - 1] == 1) {
            abs_b = new BigInt(convertToAdditionalCode(b.digits));
            resPositive = !resPositive;
        } else
            abs_b = new BigInt(b.digits);

        int[] result = new int[MAX_DIGITS];
        for (int i = 0; i < MAX_DIGITS; i++) {
            result[i] = 0;
        }
        for (int i = 0; i < abs_b.digits.length; i++) {
            if (abs_b.digits[i] == 1) {
                int carry_bit = 0;
                for (int j = i; j < MAX_DIGITS; j++) {
                    result[j] += abs_a.digits[j - i] + carry_bit;
                    if (result[j] > 1) {
                        result[j] = result[j] % 2;
                        carry_bit = 1;
                    } else
                        carry_bit = 0;
                }
            }
        }

        if (!resPositive)
            mult = new BigInt(convertToAdditionalCode(result));
        else
            mult = new BigInt(result);
        return mult;
    }


    BigInt div(BigInt b) {
        if (compareTo(b) < 0) {
            System.out.println("Error: A < B");
            return new BigInt();
        }
//        if (compareTo(b) == 0)
//            return 1;
        BigInt abs_a;
        BigInt abs_b;
        boolean resPositive = true;
        if (digits[MAX_DIGITS - 1] == 1) {
            abs_a = new BigInt(convertToAdditionalCode(digits));
            resPositive = false;
        } else
            abs_a = new BigInt(digits);

        if (digits[MAX_DIGITS - 1] == 1) {
            abs_b = new BigInt(convertToAdditionalCode(b.digits));
            resPositive = !resPositive;
        } else
            abs_b = new BigInt(b.digits);

        int ind_a = MAX_DIGITS - 1, ind_b = MAX_DIGITS - 1; // ищем где начинается число без 0 впереди
        for (int i = MAX_DIGITS - 1; i >= 0; i++) {
            if (ind_a == (MAX_DIGITS - 1) && abs_a.digits[i] != 0)
                ind_a = i;
            if (ind_b == (MAX_DIGITS - 1) && abs_b.digits[i] != 0)
                ind_b = i;
            if (ind_a != (MAX_DIGITS - 1) && ind_b != (MAX_DIGITS - 1))
                break;
        }

        int divider_len = ind_b + 1; //MAX_DIGITS - ind_b;
        int[] divider = new int[divider_len];
        for (int i = 0; i < divider_len; i++) {
            divider[i] = abs_b.digits[i];
        }
        int[] dividend_all = abs_a.digits;
        int[] res_digits = new int[MAX_DIGITS];
        Arrays.fill(res_digits, 0);
        int[] dividend = new int[divider_len + 1]; //  может быть только на 1 бит длинее
        int div_ind = 0;
        int res_ind = 0;
        for (int i = ind_a; i >= 0; i--) {
            for (int j = i; j > i - divider_len; j--) {
                dividend[divider_len - (i - j)] = dividend_all[j]; // 0 - младший
            }
            if (compare(dividend, divider) == -1) {
                res_digits[i - ind_a] = 0;  // max в 0ом
                dividend[divider_len] = dividend_all[i - divider_len - 1];
            } else
                res_digits[i - ind_a] = 1;
            int[] subtr = subtr(dividend, divider);
            int new_digit = dividend_all[ind_a + 1];
            dividend_all = shift(subtr);
//            dividend_all[0] = abs_a.digits[]
            ind_a = divider_len - 1;
        }
        return new BigInt();
    }

    private static int[] shift(int[] a) {
        int[] res = new int[MAX_DIGITS];
        Arrays.fill(res, 0);
        for (int i = 0; i < a.length - 1; i++) {
            res[i + 1] = a[i];
        }
        return res;
    }

    private static int compare(int[] a, int[] b) {
        if (a.length > b.length)
            return 1;
        if (a.length < b.length) {
            System.out.println("Error in div comparison");
            return -1;
        }
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] < b[i])
                return -1;
            if (a[i] > b[i])
                return 1;
        }
        return 0;
    }

    private int[] subtr(int[] a, int[] b) {
        int[] res = new int[b.length]; // min в 0
        if (a.length < b.length) {
            System.out.println("Error in div subtr");
            return res;
        }
        int carry = 0;
        int ind_b = b.length < a.length ? 1 : 0;
        for (int i = a.length - 1; i >= ind_b; i--) {
            res[i] = a[i] - b[i - ind_b] - carry;
            if (res[i] < 0) {
                res[i] = Math.abs(res[i]) % 2;
                carry = 1;
            } else
                carry = 0;
        }
        return res;
    }

    BigInt mod(BigInt a) {
        return new BigInt();
    }

    // this > a -> 1,
    // this == a -> 0,
    // this < a -> -1
    int compareTo(BigInt b) {
        int signA = digits[MAX_DIGITS - 1];
        int signB = b.digits[MAX_DIGITS - 1];
        BigInt absA;
        BigInt absB;
        boolean isNegative = false;
        if (signA == 1 && signB == 0)
            return -1;
        if (signA == 0 && signB == 1)
            return 1;
        if (signA == 1 && signB == 1) {
            absA = new BigInt(convertToAdditionalCode(digits));
            absB = new BigInt(convertToAdditionalCode(b.digits));
            isNegative = true;
        } else {
            absA = this;
            absB = b;
        }

        for (int i = MAX_DIGITS - 1; i >= 0; i--) {
            if (absA.digits[i] > absB.digits[i])
                if (!isNegative)
                    return 1;
                else
                    return -1;
            if (absB.digits[i] > absA.digits[i])
                if (!isNegative)
                    return -1;
                else
                    return 1;
        }

        return 0;
    }

    BigInt add(int a) {
        return new BigInt();
    }


    BigInt subtr(int a) {
        return new BigInt();
    }


    BigInt mult(int a) {
        return new BigInt();
    }


    BigInt div(int a) {
        return new BigInt();
    }

    BigInt mod(int a) {
        return new BigInt();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = MAX_DIGITS - 1; i >= 0; i--) {
            stringBuilder.append(digits[i]);
        }
        return stringBuilder.toString();
    }
}
