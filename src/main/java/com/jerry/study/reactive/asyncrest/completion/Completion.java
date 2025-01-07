package com.jerry.study.reactive.asyncrest.completion;

//import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

// 이전의 비동기 작업의 결과를 이어받아 비동기 작업을 수행하고 그다음 비동기 작업에게 나의 결과를 넘겨줌.
// 타입이 2개일 수 있음(andApply)

public class Completion<S, T> {
    public Completion next;


    public Completion() {
    }


    //1. Class 레벨에 정의되어있는 타입 파라미터를 이 메서드에 적용하는가
    //2. Method 레벨에서 추가 정의된 타입 파라미터를 적용하는가
    public void andAccept(Consumer<T> con) {
        Completion<T, Void> c = new AcceptCompletion<>(con);
        this.next = c;
    }

    public Completion<T, T> andError(Consumer<Throwable> econ) {
        Completion<T, T> c = new ErrorCompletion<>(econ);
        this.next = c;
        return c;
    }

//    public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
//        Completion<T, V> c = new AsyncCompletion<>(fn);
//        this.next = c;
//        return c;
//    }
//
//    public static <S, T> Completion<S, T> from(ListenableFuture<T> f) {
//        Completion<S, T> completion = new Completion<>();
//        f.addCallback(
//                completion::complete,
//                completion::error
//        );
//        return completion;
//    }

    void complete(T s) {
        if (next != null) {
            next.run(s);
        }
    }

    void error(Throwable e) {
        if (next != null) next.error(e);
    }

    void run(S value) {
    }
}
