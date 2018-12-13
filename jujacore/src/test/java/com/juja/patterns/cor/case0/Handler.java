package com.juja.patterns.cor.case0;

public class Handler {
    // тут хранится следующий элемент
    protected Handler successor;
    // а это тиип обрабатываемого реквеста
    private Class aClass;
    // конфигурим хэндлер
    public Handler(Class aClass) {
        this.aClass = aClass;
    }
    // так устанавливаем
    public void  setSuccessor(Handler successor) {
        this.successor = successor;
    }

    // обработка сигнала
    public void handlerRequest(Request request) {
        if (request.getMessage().getClass().equals(aClass)) {
            System.out.printf("Request %s processed by Handler#%s%n", request, hashCode());
        } else if (successor != null) {
            successor.handlerRequest(request);
        }
    }
}
