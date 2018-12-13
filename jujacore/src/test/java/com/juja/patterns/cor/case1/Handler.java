package com.juja.patterns.cor.case1;

public abstract class Handler {

    private Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public final void handleRequest(Request request) {
        if (!handle(request) && successor != null) {
            successor.handleRequest(request);
        }
    }

    protected abstract boolean handle(Request request);
}
