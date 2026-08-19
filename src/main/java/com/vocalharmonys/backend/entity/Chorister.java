package com.vocalharmonys.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** One choir member's public profile, shown on the "Choristes" page. */
@Entity
@Table(name = "choristers")
public class Chorister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    // Free text, not the 4-value VoicePart enum: the real roster also
    // includes non-singing roles (Manager, Batterie, Guitare, Pianiste),
    // and this field is whatever the "Nom - Pupitre" photo file name says.
    @Column(name = "voice_part", nullable = false, length = 20)
    private String voicePart;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    public Chorister() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoicePart() {
        return voicePart;
    }

    public void setVoicePart(String voicePart) {
        this.voicePart = voicePart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
