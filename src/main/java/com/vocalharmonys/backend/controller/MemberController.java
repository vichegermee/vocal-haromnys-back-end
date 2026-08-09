package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.CreateMemberRequest;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.service.MemberService;
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
 * Member account management. Every route here requires an existing member to
 * be logged in already (see SecurityConfig's default "anyRequest().authenticated()"
 * rule) — there's no public self-registration, accounts are created by
 * someone already inside the espace membre.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberResponse> listAll() {
        return memberService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@Valid @RequestBody CreateMemberRequest request) {
        return memberService.create(request);
    }
}
