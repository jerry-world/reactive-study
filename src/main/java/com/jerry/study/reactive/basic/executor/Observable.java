package com.jerry.study.reactive.basic.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class Observable {

    // Observerable의 한계점
    /** 1. Complete 개념이 없음
     * 2. Error 난 케이스를 취급할 수 없음
     */



    // Iterable <---> Observable (쌍대성; duality) Erik meijer
    // Pull     <---> Push

    /**
     * Iterable
     * public static void main(String[] args) {
     * Iterable<Integer> iter = () ->
     * new Iterator<>() {
     * int i = 0;
     * final static int MAX = 10;
     * <p>
     * public boolean hasNext() {
     * return i < MAX;
     * }
     * <p>
     * public Integer next() {
     * return ++i;
     * }
     * };
     * <p>
     * for (Integer i : iter) {
     * System.out.println(i);
     * }
     * }
     **/

    static class IntObservable extends java.util.Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged(); // 이전과 다른 새로운 변화가 생겼다.
                notifyObservers(i); // 새로운 변화를 전달한다.
            }
        }
    }

    // Observable

    public static void main(String[] args) {
//        Observable : Source(이벤트 소스) -> Event(뭔가 상황이 발생했을 때 타겟인 옵저버에게 해당 상황을 던져준다.)
        java.util.Observer ob1 = (observable, arg) -> { //notifyObservers 가 호출되면 Observable에 등록된 Observer에게 전달
            System.out.println(Thread.currentThread().getName() + " " + arg);
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob1);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + "   EXIT");
    }
}
