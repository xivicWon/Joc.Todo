package com.joc.todo.strategy;

import java.util.ArrayList;
import java.util.List;

public class AppleFilter {

    // 외부에서 일부 로직을 결정하는 패턴 : 탬플릿 메소드 패턴(상속), 전략 패턴
    //

    // OCP ( Open-closed Principal) -> 확장에는 닫혀있고, 수정에는 닫혀있다( 코드 미수정 )
    public static List<Apple> filterApples(List<Apple> apples, ApplePredicate applePredicate) {

        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            // 전략 패턴 ( Stratigy Pattern) -> 로직의 일부 행동을 외부에서 주입받는다.
            // 특정 메소드를 이용하는 클라이언트가 전체 로직의 일부 행동을 결정한다.
            if (applePredicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> randomApples = AppleUtils.getRandomApples(100);

        // 1. 일반 구현식
        List<Apple> apples = AppleFilter.filterApples(randomApples, new AppleGreenColorPredicate());

        // 2. 익명 클래스
        //List<Apple> apples = AppleFilter.filterApples(randomApples, new ApplePredicate() {
        //    @Override
        //    public boolean test(Apple apple) {
        //        return false;
        //    }
        //});

        //  3. 람다식
        // List<Apple> apples = AppleFilter.filterApples(randomApples, apple -> false);

        for (Apple apple : apples) {
            System.out.println("apple = " + apple);
        }
    }
}
