package com.catchtabling.common.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ping")
@RestController
public class PingController extends BaseAPIController {

    public PingController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @GetMapping
    @Operation(description = "통신 테스트를 한다.", summary = "통신 테스트")
    public String pong() {
        return "ping-pong !";
    }
}
