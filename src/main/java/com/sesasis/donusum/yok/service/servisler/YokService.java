package com.sesasis.donusum.yok.service.servisler;

import com.sesasis.donusum.yok.core.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class YokService  {
    private final static String APPLICATION_JSON = "application/json";
    private final RestTemplate restTemplate;

    @Value("${entegration.yokservisler.url}")
    private String url;

    @Value("${entegration.yokservisler.authorization-value}")
    private String authorizationValue;

    public YokService() {
        this.restTemplate = new RestTemplate();
    }


    private HttpEntity<Map> getHttpEntity(Map body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorizationValue);
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

        if(body==null){
            return new HttpEntity(headers);
        }

        return new HttpEntity(body, headers);
    }

    public ApiResponse getFaaliyetler() {

        HttpEntity<Map> httpEntity = getHttpEntity(null);

        String endPoint = url + "getFaaliyetler";

        ResponseEntity<Object> faaliyetlerResponse = restTemplate.exchange(endPoint, HttpMethod.GET, httpEntity, Object.class);

        return  new ApiResponse(faaliyetlerResponse.getStatusCode()==HttpStatus.OK,"",faaliyetlerResponse.getBody());
    }

}
