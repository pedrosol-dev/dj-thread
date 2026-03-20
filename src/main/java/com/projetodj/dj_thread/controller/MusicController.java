package com.projetodj.dj_thread.controller;

import com.projetodj.dj_thread.service.SoundEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
public class MusicController {

    @Autowired
    private SoundEngine engine;

    @GetMapping("/api/files")
    public Map<String, List<String>> getFiles() {
        Map<String, List<String>> tree = new HashMap<>();
        File root = new File("src/main/resources/static/sounds");
        if (root.exists()) {
            for (File folder : root.listFiles(File::isDirectory)) {
                List<String> files = Arrays.stream(folder.listFiles())
                        .map(File::getName).filter(n -> n.endsWith(".mp3")).toList();
                tree.put(folder.getName(), files);
            }
        }
        return tree;
    }

    @MessageMapping("/play")
    public void play(@Payload Map<String, Object> payload, SimpMessageHeaderAccessor sha) {
        engine.startThread(sha.getSessionId(),
            payload.get("folder") + "/" + payload.get("file"),
            Long.parseLong(payload.get("interval").toString()));
    }

    @MessageMapping("/stop")
    public void stop(SimpMessageHeaderAccessor sha) {
        engine.stopUser(sha.getSessionId());
    }
}