package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.EmailService;
import com.example.demo.util.RandomUtils;
import com.example.demo.util.VerCodeGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;


@Service
public class EmailServiceImpl implements EmailService {
    //注入配置文件中配置的信息——>from
    @Value("${spring.mail.from}")
    private String from;

    private String code;

    private Date sendTime;

    @Override
    public Result<?> getCode(String sender, JavaMailSenderImpl mailSender, String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("海大评课验证码");//设置邮件标题
        code = RandomUtils.randomCode();
        sendTime = new Date();
        message.setText("尊敬的用户,您好:\n"
                + "本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）"); //设置邮件正文
        message.setFrom(sender);//发件人
        message.setTo(receiver);//收件人
        mailSender.send(message);//发送邮件
        return Result.success();
    }

    @Override
    public Boolean addUser(User user, String verCode) {
        Date date = new Date();
        //判断验证码
        if (VerCodeGenerateUtil.getMinute(sendTime, date) > 5) {
            return false;
        }
        if (!verCode.equals(code)) {
            return false;
        }
        code = null;
        return true;
    }
}

