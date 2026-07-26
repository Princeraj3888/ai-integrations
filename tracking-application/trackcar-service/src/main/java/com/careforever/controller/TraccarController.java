package com.careforever;

import com.careforever.service.TraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/traccar")
@RequiredArgsConstructor
public class TraccarController {

    private final TraccarService traccarService;

    @GetMapping("/devices")
    public String devices(@RequestHeader("Cookie") String cookie){

        return traccarService.getDevices(cookie);

    }

}
