package com.vocalharmonys.backend.email;

import com.vocalharmonys.backend.entity.CdOrder;
import com.vocalharmonys.backend.entity.Donation;
import com.vocalharmonys.backend.entity.JoinApplication;
import com.vocalharmonys.backend.entity.Reservation;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Sends a recap email to the admin inbox for every reservation request, join
 * application, and paid donation. Every method here catches and logs — never
 * rethrows — so a Resend outage can never fail or slow down the visitor's
 * request; {@code @Async} additionally hands the whole call off to a small
 * dedicated thread pool (see AsyncConfig) so a merely *slow* Resend call
 * doesn't hold the request thread either.
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ResendClient resendClient;
    private final String adminRecipient;
    private final String frontendBaseUrl;

    public EmailService(
            ResendClient resendClient,
            @Value("${app.mail.admin-recipient}") String adminRecipient,
            @Value("${app.frontend.base-url}") String frontendBaseUrl
    ) {
        this.resendClient = resendClient;
        this.adminRecipient = adminRecipient;
        this.frontendBaseUrl = frontendBaseUrl;
    }

    @Async
    public void notifyReservation(Reservation reservation) {
        String subject = "Nouvelle demande de prestation — " + reservation.getRequesterName();
        String html = "<h2>Nouvelle demande de prestation</h2>"
                + row("Type d'événement", reservation.getEventType())
                + row("Date souhaitée", String.valueOf(reservation.getDesiredDate()))
                + row("Lieu", reservation.getLocation())
                + row("Budget", reservation.getBudget())
                + row("Nombre de choristes souhaité", reservation.getChoristerCount())
                + row("Message", reservation.getMessage())
                + row("Nom", reservation.getRequesterName())
                + row("Email", reservation.getRequesterEmail());
        sendSafely("reservation", reservation.getId(), subject, html);
    }

    @Async
    public void notifyJoinApplication(JoinApplication application) {
        String subject = "Nouvelle candidature choriste — " + application.getApplicantName();
        String html = "<h2>Nouvelle candidature pour rejoindre la chorale</h2>"
                + row("Pupitre", String.valueOf(application.getVoicePart()))
                + row("Expérience", application.getExperience())
                + row("Disponibilités", application.getAvailability())
                + row("Extrait audio", application.getAudioLink())
                + row("Motivation", application.getMotivation())
                + row("Nom", application.getApplicantName())
                + row("Email", application.getApplicantEmail());
        sendSafely("join application", application.getId(), subject, html);
    }

    @Async
    public void notifyDonationPaid(Donation donation) {
        String subject = "Nouveau don reçu — " + donation.getAmount() + " €";
        String html = "<h2>Nouveau don confirmé</h2>"
                + row("Montant", donation.getAmount() + " €")
                + row("Donateur", donation.getDonorName())
                + row("Email", donation.getDonorEmail())
                + row("Payé le", donation.getPaidAt() != null ? donation.getPaidAt().format(DATE_TIME) : "-");
        sendSafely("donation", donation.getId(), subject, html);
    }

    @Async
    public void notifyCdOrderPaid(CdOrder order) {
        String subject = "Nouvelle commande CD payée — " + order.getCdTitleSnapshot();
        String address = order.getShippingStreet() + ", " + order.getShippingPostalCode() + " "
                + order.getShippingCity() + ", " + order.getShippingCountry();
        String html = "<h2>Nouvelle commande de CD confirmée</h2>"
                + row("Album", order.getCdTitleSnapshot())
                + row("Quantité", String.valueOf(order.getQuantity()))
                + row("Prix unitaire", order.getUnitPriceSnapshot() + " €")
                + row("Livraison", order.getShippingOption().getLabel() + " (" + order.getShippingCost() + " €)")
                + row("Total payé", order.getTotalAmount() + " €")
                + row("Client", order.getCustomerName())
                + row("Email", order.getCustomerEmail())
                + row("Téléphone", order.getCustomerPhone())
                + row("Adresse de livraison", address)
                + row("Message", order.getMessage())
                + row("Payé le", order.getPaidAt() != null ? order.getPaidAt().format(DATE_TIME) : "-");
        sendSafely("CD order", order.getId(), subject, html);
    }

    /**
     * Sends a newly created member their login credentials. {@code
     * plaintextPassword} only ever exists in memory between {@code
     * MemberService.create()} generating it and this call — it is not
     * logged here (see {@code sendSafely}, which never includes the html
     * body/subject in its log line) or anywhere else.
     */
    @Async
    public void sendMemberCredentials(String toEmail, String fullName, String username, String plaintextPassword) {
        String subject = "Vos identifiants — Espace membre Vocal Harmony's";
        String html = "<h2>Bienvenue " + escapeHtml(fullName) + " !</h2>"
                + "<p>Un compte vient d'être créé pour vous sur l'espace membre de Vocal Harmony's.</p>"
                + row("Identifiant", username)
                + row("Mot de passe provisoire", plaintextPassword)
                + "<p>Connectez-vous ici : <a href=\"" + frontendBaseUrl + "/connexion\">" + frontendBaseUrl + "/connexion</a></p>"
                + "<p>Nous vous invitons à changer ce mot de passe dès votre première connexion.</p>";
        sendSafelyTo(List.of(toEmail), "member credentials", null, subject, html);
    }

    private void sendSafely(String kind, Long id, String subject, String html) {
        sendSafelyTo(List.of(adminRecipient), kind, id, subject, html);
    }

    private void sendSafelyTo(List<String> to, String kind, Long id, String subject, String html) {
        try {
            resendClient.send(to, subject, html);
        } catch (Exception e) {
            log.error("Failed to send {} email for id={}", kind, id, e);
        }
    }

    private static String row(String label, String value) {
        String safeValue = (value == null || value.isBlank()) ? "—" : escapeHtml(value);
        return "<p><strong>" + escapeHtml(label) + " :</strong> " + safeValue + "</p>";
    }

    /** Every value interpolated here came from an unauthenticated public form — escape before embedding in HTML. */
    private static String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
