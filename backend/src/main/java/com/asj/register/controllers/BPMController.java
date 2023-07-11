package com.asj.register.controllers;
import com.asj.register.services.interfaces.IBPMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/BPM")
public class BPMController {
    private final IBPMService bpmService;
    @GetMapping("/getPayload")
    public ResponseEntity<Map<String, String>> getPayload(@RequestParam String bpmWorklistTaskId, @RequestParam String bpmWorklistContext){
        Map<String, String> solicitudBPM = bpmService.getPayload(bpmWorklistTaskId, bpmWorklistContext);
        return ResponseEntity.ok(solicitudBPM);
    }
}
