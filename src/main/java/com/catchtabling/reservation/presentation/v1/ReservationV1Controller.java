package com.catchtabling.reservation.presentation.v1;

import com.catchtabling.common.dto.DefaultResponseFormat;
import com.catchtabling.common.presentation.BaseAPIController;
import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.dto.MemberReservationResponse;
import com.catchtabling.reservation.dto.MemberReservationsResponse;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{reservationNum}")
    @Operation(description = "식당 예약 상세 내역을 조회한다.", summary = "식당 예약 상세 내역 조회")
    public ResponseEntity<DefaultResponseFormat> getDetails(@PathVariable String reservationNum) {
        MemberReservationResponse response = reservationService.getDetails(reservationNum);

        return responseEntityOk(response);
    }

    @GetMapping
    @Operation(description = "유저의 식당 예약 목록을 조회한다.", summary = "유저 식당 예약 목록 조회")
    public ResponseEntity<DefaultResponseFormat> getList(Long memberId,
                                                         @RequestParam(defaultValue = "0") int state,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "visitDateTime"));
        MemberReservationsResponse response = reservationService.getReservationList(
                memberId,
                EntryState.from(state),
                pageable);

        return responseEntityOk(response);
    }
}
