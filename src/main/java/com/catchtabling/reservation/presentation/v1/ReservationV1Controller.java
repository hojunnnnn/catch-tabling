package com.catchtabling.reservation.presentation.v1;

import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/reservations")
@Tag(name = "유저 예약 요청 V1")
@RestController
public class ReservationV1Controller {
    private final ReservationService reservationService;

    @PostMapping
    @Operation(description = "식당 예약 등록을 요청한다.", summary = "식당 예약 요청")
    public ResponseEntity<ReservationV1Response> reserve(@RequestBody @Valid ReservationV1Request v1Request) {
        ReservationV1Response response = reservationService.reserve(v1Request);
        return ResponseEntity.ok()
                .body(response);
    }
}
