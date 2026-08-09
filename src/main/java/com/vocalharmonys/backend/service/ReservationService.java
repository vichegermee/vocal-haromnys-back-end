package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.ReservationRequest;
import com.vocalharmonys.backend.dto.ReservationResponse;
import com.vocalharmonys.backend.entity.Reservation;
import com.vocalharmonys.backend.repository.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Réserver une prestation" form on the Contact page. */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> listAll() {
        return reservationRepository.findAllByOrderByCreatedAtDesc().stream().map(ReservationResponse::from).toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setEventType(request.eventType());
        reservation.setDesiredDate(request.desiredDate());
        reservation.setBudget(request.budget());
        reservation.setLocation(request.location());
        reservation.setChoristerCount(request.choristerCount());
        reservation.setMessage(request.message());
        reservation.setRequesterName(request.requesterName());
        reservation.setRequesterEmail(request.requesterEmail());
        return ReservationResponse.from(reservationRepository.save(reservation));
    }
}
