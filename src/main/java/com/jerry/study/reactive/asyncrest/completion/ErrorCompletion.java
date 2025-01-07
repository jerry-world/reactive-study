package com.jerry.study.reactive.asyncrest.completion;

import java.util.function.Consumer;

public class ErrorCompletion<T> extends Completion<T, T> {
    public Consumer<Throwable> econ;

    public ErrorCompletion(Consumer<Throwable> econ) {
        this.econ = econ;
    }

    @Override
    void run(T value) {
        if (next != null) next.run(value);
    }

    @Override
    void error(Throwable e) {
        econ.accept(e);
    }
}
