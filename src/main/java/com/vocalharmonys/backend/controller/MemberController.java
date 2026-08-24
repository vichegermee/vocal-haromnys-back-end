package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.BulkImportMemberRequest;
import com.vocalharmonys.backend.dto.BulkImportResult;
import com.vocalharmonys.backend.dto.ChangePasswordRequest;
import com.vocalharmonys.backend.dto.CreateMemberRequest;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.security.MemberPrincipal;
import com.vocalharmonys.backend.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Member account management. Listing, creating, deleting, and bulk-importing
 * accounts are super-admin-only (see SecurityConfig — {@code hasRole("ADMIN")}
 * on this path). Changing your own password is the one route here open to
 * any logged-in member, admin or not.
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

    @PostMapping("/bulk-import")
    public List<BulkImportResult> bulkImport(@Valid @RequestBody BulkImportMemberRequest request) {
        return memberService.bulkImport(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal MemberPrincipal principal) {
        memberService.delete(id, principal.member().getId());
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        memberService.changePassword(principal.member().getId(), request);
    }
}
