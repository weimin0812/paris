package com.free.paris.util;

import java.util.ArrayList;
import java.util.List;

public class MessageTracker {
    private static List<String> MESSAGES = new ArrayList<>();

    public static void addMessage(String message) {
        MESSAGES.add(message);
    }

    public static void clearMessages() {
        MESSAGES.clear();
    }

    public static List<String> getMessages() {
        return MESSAGES;
    }
}
