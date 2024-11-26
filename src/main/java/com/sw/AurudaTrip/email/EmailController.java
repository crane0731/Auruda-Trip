package com.sw.AurudaTrip.email;

import com.sw.AurudaTrip.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auruda/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String content) {
        emailService.sendEmail(recipient, subject, content);
        return "이메일 전송 완료!";
    }
}
