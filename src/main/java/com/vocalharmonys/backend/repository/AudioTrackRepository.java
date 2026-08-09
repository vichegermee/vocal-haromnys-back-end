package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.AudioTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioTrackRepository extends JpaRepository<AudioTrack, Long> {
}
