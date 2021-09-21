package com.qaladies.reactive.unittest;

import com.qaladies.reactive.unittest.model.Lady;

public class LadyMock {

    public static Lady lady(){
        return Lady.builder()
                .id("1")
                .name("kamila")
                .role("backend")
                .stack("java")
                .build();
    }
}
