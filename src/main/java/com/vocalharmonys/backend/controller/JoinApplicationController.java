package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.JoinApplicationRequest;
import com.vocalharmonys.backend.dto.JoinApplicationResponse;
import com.vocalharmonys.backend.service.JoinApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The "Rejoindre la chorale" form on the Contact page. Submitting an
 * application is public; listing applications back requires a logged-in
 * member — see SecurityConfig.
 */
@RestController
@RequestMapping("/api/join-applications")
public class JoinApplicationController {

    private final JoinApplicationService joinApplicationService;

    public JoinApplicationController(JoinApplicationService joinApplicationService) {
        this.joinApplicationService = joinApplicationService;
    }

    @GetMapping
    public List<JoinApplicationResponse> listAll() {
        return joinApplicationService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JoinApplicationResponse create(@Valid @RequestBody JoinApplicationRequest request) {
        return joinApplicationService.create(request);
    }
}
