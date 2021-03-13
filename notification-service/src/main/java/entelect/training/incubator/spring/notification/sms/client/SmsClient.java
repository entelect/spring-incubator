package entelect.training.incubator.spring.notification.sms.client;

public interface SmsClient {
    
    void sendSms(String phoneNumber, String message);
}
