package com.mediscreen.report.proxies;

import com.mediscreen.report.dto.ObservationDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ObservationProxy {

    public List<ObservationDTO> getObservationByPatientId(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ObservationDTO>> responseEntity =
                restTemplate.exchange("http://localhost:8082/rest/patient/observation/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<List<ObservationDTO>>() {
                });
        return responseEntity.getBody();
    }

}
