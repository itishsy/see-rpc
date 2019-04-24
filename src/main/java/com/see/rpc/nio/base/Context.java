package com.see.rpc.nio.base;

public final class Context {

    private static InheritableThreadLocal<String> session = new InheritableThreadLocal<>();

    public static void set(String sessionKey) {
        session.set(sessionKey);
    }

    public static String get() {
        return session.get();
    }

}
