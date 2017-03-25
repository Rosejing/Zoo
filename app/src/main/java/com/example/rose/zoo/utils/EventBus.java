package com.example.rose.zoo.utils;

import com.squareup.otto.Bus;

/**
 * Created by Rose on 12/28/2016.
 */

public class EventBus extends Bus {
    private static final EventBus bus = new EventBus();

    public static Bus getInstance() {
        return bus;
    }
    private EventBus() {}
}
