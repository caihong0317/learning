package com.caihong.javathinking.multideleviry;

import com.caihong.javathinking.enumtype.Enums;

public class RoShamBoUtil {
    public static <T extends Competitor<T>> void match(T a, T b) {
        System.out.println(a + " VS " + b + ": " + a.compete(b));
    }

    public static <T extends Enum<T> & Competitor<T>> void play(Class<T> clazz, int size) {
        for (int i = 0; i < size; i++) {
            match(Enums.random(clazz), Enums.random(clazz));
        }
    }
}
