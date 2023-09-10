package com.timoxino.interview.toastmaster.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageHandler;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.toastmaster.annotation.GcpCloudRun;

@Configuration
@GcpCloudRun
public class PubSubSenderConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(PubSubSenderConfiguration.class);

    @Bean
    public DirectChannel pubSubOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "pubSubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "cv_pending_topic");
    }

    @MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
    @GcpCloudRun
    public interface PubSubCvGateway {
        void sendCvToPubSub(CandidateBaseMessage message);
    }
}
