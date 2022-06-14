package com.joc.todo.strategy;

import java.util.ArrayList;
import java.util.List;

public class AppleUtils {

    public static List<Apple> getRandomApples(int count) {
        List<Apple> apples = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Apple Color
            int randomColorNumber = (int) (Math.random() * 6);
            Color randomColor = Color.valueOfOrdinal(randomColorNumber);


            // Apple Weight
            int randomWeight = (int) (Math.random() * 150 + 100);
            Apple apple = new Apple(randomColor, randomWeight);
            apples.add(apple);
        }
        return apples;
    }
}
