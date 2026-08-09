package com.vocalharmonys.backend.security;

import com.vocalharmonys.backend.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * How Spring Security looks up a user by username — both when {@code
 * AuthService} checks a login attempt, and when {@link
 * JwtAuthenticationFilter} re-hydrates the logged-in user from a token on
 * every subsequent request.
 */
@Service
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsernameIgnoreCase(username)
                .map(MemberPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun membre avec l'identifiant : " + username));
    }
}
