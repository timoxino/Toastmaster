package com.timoxino.interview.toastmaster.service;

public interface EmailService {
    void sendEmail(String from, String to, String subject, String text);
}
