package com.vocalharmonys.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * A song in the rehearsal repertoire. Only visible to logged-in members
 * (see SecurityConfig) — same as the "Répertoire" page today.
 */
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    /** e.g. "Chorale complète", "SATB", "Soprano / Alto". */
    @Column(nullable = false, length = 150)
    private String voicing;

    /** e.g. "Sol majeur". Named musicalKey — "key" clashes with SQL/JPQL. */
    @Column(name = "musical_key", nullable = false, length = 50)
    private String musicalKey;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private List<AudioTrack> tracks = new ArrayList<>();

    public Song() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoicing() {
        return voicing;
    }

    public void setVoicing(String voicing) {
        this.voicing = voicing;
    }

    public String getMusicalKey() {
        return musicalKey;
    }

    public void setMusicalKey(String musicalKey) {
        this.musicalKey = musicalKey;
    }

    public List<AudioTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<AudioTrack> tracks) {
        this.tracks = tracks;
    }
}
