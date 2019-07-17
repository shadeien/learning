package com.shadeien.math;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

@Slf4j
public class StringLearning {

    public static void main(String[] args) {
//        String a = new String(new int[]{20320, 22909}, 0, 2);
        String a = new String(new int[]{0x10FFFF, 0xFFF1, 0xD800}, 0, 3);
        log.info("String:{}", a);
        log.info("arrays:{}", a.toCharArray());

        String sp = "ab";
        String[] ar = sp.split("a");

        Pattern pattern = Pattern.compile("(a|b)*abb");
        log.info("{}", pattern.matcher("asssabb").toMatchResult());
        log.info("{}", pattern.matcher("bsabb").toMatchResult());
        log.info("{}", pattern.matcher("abss").toMatchResult());

        int hexCode = 0xFF;
        log.info("{}", hexCode);

        String str2 = new String("def");
        String str3 = str2.intern();
        String str4 = "def";
        log.info("{}, {}, {}", System.identityHashCode(str2), System.identityHashCode(str3), System.identityHashCode(str4));

        int i = 0x12345678;
        printAddresses("printAddresses", i);
//        char* pc = (char*)&i;
//        if (*pc == 0x12) {
//            printf("Big Endian\n");
//        } else if (*pc == 0x78) {
//            printf("Little Endian\n");
//        }
    }

    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ":         0x");
        Unsafe unsafe = getUnsafe();
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = 8;//is64bit ? 8 : 1;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString(last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
