package com.greensphere.userservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greensphere.userservice.dto.response.notificationServiceResponse.SmsResponse;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoutOutService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${shoutout.api.url}")
    private String serviceUrl;

    @Value("${shoutout.api.key}")
    private String apiKey;

    @Value("${shoutout.source.name}")
    private String sourceName;

    public SmsResponse sms(String number, String content) {
        try {
            Map<String, Object> body = new HashMap<>();
            HashMap<String, String> contentMap = new HashMap<>();
            contentMap.put("sms", content);

            ArrayList<String> transports = new ArrayList<>();
            transports.add("sms");

            ArrayList<String> destinations = new ArrayList<>();
            destinations.add(number);

            body.put("source", sourceName);
            body.put("destinations", destinations);
            body.put("content", contentMap);
            body.put("transports", transports);

            log.info("sms -> request: {}", objectMapper.writeValueAsString(body));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Apikey " + apiKey);

            ResponseEntity<String> response = webClient.post()
                    .uri(serviceUrl)
                    .headers(header -> header.addAll(headers))
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            if (response == null) {
                log.error("sms -> response is null");
                return SmsResponse.builder()
                        .code(ResponseCodeUtil.FAILED_CODE)
                        .message("Response is null")
                        .build();
            }
            log.info("sms -> response : {}", response.getBody());

            if (response.getStatusCode().value() == 200) {
                try {
                    JsonNode node = objectMapper.readTree(response.getBody());
                    if (node.has("status")) {
                        return SmsResponse.builder()
                                .code(ResponseCodeUtil.SUCCESS_CODE)
                                .message("Sms successfully sent")
                                .build();
                    }
                } catch (Exception parseEx) {
                    log.warn("sms -> could not parse response body: {}", parseEx.getMessage());
                    // Fall through to generic success on 200 if upstream does not include 'status'
                    return SmsResponse.builder()
                            .code(ResponseCodeUtil.SUCCESS_CODE)
                            .message("Sms successfully sent")
                            .build();
                }
            }
            return SmsResponse.builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .message("Sms sending failed, response: " + response.getBody())
                    .build();
        } catch (Exception e) {
            log.error("sms -> Exception: {}", e.getMessage(), e);
            return SmsResponse.builder()
                    .code(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)
                    .message("Error occurred while sending message, error: " + e.getMessage())
                    .build();
        }
    }
}


