package com.catchtabling.reservation.presentation.v1;

import com.catchtabling.common.dto.DefaultResponseFormat;
import com.catchtabling.common.presentation.BaseAPIController;
import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "유저 예약 요청 V1")
@RequestMapping("api/v1/reservations")
@RestController
public class ReservationV1Controller extends BaseAPIController {
    private final ReservationService reservationService;

    public ReservationV1Controller(HttpServletRequest httpServletRequest,
                                   ReservationService reservationService) {
        super(httpServletRequest);
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(description = "식당 예약 등록을 요청한다.", summary = "식당 예약 요청")
    public ResponseEntity<DefaultResponseFormat> reserve(@RequestBody @Valid ReservationV1Request v1Request) {
        ReservationV1Response response = reservationService.reserve(v1Request);
        return responseEntityOk(response);
    }
}
