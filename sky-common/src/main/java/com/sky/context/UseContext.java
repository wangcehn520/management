package com.sky.context;

public class UseContext {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setValue(String value) {
        threadLocal.set(value);
    }

    public static String getValue() {
        return threadLocal.get();
    }

    public static void removeValue() {
        threadLocal.remove();
    }

}
