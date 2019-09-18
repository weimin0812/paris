package com.free.paris.tx;

import com.free.paris.util.MessageTracker;

public class TransactionManager {
    public void start() {
        System.out.println("start tx");
        MessageTracker.addMessage("start tx");
    }

    public void commit() {
        System.out.println("commit tx");
        MessageTracker.addMessage("commit tx");
    }

    public void rollBack() {
        System.out.println("roll back tx");
        MessageTracker.addMessage("roll back tx");
    }
}
