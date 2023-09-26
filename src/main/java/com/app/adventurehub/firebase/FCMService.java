package com.app.adventurehub.firebase;

import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FCMService
{
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    FCMService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${app.firebase.server-key}")
    private String FIREBASE_SERVER_KEY;

    public void sendMessageToToken(PushNotificationRequest body) throws JsonProcessingException {
        String url = "https://fcm.googleapis.com/fcm/send";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String mobileToken = userRepository.findByUsername(body.getTo()).getMobile_token();

        String payloadJson = "" +
                "{\n" +
                "  \"to\": \"" + mobileToken + "\",\n" +
                "  \"notification\": {\n" +
                "    \"title\": \"" + body.getNotification().getTitle() + "\",\n" +
                "    \"body\": \"" + body.getNotification().getBody() + "\"\n" +
                "  }\n" +
                "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(payloadJson, headers);

//        restTemplate.postForObject(url, payload, String.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Notification was not sent");
        }

        String response = responseEntity.getBody();
        System.out.println(response);
    }
}
