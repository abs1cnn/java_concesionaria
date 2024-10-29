package com.example.notificacion.services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppNotificationService {

    // Twilio credentials
    public static final String ACCOUNT_SID = "your_account_sid";
    public static final String AUTH_TOKEN = "your_auth_token";
    public static final String FROM_WHATSAPP_NUMBER = "whatsapp:+14155238886"; // Twilio Sandbox or your registered number

    public WhatsAppNotificationService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendWhatsAppMessage(String to, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(FROM_WHATSAPP_NUMBER),
                message
        ).create();
    }
}