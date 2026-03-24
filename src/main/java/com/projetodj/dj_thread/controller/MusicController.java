package com.projetodj.dj_thread.controller;

import com.projetodj.dj_thread.service.SoundEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.*;

@Controller
public class MusicController {

    @Autowired
    private SoundEngine engine;

    @MessageMapping("/play")
    public void play(@Payload Map<String, Object> payload, SimpMessageHeaderAccessor sha) {
        String trackId = (String) payload.get("trackId");
        String soundPath = payload.get("folder") + "/" + payload.get("file");
        long interval = Long.parseLong(payload.get("interval").toString());
        engine.play(sha.getSessionId(), trackId, soundPath, interval);
    }

    @MessageMapping("/pause")
    public void pause(@Payload Map<String, String> payload, SimpMessageHeaderAccessor sha) {
        engine.pause(sha.getSessionId(), payload.get("trackId"));
    }

    @MessageMapping("/resume")
    public void resume(@Payload Map<String, String> payload, SimpMessageHeaderAccessor sha) {
        engine.resume(sha.getSessionId(), payload.get("trackId"));
    }

    @MessageMapping("/stop")
    public void stop(@Payload Map<String, String> payload, SimpMessageHeaderAccessor sha) {
        engine.stop(sha.getSessionId(), payload.get("trackId"));
    }

    @GetMapping("/api/files")
    @ResponseBody
    public Map<String, List<String>> getFiles() {
        Map<String, List<String>> tree = new HashMap<>();
        File root = new File("src/main/resources/static/sounds");
        if (root.exists() && root.isDirectory()) {
            File[] folders = root.listFiles(File::isDirectory);
            if (folders != null) {
                for (File folder : folders) {
                    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
                    if (files != null) {
                        List<String> names = Arrays.stream(files).map(File::getName).toList();
                        tree.put(folder.getName(), names);
                    }
                }
            }
        }
        return tree;
    }
}