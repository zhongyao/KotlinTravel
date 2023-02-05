package com.hongri.kotlin.util;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author：hongri
 * @date：2/2/23
 * @description：
 */
public class JavaUtil {

    public static void testJvmOverloads() {
        Util.INSTANCE.f("ddd",300);
    }

    @Nullable
    public static List<String> getList() {
        return null;
    }
}
