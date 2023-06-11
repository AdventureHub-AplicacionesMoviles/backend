package com.app.adventurehub.firebase;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private FCMService fcmService;

    @PostMapping("/send")
    public void sendNotification(@RequestBody PushNotificationRequest body){
        try {
            fcmService.sendMessageToToken(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
