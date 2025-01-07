package com.jerry.study.reactive.asyncrest.completion;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class AcceptCompletion<S> extends Completion<S, Void> {
    public Consumer<S> con;
    public AcceptCompletion(Consumer<S> con) {
        this.con = con;
    }

    @Override
    void run(S value) {
        con.accept(value);
    }
}
