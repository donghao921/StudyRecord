package com.dongh.sample.event;

/**
 * @author chenxz
 * @date 2018/9/1
 * @desc 测试 EventBus
 */
public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
