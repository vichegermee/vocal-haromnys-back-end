package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.AboutPhotoRequest;
import com.vocalharmonys.backend.dto.AboutPhotoResponse;
import com.vocalharmonys.backend.entity.AboutPhoto;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.AboutPhotoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the rotating carousel next to the "Notre histoire" text on the About page. */
@Service
public class AboutPhotoService {

    private final AboutPhotoRepository aboutPhotoRepository;

    public AboutPhotoService(AboutPhotoRepository aboutPhotoRepository) {
        this.aboutPhotoRepository = aboutPhotoRepository;
    }

    public List<AboutPhotoResponse> listAll() {
        return aboutPhotoRepository.findAllByOrderByDisplayOrderAsc().stream().map(AboutPhotoResponse::from).toList();
    }

    public AboutPhotoResponse create(AboutPhotoRequest request) {
        AboutPhoto photo = new AboutPhoto();
        photo.setImageUrl(request.imageUrl());
        photo.setDisplayOrder(request.displayOrder());
        return AboutPhotoResponse.from(aboutPhotoRepository.save(photo));
    }

    public AboutPhotoResponse update(Long id, AboutPhotoRequest request) {
        AboutPhoto photo = aboutPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Photo introuvable : " + id));
        photo.setImageUrl(request.imageUrl());
        photo.setDisplayOrder(request.displayOrder());
        return AboutPhotoResponse.from(aboutPhotoRepository.save(photo));
    }

    public void delete(Long id) {
        if (!aboutPhotoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Photo introuvable : " + id);
        }
        aboutPhotoRepository.deleteById(id);
    }
}
