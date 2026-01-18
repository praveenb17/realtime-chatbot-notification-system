package com.praveen.chatbot.service;

import com.praveen.chatbot.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.praveen.chatbot.dto.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ChatMessage msg = new ChatMessage(MessageType.SYSTEM, "server", null, "CONNECTED");
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage incoming = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        ChatMessage outgoing = new ChatMessage(
                MessageType.CHAT,
                incoming.getFromUser(),
                incoming.getToUser(),
                "ECHO: " + incoming.getContent()
        );

        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(outgoing)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // no-op for now
    }
}
