package com.barauna.DEVinHouse.amqp.dto;

public class AMQPMessage<T> {
    private Integer tries;
    private T body;

    public AMQPMessage() {
        this.tries = 0;
    }

    public AMQPMessage(T body) {
        this();
        this.body = body;
    }

    public Integer getTries() {
        return tries;
    }

    public void setTries(Integer tries) {
        this.tries = tries;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void incrementTries() {
        this.tries += 1;
    }
}
