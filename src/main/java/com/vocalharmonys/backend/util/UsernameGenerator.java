package com.vocalharmonys.backend.util;

import com.vocalharmonys.backend.repository.MemberRepository;
import java.text.Normalizer;
import org.springframework.stereotype.Component;

/**
 * Turns a chorister's name into a login username ("Marie Dupont" ->
 * "marie.dupont"), appending a numeric suffix if that's already taken.
 */
@Component
public class UsernameGenerator {

    private final MemberRepository memberRepository;

    public UsernameGenerator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String generate(String firstName, String lastName) {
        String base = slugify((lastName == null || lastName.isBlank()) ? firstName : firstName + " " + lastName);
        if (base.isBlank()) {
            base = "membre";
        }

        String candidate = base;
        int suffix = 2;
        while (memberRepository.existsByUsernameIgnoreCase(candidate)) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }

    private String slugify(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // strip accents
        return normalized
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", ".");
    }
}
