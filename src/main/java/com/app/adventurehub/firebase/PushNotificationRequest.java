package com.app.adventurehub.firebase;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PushNotificationRequest {
    private Notification notification;
    private Object data;
    private String to;
}
