package org.firstinspires.ftc.teamcode.event.impl;

import org.firstinspires.ftc.teamcode.event.IEvent;

public class MessageEvent implements IEvent {
    static class Message {
        public final String id;
        public final int length;
        public final byte[] data;

        public Message(String id, byte[] data) {
            this.id = id;
            this.length = data.length;
            this.data = data;
        }
    }

    private Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
