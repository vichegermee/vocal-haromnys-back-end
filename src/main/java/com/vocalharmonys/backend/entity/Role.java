package com.vocalharmonys.backend.entity;

/**
 * Every seeded account is MEMBER today — none of the authorization rules in
 * SecurityConfig check the role yet. It's modeled now so an admin-only
 * restriction can be added later without a schema change.
 */
public enum Role {
    MEMBER,
    ADMIN
}
