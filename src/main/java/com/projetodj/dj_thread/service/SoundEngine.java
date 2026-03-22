package com.projetodj.dj_thread.service;

import com.projetodj.dj_thread.model.InstrumentTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SoundEngine {

    @Autowired
    private SimpMessagingTemplate template;

    private final Map<String, Map<String, InstrumentTrack>> userTracks = new ConcurrentHashMap<>();

    public void play(String sessionId, String trackId, String soundPath, long ms) {
        if (trackId == null) return;
        InstrumentTrack track = new InstrumentTrack(trackId, soundPath, ms, template);
        userTracks.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>()).put(trackId, track);
        new Thread(track, "DJ-Thread-" + trackId).start();
    }

    public void pause(String sessionId, String trackId) {
        getTrack(sessionId, trackId).ifPresent(InstrumentTrack::pauseTrack);
    }

    public void resume(String sessionId, String trackId) {
        getTrack(sessionId, trackId).ifPresent(InstrumentTrack::resumeTrack);
    }

    public void stop(String sessionId, String trackId) {
        if (trackId == null) return;
        Map<String, InstrumentTrack> tracks = userTracks.get(sessionId);
        if (tracks != null && tracks.containsKey(trackId)) {
            tracks.get(trackId).stopTrack();
            tracks.remove(trackId);
        }
    }

    public void stopAll(String sessionId) {
        Map<String, InstrumentTrack> tracks = userTracks.remove(sessionId);
        if (tracks != null) {
            tracks.values().forEach(InstrumentTrack::stopTrack);
        }
    }

    private Optional<InstrumentTrack> getTrack(String sessionId, String trackId) {
        if (trackId == null || sessionId == null) return Optional.empty();
        return Optional.ofNullable(userTracks.get(sessionId)).map(m -> m.get(trackId));
    }
}