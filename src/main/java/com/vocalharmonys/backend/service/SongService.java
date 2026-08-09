package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.AudioTrackRequest;
import com.vocalharmonys.backend.dto.SongRequest;
import com.vocalharmonys.backend.dto.SongResponse;
import com.vocalharmonys.backend.entity.AudioTrack;
import com.vocalharmonys.backend.entity.Song;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.SongRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Owns the répertoire (songs) and their audio tracks together — a song's
 * tracks are always created/replaced as part of the song itself, there's no
 * separate "add one track" endpoint (see SongController).
 */
@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Transactional(readOnly = true)
    public List<SongResponse> listAll() {
        return songRepository.findAllByOrderByIdAsc().stream().map(SongResponse::from).toList();
    }

    @Transactional
    public SongResponse create(SongRequest request) {
        Song song = new Song();
        applyRequest(song, request);
        return SongResponse.from(songRepository.save(song));
    }

    @Transactional
    public SongResponse update(Long id, SongRequest request) {
        Song song = findOrThrow(id);
        applyRequest(song, request);
        return SongResponse.from(songRepository.save(song));
    }

    @Transactional
    public void delete(Long id) {
        if (!songRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chant introuvable : " + id);
        }
        songRepository.deleteById(id);
    }

    private Song findOrThrow(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chant introuvable : " + id));
    }

    private void applyRequest(Song song, SongRequest request) {
        song.setTitle(request.title());
        song.setVoicing(request.voicing());
        song.setMusicalKey(request.musicalKey());

        // orphanRemoval=true on Song.tracks means clearing + re-adding deletes
        // the tracks that are no longer in the request and inserts the rest.
        song.getTracks().clear();
        for (AudioTrackRequest trackRequest : request.tracks()) {
            AudioTrack track = new AudioTrack();
            track.setSong(song);
            track.setTrackType(trackRequest.trackType());
            track.setFileUrl(trackRequest.fileUrl());
            song.getTracks().add(track);
        }
    }
}
