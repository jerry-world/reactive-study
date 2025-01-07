package com.jerry.study.reactive.asyncrest.completion;

//import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public class AsyncCompletion<S, T> extends Completion<S, T> {

//    public Function<S, ListenableFuture<T>> fn;
//
//    public AsyncCompletion(Function<S, ListenableFuture<T>> fn) {
//        this.fn = fn;
//    }
//
//    @Override
//    void run(S value) {
//        ListenableFuture<T> lf = fn.apply(value);
//        lf.addCallback(
//                this::complete,
//                this::error
//        );
//    }
}
