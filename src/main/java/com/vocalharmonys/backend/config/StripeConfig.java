package com.vocalharmonys.backend.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The Stripe Java SDK reads its API key from a static field rather than a
 * client instance, so this just sets it once at startup from {@code
 * app.stripe.secret-key} — every other class (StripeCheckoutService,
 * webhook handling) then calls the SDK without needing the key passed in.
 */
@Configuration
public class StripeConfig {

    public StripeConfig(@Value("${app.stripe.secret-key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }
}
