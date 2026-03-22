package com.projetodj.dj_thread.model;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class InstrumentTrack implements Runnable {
    private final String trackId;
    private final String soundPath;
    private final long interval;
    private final SimpMessagingTemplate template;

    private volatile boolean running = true;
    private volatile boolean paused = false;

    public InstrumentTrack(String trackId, String soundPath, long interval, SimpMessagingTemplate template) {
        this.trackId = trackId;
        this.soundPath = soundPath;
        this.interval = interval;
        this.template = template;
    }

    public synchronized void pauseTrack() { this.paused = true; }

    public synchronized void resumeTrack() {
        this.paused = false;
        this.notify();
    }

    public synchronized void stopTrack() {
        this.running = false;
        this.paused = false;
        this.notify();
    }

    @Override
    public void run() {
        try {
            while (running) {
                synchronized (this) {
                    while (paused && running) {
                        this.wait();
                    }
                }
                if (!running) break;

                // Envia o trackId para o frontend saber qual card disparar o som
                template.convertAndSend("/topic/sounds", trackId);

                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}