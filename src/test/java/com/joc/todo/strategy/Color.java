package com.joc.todo.strategy;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicReference;

public enum Color {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    BLACK,
    WHITE;

    public static Color valueOfOrdinal(int ordinal) {
        EnumSet<Color> colors = EnumSet.allOf(Color.class);
        AtomicReference<Color> result = new AtomicReference<>();
        colors.forEach(color -> {
            if (color.ordinal() == ordinal) {
                result.set(color);
            }
        });

        return result.get();
    }
}
