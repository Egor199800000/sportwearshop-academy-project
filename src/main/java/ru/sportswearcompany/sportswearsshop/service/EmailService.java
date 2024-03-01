package ru.sportswearcompany.sportswearsshop.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailService {
    Logger logger= LoggerFactory.getLogger(this.getClass());//создаем логгер для этого класса

    private String FROM= "SportsWearsShop";

    private JavaMailSender javaMailSender;

    public void sendMessage(String email,String username){
        SimpleMailMessage simpleMailMessage=null;

        try {

            simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(FROM);//назначаем отправителя
            simpleMailMessage.setTo(email);//назначаем почту отправителя
            simpleMailMessage.setSubject("Registration was successful !!! :");//назначаем текст письма
            simpleMailMessage.setText("Dear " + username + " thank you registration");//который придет только что зареганому пользователю
            javaMailSender.send(simpleMailMessage);//отправляем
        }
        catch (Exception e){
            logger.error("Something went wrong with this email "+email);
        }
    }


}
