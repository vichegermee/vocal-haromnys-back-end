package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.ChoristerRequest;
import com.vocalharmonys.backend.dto.ChoristerResponse;
import com.vocalharmonys.backend.entity.Chorister;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.ChoristerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChoristerService {

    private final ChoristerRepository choristerRepository;

    public ChoristerService(ChoristerRepository choristerRepository) {
        this.choristerRepository = choristerRepository;
    }

    public List<ChoristerResponse> listAll() {
        return choristerRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(ChoristerResponse::from)
                .toList();
    }

    public ChoristerResponse create(ChoristerRequest request) {
        Chorister chorister = new Chorister();
        applyRequest(chorister, request);
        return ChoristerResponse.from(choristerRepository.save(chorister));
    }

    public ChoristerResponse update(Long id, ChoristerRequest request) {
        Chorister chorister = findOrThrow(id);
        applyRequest(chorister, request);
        return ChoristerResponse.from(choristerRepository.save(chorister));
    }

    public void delete(Long id) {
        if (!choristerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Choriste introuvable : " + id);
        }
        choristerRepository.deleteById(id);
    }

    private Chorister findOrThrow(Long id) {
        return choristerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choriste introuvable : " + id));
    }

    private void applyRequest(Chorister chorister, ChoristerRequest request) {
        chorister.setName(request.name());
        chorister.setVoicePart(request.voicePart());
        chorister.setDescription(request.description());
        chorister.setImageUrl(request.imageUrl());
        chorister.setDisplayOrder(request.displayOrder());
    }
}
