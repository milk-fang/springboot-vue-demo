package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public interface EmailService {

   Result<?> getCode(String sender, JavaMailSenderImpl mailSender, String receiver);

   Boolean addUser(User user, String verCode);
}
