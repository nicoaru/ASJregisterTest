package com.asj.register.services;

import com.asj.register.exceptions.custom_exceptions.ErrorProcessException;
import com.asj.register.services.interfaces.IBPMService;
import com.besysoft.besyreferences.BesyReferences;
import com.besysoft.besyreferences.entities.WebServiceRest;
import com.besysoft.besyreferences.exception.BesyReferencesException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BPMService implements IBPMService {

    private final String PAYLOAD_PATH = "obpm12/solicitud/payload";


    private String getURLFromBesyReference() {
        WebServiceRest obpmWS;
        try {
            obpmWS = (WebServiceRest)  new BesyReferences().getExternalResource("PageFlowEngineWS");
        } catch (BesyReferencesException e) {
            throw new ErrorProcessException("Error consultando BesyReferences al buscar el archivo [" + "PageFlowEngineWS" + "]");
        }
        return obpmWS.getUrl();
    }

    @Override
    public Map<String, String> getPayload(String bpmWorklistTaskId, String bpmWorklistContext) {
        String url = this.getURLFromBesyReference();
        String urlParams = "?bpmWorklistTaskId=" + bpmWorklistTaskId + "&bpmWorklistContext=" + bpmWorklistContext;
        String completeUrl =  url+PAYLOAD_PATH+urlParams;
        System.out.println(completeUrl);
        try {
            Map<String, String> response = new RestTemplate().getForObject(completeUrl, new HashMap<String, String>().getClass() );
            System.out.println("payload response: "+response);

            return response;
        }
        catch (RestClientException e) {
            throw new ErrorProcessException("Error intentando traer el bpm payload");
        }


    }



    @Override
    public Map<String, String> updatePayload(String bpmWorklistTaskId, String bpmWorklistContext, Map<String, String> updatedPayload) {
        ResponseEntity<Void> responseUpdatePayload;

        String url = this.getURLFromBesyReference();
        String urlParams = "?bpmWorklistTaskId=" + bpmWorklistTaskId + "&bpmWorklistContext=" + bpmWorklistContext;
        String completeUrl =  url+PAYLOAD_PATH+urlParams;
        //System.out.println(completeUrl);


        try {
           responseUpdatePayload = new RestTemplate().exchange(completeUrl, HttpMethod.PUT, new HttpEntity<>(updatedPayload), Void.class);
            System.out.println(responseUpdatePayload);
            if(!responseUpdatePayload.getStatusCode().is2xxSuccessful()) throw new ErrorProcessException("Error haciendo update del payload");
        }
        catch(Exception e) {
            System.out.println("CATCH - Error haciendo update del payload");
            e.printStackTrace();
            throw new ErrorProcessException("Error haciendo update del payload");
        }

        return getPayload(bpmWorklistTaskId, bpmWorklistContext);
    }
}
