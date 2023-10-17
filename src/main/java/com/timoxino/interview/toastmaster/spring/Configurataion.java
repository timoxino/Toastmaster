package com.timoxino.interview.toastmaster.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.toastmaster.annotation.LocalProfile;
import com.timoxino.interview.toastmaster.spring.PubSubSenderConfiguration.PubSubCvGateway;

@Configuration
public class Configurataion {

    @Bean
    public ObjectMapper customizeJacksonConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }

    @LocalProfile
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
