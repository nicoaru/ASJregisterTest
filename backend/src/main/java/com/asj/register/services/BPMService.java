package com.asj.register.services;

import com.asj.register.exceptions.custom_exceptions.ErrorProcessException;
import com.asj.register.services.interfaces.IBPMService;
import com.besysoft.besyreferences.BesyReferences;
import com.besysoft.besyreferences.entities.WebServiceRest;
import com.besysoft.besyreferences.exception.BesyReferencesException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BPMService implements IBPMService {

    @Override
    public Map<String, String> getPayload(String bpmWorklistTaskId, String bpmWorklistContext) {
        String url = this.getURLFromBesyReference();
        String urlParams = "?bpmWorklistTaskId=" + bpmWorklistTaskId + "&bpmWorklistContext=" + bpmWorklistContext;
        String completeUrl =  url + "/obpm12/solicitud/payload" + urlParams;
        System.out.println(completeUrl);
        Map<String, String> response = new RestTemplate().getForObject(completeUrl, new HashMap<String, String>().getClass() );
        System.out.println(response);
        return response;
    }

    private String getURLFromBesyReference() {
        System.out.println("Inicio busqueda de BesyReference");
        WebServiceRest obpmWS;
        try {
            obpmWS = (WebServiceRest)  new BesyReferences().getExternalResource("PageFlowEngineWS");
        } catch (BesyReferencesException e) {
            throw new ErrorProcessException("Error consultando BesyReferences al buscar el archivo [" + "PageFlowEngineWS" + "]");
        }
        return obpmWS.getUrl();
    }

}
