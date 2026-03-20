package com.projetodj.dj_thread.config;

import com.projetodj.dj_thread.service.SoundEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener {
    @Autowired private SoundEngine engine;

    @EventListener
    public void onDisconnect(SessionDisconnectEvent e) {
        engine.stopUser(e.getSessionId());
    }
}