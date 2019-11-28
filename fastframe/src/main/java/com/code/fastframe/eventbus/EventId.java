package com.code.fastframe.eventbus;

public class EventId {

    private static int baseId = 0;

    public static class System {

        public static final int NET_CHANGE = ++baseId;
    }


}
