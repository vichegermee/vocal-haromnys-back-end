package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

    // Loads the tracks in the same query instead of one extra SELECT per song.
    @EntityGraph(attributePaths = "tracks")
    List<Song> findAllByOrderByIdAsc();
}
