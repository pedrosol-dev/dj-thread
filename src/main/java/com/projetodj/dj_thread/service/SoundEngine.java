package com.projetodj.dj_thread.service;

import com.projetodj.dj_thread.model.InstrumentTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.*;

@Service
public class SoundEngine {
    // A pool that allows many independent instrument threads
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
    private final Map<String, List<ScheduledFuture<?>>> userThreads = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate template;

    public void startThread(String sessionId, String path, long ms) {
        ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(
            new InstrumentTask(sessionId, path, template), 0, ms, TimeUnit.MILLISECONDS
        );
        userThreads.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(task);
        System.out.println("Started: " + path + " every " + ms + "ms for " + sessionId);
    }

    public void stopUser(String sessionId) {
        List<ScheduledFuture<?>> tasks = userThreads.remove(sessionId);
        if (tasks != null) {
            System.out.println("Killing all threads for session: " + sessionId);
            tasks.forEach(t -> t.cancel(false));
        }
    }
}