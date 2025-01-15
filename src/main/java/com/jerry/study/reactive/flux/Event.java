package com.jerry.study.reactive.flux;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Event {
    private long id;
    private String value;
}
