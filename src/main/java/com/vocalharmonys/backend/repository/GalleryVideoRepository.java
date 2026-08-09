package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.GalleryVideo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryVideoRepository extends JpaRepository<GalleryVideo, Long> {

    List<GalleryVideo> findAllByOrderByDisplayOrderAsc();
}
