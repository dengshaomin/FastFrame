package com.code.runtime.contants;

public class ComponentsContants {

    public static final String VIEW_DATA = "VIEW_DATA";

    public static class Name {

        private static int NAME_BASE = 0;

        private static final String NAME_STR = "COMPONENT_NAME_";

        public static final String Wechart = NAME_STR + NAME_BASE++;
    }

    public static class Action {

        private static final String ACTION_STR = "COMPONENT_ACTION_";

        private static int ACTION_BASE = 0;

        public static final String Share = ACTION_STR + ACTION_BASE++;
    }
}
