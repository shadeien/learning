package com.shadeien.math.operation;

import java.util.HashMap;
import java.util.Map;

public class OperationExample {
    /**
     * <1> 常用的等式：-n = ~(n-1) = ~n+1
     *
     * <2> 获取整数n的二进制中最后一个1：n&(-n) 或者 n&~(n-1)，如：n=010100，则-n=101100，n&(-n)=000100
     *
     * <3> 去掉整数n的二进制中最后一个1：n&(n-1)，如：n=010100，n-1=010011，n&(n-1)=010000
     * @param num1
     * @param num2
     * @return
     */
    public static int addV2(int num1, int num2) {
        int sum,carry;
        do {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        } while (carry != 0);

        return sum;
    }
    public static int add(int num1, int num2) {
        if (num2 == 0)
            return num1;
        int sum = num1 ^ num2;
        int carry = (num1 & num2)<<1;

        return add(sum, carry);
    }

    public static int substract(int num1, int num2) {
        return add(num1, add(~num2, 1));
    }

    public static long myMuti(int a,int b)
    {
        boolean flag=(b<0);
        if(flag) b=-b;
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        for(int i=0;i<32;i++)
        {
            map.put(1<<i, i);
        }
        int sum=0;
        while(b>0)
        {
            int last=b&(~b+1); //取得最后一个1
            int count=map.get(last);//取得相关的移位
            sum+=a<<count;
            b=b&(b-1);
        }
        if(flag) sum=-sum;
        return sum;
    }

    public static int multiply(int num1, int num2) {
        boolean flag = num2 < 0;
        if (flag) {
            num2 = add(~num2, 1);
        }
        int sum = 0;
        while (num2>0) {
            if ((num2 & 0x1) != 0) {
                sum = add(sum, num1);
            }
            num1 = num1 << 1;
            num2 = num2 >> 1;
        }
        if (flag) {
            sum = add(~sum, 1);
        }

        return sum;
    }

    public static int divideV1(int a, int b) {
        // 先取被除数和除数的绝对值
        int dividend = a > 0 ? a : add(~a, 1);
        int divisor = b > 0 ? b : add(~b, 1);

        int quotient = 0;// 商
        int remainder = 0;// 余数
        // 不断用除数去减被除数，直到被除数小于被除数（即除不尽了）
        while(dividend >= divisor){// 直到商小于被除数
            quotient = add(quotient, 1);
            dividend = substract(dividend, divisor);
        }
        // 确定商的符号
        if((a ^ b) < 0){// 如果除数和被除数异号，则商为负数
            quotient = add(~quotient, 1);
        }
        // 确定余数符号
        remainder = b > 0 ? dividend : add(~dividend, 1);
        System.out.println("remainder:"+remainder);
        return quotient;// 返回商
    }

    public static int divideV2(int a, int b) {
        // 先取被除数和除数的绝对值
        int dividend = a > 0 ? a : add(~a, 1);
        int divisor = b > 0 ? b : add(~b, 1);

        int quotient = 0;// 商
        int remainder = 0;// 余数
        // 不断用除数去减被除数，直到被除数小于被除数（即除不尽了）
        for (int i = 32; i >= 0; i--) {
            //比较dividend是否大于divisor的(1<<i)次方，不要将dividend与(divisor<<i)比较，而是用(dividend>>i)与divisor比较，
            //效果一样，但是可以避免因(divisor<<i)操作可能导致的溢出，如果溢出则会可能dividend本身小于divisor，但是溢出导致dividend大于divisor
            if((dividend >> i) >= divisor) {
                quotient = add(quotient, 1 << i);
                dividend = substract(dividend, divisor << i);
                break;
            }
        }
        // 确定商的符号
        if((a ^ b) < 0){// 如果除数和被除数异号，则商为负数
            quotient = add(~quotient, 1);
        }
        // 确定余数符号
        remainder = b > 0 ? dividend : add(~dividend, 1);
        System.out.println("remainder:"+remainder);
        return quotient;// 返回商
    }

    public static void main(String[] args) {
        System.out.println(add(100, 200));
        System.out.println(addV2(100, 200));
        System.out.println(substract(100, 200));
        System.out.println(myMuti(-8, 5));
        System.out.println(multiply(-8, -9));
        System.out.println(divideV1(-8, -3));
        System.out.println(divideV1(22222222, 2));
    }
}
