package com.example.mailspringbootrabbitmq.mail_test;

import com.example.mailspringbootrabbitmq.Bean.MailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 伍六七
 * @date 2022/12/12 15:45
 */
@Component
public class MailServiceImpl {

    @Value("${spring.mail.username}")
    private String from;


    @Autowired
    private JavaMailSender mailSender;

    public boolean sendSimpleMail(MailVo mailVo) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mailVo.getTo().split(","));
            helper.setSubject(mailVo.getSub());
            String[] split = mailVo.getText().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<h2>" + mailVo.getSub() + "</h2>");
            for (int i = 0; i < split.length; i++) {
                stringBuilder.append(split[i] + "<br>");
            }
            helper.setText(stringBuilder.toString(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
