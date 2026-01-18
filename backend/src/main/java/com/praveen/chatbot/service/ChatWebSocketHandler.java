package com.praveen.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praveen.chatbot.dto.ChatMessage;
import com.praveen.chatbot.dto.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // userId -> session
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        send(session, new ChatMessage(MessageType.SYSTEM, "server", null, "CONNECTED"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage incoming = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        if (incoming.getType() == MessageType.REGISTER) {
            String user = incoming.getFromUser();
            if (user == null || user.isBlank()) {
                send(session, new ChatMessage(MessageType.ERROR, "server", null, "fromUser is required for REGISTER"));
                return;
            }
            sessions.put(user, session);
            send(session, new ChatMessage(MessageType.ACK, "server", user, "REGISTERED"));
            return;
        }

        if (incoming.getType() == MessageType.CHAT) {
            String toUser = incoming.getToUser();
            if (toUser == null || toUser.isBlank()) {
                send(session, new ChatMessage(MessageType.ERROR, "server", incoming.getFromUser(), "toUser is required"));
                return;
            }

            WebSocketSession recipientSession = sessions.get(toUser);
            if (recipientSession == null || !recipientSession.isOpen()) {
                send(session, new ChatMessage(MessageType.ERROR, "server", incoming.getFromUser(), "USER_OFFLINE"));
                return;
            }

            // deliver to recipient
            send(recipientSession, incoming);

            // ack back to sender
            send(session, new ChatMessage(MessageType.ACK, "server", incoming.getFromUser(), "DELIVERED"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // remove any user mapped to this session
        sessions.entrySet().removeIf(e -> e.getValue().getId().equals(session.getId()));
    }

    private void send(WebSocketSession session, ChatMessage msg) throws Exception {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
    }
}
