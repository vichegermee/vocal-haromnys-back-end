package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.CreateMemberRequest;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.entity.Member;
import com.vocalharmonys.backend.repository.MemberRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Account management, as distinct from {@code AuthService} (which only
 * checks credentials and issues tokens). This is where a new member account
 * gets created — the password is hashed here, once, before it's ever
 * persisted.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MemberResponse> listAll() {
        return memberRepository.findAll().stream().map(MemberResponse::from).toList();
    }

    public MemberResponse create(CreateMemberRequest request) {
        if (memberRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new ResponseStatusException(CONFLICT, "Cet identifiant est déjà utilisé.");
        }

        Member member = new Member();
        member.setUsername(request.username());
        member.setPasswordHash(passwordEncoder.encode(request.password()));
        member.setFullName(request.fullName());

        return MemberResponse.from(memberRepository.save(member));
    }
}
