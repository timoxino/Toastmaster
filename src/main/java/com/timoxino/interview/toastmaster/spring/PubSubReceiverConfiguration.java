package com.timoxino.interview.toastmaster.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.toastmaster.annotation.GcpCloudRun;
import com.timoxino.interview.toastmaster.service.OrchestrationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@GcpCloudRun
public class PubSubReceiverConfiguration {

    @Autowired
    OrchestrationService orchestrationService;

    @Bean
    public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
        return new JacksonPubSubMessageConverter(objectMapper);
    }

    /**
     * Create a message channel for messages arriving from the subscription
     * `toastmaster_subscription`.
     */
    @Bean
    public MessageChannel inputMessageChannel() {
        return new PublishSubscribeChannel();
    }

    /**
     * Create an inbound channel adapter to listen to the subscription
     * `toastmaster_subscription` and send
     * messages to the input message channel.
     */
    @Bean
    public PubSubInboundChannelAdapter inboundChannelAdapter(
            @Qualifier("inputMessageChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
                "toastmaster_subscription");
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(CandidateQuestionsMessage.class);
        return adapter;
    }

    /**
     * Define what happens to the messages arriving in the message channel.
     */
    @ServiceActivator(inputChannel = "inputMessageChannel")
    @GcpCloudRun
    public void messageReceiver(
            CandidateQuestionsMessage payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        log.info("Message arrived from 'compiled_questions_topic'. Payload: {}", payload.toString());
        orchestrationService.processQuestionsMessage(payload);
        log.info("Message from 'compiled_questions_topic' processed successfully");
        message.ack();
        log.info("Acknowledged successfully");
    }
}
