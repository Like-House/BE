package backend.like_house.global.email;

import backend.like_house.global.error.code.status.ErrorStatus;
import backend.like_house.global.error.handler.AuthException;
import backend.like_house.global.redis.RedisUtil;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    public String sendMessage(String receiver) throws Exception {
        String code = createCode();
        MimeMessage message = createMessage(receiver, code);

        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new AuthException(ErrorStatus.EMAIL_SEND_FAIL);
        }
        emailSender.send(message);
        redisUtil.saveEmailCode(receiver, code);

        return code;
    }
    private String createCode() {
        StringBuffer code = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10)); // 0부터 9까지의 숫자를 무작위로 선택하여 추가
        }
        return code.toString();
    }

    private MimeMessage createMessage(String receiver, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        InternetAddress[] recipients = {new InternetAddress(receiver)};
        message.setSubject("LIKEHOUSE 회원가입 인증 코드입니다.");
        message.setRecipients(Message.RecipientType.TO, recipients);

        String msg = "<!DOCTYPE html>"
                + "<html lang=\"ko\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>이메일 인증 코드</title>"
                + "</head>"
                + "<body style=\"word-break: keep-all; box-sizing: border-box; max-width: 550px; width: 100%; max-height: 440px; height: 100%; overflow: auto; display: flex; border-top: 2px solid #322611; border-bottom: 2px solid #322611; margin: 0;\">"
                + "    <div style=\"display: flex; flex-direction: column; width: 100%; height: 100%; padding-top: 30px;\">"
                + "        <div style=\"margin-left: 20px;\">"
                + "            <h1 style=\"font-size: 23px; color: #322611; margin-bottom: 35px;\">이메일 인증 코드</h1>"
                + "            <p style=\"margin: 3px 0; font-size: 15px; line-height: 23px;\">안녕하세요. <br />가족 소통을 도모하는 폐쇄형 SNS "
                + "               <strong style=\"color: #83632c\">가족같은</strong> 입니다. "
                + "               <br/> LikeHouse 회원가입을 위해 하단의 <strong style=\"color: #83632c\">\"인증 코드\" </strong>로 이메일 인증을 완료해 주세요. "
                + "               <br/><br/> 감사합니다."
                + "            </p>"
                + "        </div>"
                + "        <div style=\"margin-bottom: 30px; font-weight: bold; letter-spacing: 1px; margin-left: 20px; margin-top: 30px; font-size: 20px; background-color: #fff3c1; width: 200px; height: 50px; display: flex; justify-content: center; align-items: center;\">" + code + "</div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        message.setContent(msg, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress("${spring.mail.username}", "LIKEHOUSE"));

        return message;
    }
}
