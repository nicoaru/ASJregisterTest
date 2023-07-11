package com.asj.register.services.interfaces;


import java.util.Map;

public interface IBPMService {
    Map<String, String> getPayload(String bpmWorklistTaskId, String bpmWorklistContext);
}
