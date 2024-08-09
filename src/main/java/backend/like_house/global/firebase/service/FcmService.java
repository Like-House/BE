package backend.like_house.global.firebase.service;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.AuthException;
import backend.like_house.global.error.handler.TokenException;
import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public void sendNotification(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new TokenException(ErrorStatus.TOKEN_NOT_SEND);
        }

    }

    public void isTokenValid(String sender, String token) {
        Notification notification = Notification.builder()
                .setTitle(sender)
                .setBody("토큰 인증 되었 습니다.")
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new AuthException(ErrorStatus.TOKEN_NOT_VALID);
        }
    }
}
