package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.AboutPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutPhotoRepository extends JpaRepository<AboutPhoto, Long> {

    List<AboutPhoto> findAllByOrderByDisplayOrderAsc();
}
