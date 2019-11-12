package com.darksideofthedev.microservices.limitsservice;

import com.darksideofthedev.microservices.limitsservice.config.LimitsConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitsConfigurationController {

    private final LimitsConfiguration limitsConfiguration;

    @GetMapping("/limits")
    public LimitsConfiguration getLimitsFromConfiguration() {
        return limitsConfiguration;
    }
}
