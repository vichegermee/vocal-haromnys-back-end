package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.BulkImportMemberRequest;
import com.vocalharmonys.backend.dto.BulkImportResult;
import com.vocalharmonys.backend.dto.ChangePasswordRequest;
import com.vocalharmonys.backend.dto.CreateMemberRequest;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.email.EmailService;
import com.vocalharmonys.backend.entity.Member;
import com.vocalharmonys.backend.entity.Role;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.MemberRepository;
import com.vocalharmonys.backend.util.PasswordGenerator;
import com.vocalharmonys.backend.util.UsernameGenerator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Account management, as distinct from {@code AuthService} (which only
 * checks credentials and issues tokens). Every account created here gets a
 * generated username and password — neither is ever supplied by the admin
 * filling the form, and the plaintext password never comes back in any
 * response; it only ever travels through {@link EmailService#sendMemberCredentials}.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final UsernameGenerator usernameGenerator;
    private final EmailService emailService;

    public MemberService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            PasswordGenerator passwordGenerator,
            UsernameGenerator usernameGenerator,
            EmailService emailService
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.usernameGenerator = usernameGenerator;
        this.emailService = emailService;
    }

    public List<MemberResponse> listAll() {
        return memberRepository.findAll().stream().map(MemberResponse::from).toList();
    }

    public MemberResponse create(CreateMemberRequest request) {
        if (memberRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(CONFLICT, "Cette adresse email a déjà un compte.");
        }
        Member member = createMember(request.firstName(), request.lastName(), request.email(),
                request.role() == null ? Role.MEMBER : request.role());
        return MemberResponse.from(member);
    }

    public List<BulkImportResult> bulkImport(BulkImportMemberRequest request) {
        return request.members().stream()
                .map(row -> {
                    try {
                        if (memberRepository.existsByEmailIgnoreCase(row.email())) {
                            return BulkImportResult.failure(row.firstName(), row.lastName(), row.email(),
                                    "Cette adresse email a déjà un compte.");
                        }
                        createMember(row.firstName(), row.lastName(), row.email(), Role.MEMBER);
                        return BulkImportResult.success(row.firstName(), row.lastName(), row.email());
                    } catch (Exception e) {
                        return BulkImportResult.failure(row.firstName(), row.lastName(), row.email(), "Erreur inattendue.");
                    }
                })
                .toList();
    }

    public void delete(Long id, Long currentMemberId) {
        if (id.equals(currentMemberId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Vous ne pouvez pas supprimer votre propre compte.");
        }
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membre introuvable : " + id);
        }
        memberRepository.deleteById(id);
    }

    public void changePassword(Long memberId, ChangePasswordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Membre introuvable : " + memberId));

        if (!passwordEncoder.matches(request.currentPassword(), member.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mot de passe actuel incorrect.");
        }

        member.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        memberRepository.save(member);
    }

    private Member createMember(String firstName, String lastName, String email, Role role) {
        String fullName = (lastName == null || lastName.isBlank()) ? firstName : firstName + " " + lastName;
        String username = usernameGenerator.generate(firstName, lastName);
        String plaintextPassword = passwordGenerator.generate();

        Member member = new Member();
        member.setUsername(username);
        member.setPasswordHash(passwordEncoder.encode(plaintextPassword));
        member.setFullName(fullName);
        member.setEmail(email);
        member.setRole(role);
        member = memberRepository.save(member);

        emailService.sendMemberCredentials(email, fullName, username, plaintextPassword);
        return member;
    }
}
