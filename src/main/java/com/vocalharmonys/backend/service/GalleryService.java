package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.GalleryPhotoRequest;
import com.vocalharmonys.backend.dto.GalleryPhotoResponse;
import com.vocalharmonys.backend.dto.GalleryVideoRequest;
import com.vocalharmonys.backend.dto.GalleryVideoResponse;
import com.vocalharmonys.backend.entity.GalleryPhoto;
import com.vocalharmonys.backend.entity.GalleryVideo;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.GalleryPhotoRepository;
import com.vocalharmonys.backend.repository.GalleryVideoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Backs the "Galerie" page's two sections. Kept as one service (rather than
 * splitting photos/videos into their own services) since they're always
 * managed and displayed together and neither has enough behavior on its own
 * to justify a separate class.
 */
@Service
public class GalleryService {

    private final GalleryPhotoRepository photoRepository;
    private final GalleryVideoRepository videoRepository;

    public GalleryService(GalleryPhotoRepository photoRepository, GalleryVideoRepository videoRepository) {
        this.photoRepository = photoRepository;
        this.videoRepository = videoRepository;
    }

    public List<GalleryPhotoResponse> listPhotos() {
        return photoRepository.findAllByOrderByDisplayOrderAsc().stream().map(GalleryPhotoResponse::from).toList();
    }

    public GalleryPhotoResponse createPhoto(GalleryPhotoRequest request) {
        GalleryPhoto photo = new GalleryPhoto();
        photo.setLabel(request.label());
        photo.setImageUrl(request.imageUrl());
        photo.setDisplayOrder(request.displayOrder());
        return GalleryPhotoResponse.from(photoRepository.save(photo));
    }

    public GalleryPhotoResponse updatePhoto(Long id, GalleryPhotoRequest request) {
        GalleryPhoto photo = photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Photo introuvable : " + id));
        photo.setLabel(request.label());
        photo.setImageUrl(request.imageUrl());
        photo.setDisplayOrder(request.displayOrder());
        return GalleryPhotoResponse.from(photoRepository.save(photo));
    }

    public void deletePhoto(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Photo introuvable : " + id);
        }
        photoRepository.deleteById(id);
    }

    public List<GalleryVideoResponse> listVideos() {
        return videoRepository.findAllByOrderByDisplayOrderAsc().stream().map(GalleryVideoResponse::from).toList();
    }

    public GalleryVideoResponse createVideo(GalleryVideoRequest request) {
        GalleryVideo video = new GalleryVideo();
        video.setTitle(request.title());
        video.setYoutubeId(request.youtubeId());
        video.setDisplayOrder(request.displayOrder());
        return GalleryVideoResponse.from(videoRepository.save(video));
    }

    public GalleryVideoResponse updateVideo(Long id, GalleryVideoRequest request) {
        GalleryVideo video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vidéo introuvable : " + id));
        video.setTitle(request.title());
        video.setYoutubeId(request.youtubeId());
        video.setDisplayOrder(request.displayOrder());
        return GalleryVideoResponse.from(videoRepository.save(video));
    }

    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vidéo introuvable : " + id);
        }
        videoRepository.deleteById(id);
    }
}
