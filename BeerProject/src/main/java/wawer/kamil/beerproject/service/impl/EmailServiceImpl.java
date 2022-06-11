package wawer.kamil.beerproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.service.EmailService;
import wawer.kamil.beerproject.utils.mapper.MimeMessageHelperMapper;

import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private static final String ENCODING = "UTF-8";

    @Override
    @Async
    public void send(Email email) {
        MimeMessage mimeMessage = createMimeMessage(email);
        javaMailSender.send(mimeMessage);
    }

    private MimeMessage createMimeMessage(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelperMapper.map(email, new MimeMessageHelper(mimeMessage, ENCODING));
        return mimeMessage;
    }
}

