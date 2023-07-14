package com.asj.register.controllers;
import com.asj.register.services.interfaces.IBPMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/BPM")
public class BPMController {
    private final IBPMService bpmService;
    @GetMapping("/payload")
    public ResponseEntity<Map<String, String>> getPayload(@RequestParam String bpmWorklistTaskId, @RequestParam String bpmWorklistContext) {
        Map<String, String> bpmPayload = bpmService.getPayload(bpmWorklistTaskId, bpmWorklistContext);
        return ResponseEntity.status(HttpStatus.OK).body(bpmPayload);
    }

    @PutMapping("/payload")
    public ResponseEntity<Map<String, String>> updatePayload(@RequestParam String bpmWorklistTaskId, @RequestParam String bpmWorklistContext, @RequestBody Map<String, String> updatedPayload) {
        System.out.println("Entro en PUT '/api/BPM/payload");
        Map<String, String> bpmUpdatedPayload = bpmService.updatePayload(bpmWorklistTaskId, bpmWorklistContext, updatedPayload);
        System.out.println("bpmUpdatedPayload: "+bpmUpdatedPayload);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(bpmUpdatedPayload);
        System.out.println("response: "+response);
        return response;
    }

    @PostMapping("/avanzar")
    public ResponseEntity<Void> avanzarSolicitud(@RequestParam String bpmWorklistTaskId, @RequestParam String bpmWorklistContext, @RequestBody Map<String, String> body) {
        System.out.println("Entro en POST '/api/BPM/avanzar");
        bpmService.avanzarSolicitud(bpmWorklistTaskId, bpmWorklistContext, body);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println("response: "+response);
        return response;
    }

}