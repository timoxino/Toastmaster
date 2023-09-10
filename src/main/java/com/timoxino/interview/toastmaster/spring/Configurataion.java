package com.timoxino.interview.toastmaster.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.toastmaster.annotation.LocalProfile;
import com.timoxino.interview.toastmaster.spring.PubSubSenderConfiguration.PubSubCvGateway;

@Configuration
@LocalProfile
public class Configurataion {

    @Bean
    public PubSubCvGateway pubSubCvGateway() {
        return new PubSubCvGateway() {
            @Override
            public void sendCvToPubSub(CandidateBaseMessage message) {
                // Auto-generated method stub
            }
        };
    }
}
