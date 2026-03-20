package com.projetodj.dj_thread.model;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public record InstrumentTask(String sessionId, String path, SimpMessagingTemplate template) implements Runnable {
    @Override
    public void run() {
        // Debug: You will see different thread IDs here now!
        System.out.println("[" + Thread.currentThread().getName() + "] Playing instrument: " + path);

        template.convertAndSend("/topic/sounds", path);
    }
}