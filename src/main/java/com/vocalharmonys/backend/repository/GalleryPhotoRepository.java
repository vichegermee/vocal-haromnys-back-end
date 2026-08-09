package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.GalleryPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Long> {

    List<GalleryPhoto> findAllByOrderByDisplayOrderAsc();
}
