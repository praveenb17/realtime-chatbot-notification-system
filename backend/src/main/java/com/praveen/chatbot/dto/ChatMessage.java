package com.praveen.chatbot.dto;

public class ChatMessage {
    private MessageType type;
    private String fromUser;
    private String toUser;
    private String content;

    public ChatMessage() {}

    public ChatMessage(MessageType type, String fromUser, String toUser, String content) {
        this.type = type;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
