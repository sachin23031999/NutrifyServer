package com.sachin.nutrifyserver.handler

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet


class RtcWebSocketHandler : TextWebSocketHandler() {

    private val sessions: MutableSet<WebSocketSession> = CopyOnWriteArraySet()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        sessions.filter { it.isOpen && it.id != session.id }.forEach {
            it.sendMessage(message)
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }
}