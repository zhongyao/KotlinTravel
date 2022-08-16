package com.hongri.kotlin.chapter14;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：hongri
 * @date：8/16/22
 * @description：
 */
public class TestJava {

    public void printFruits() {
        List<String> list = new ArrayList<>();
        list.add("Banana");
        list.add("Orange");
        list.add("Grape");

        for (String fruit : list) {
            System.out.println(fruit);
        }
    }

}
