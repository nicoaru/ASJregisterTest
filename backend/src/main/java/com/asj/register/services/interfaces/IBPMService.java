package com.asj.register.services.interfaces;


import java.util.Map;

public interface IBPMService {
    Map<String, String> getPayload(String bpmWorklistTaskId, String bpmWorklistContext);

    Map<String, String> updatePayload(String bpmWorklistTaskId, String bpmWorklistContext, Map<String, String> updatedPayload);

    void avanzarSolicitud(String bpmWorklistTaskId, String bpmWorklistContext, Map<String, String> body);
}
