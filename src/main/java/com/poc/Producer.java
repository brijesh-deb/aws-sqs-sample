package com.poc;

import com.amazon.sqs.javamessaging.SQSMessagingClientConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.config.JmsConfig;
import com.poc.model.input.CombinerInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class Producer {

	private static Logger logger = LogManager.getLogger(Producer.class);

	@Resource
    protected JmsTemplate jmsTemplate;

    @Value("${queue.mce.combiner.io}")
    String queueA;

    @Resource
    ObjectMapper objectMapper;

    public void sendToQueueA(CombinerInput message) {
    	logger.info("Sending {} to queue {}", message, queueA);
        send(queueA, message);
    }
    
    public <MESSAGE extends Serializable> void send(String queue, MESSAGE payload) {

        jmsTemplate.send(queue, new MessageCreator() {

            public javax.jms.Message createMessage(Session session) throws JMSException {
                try {
                    javax.jms.Message createMessage = session.createTextMessage(objectMapper.writeValueAsString(payload));
                    createMessage.setStringProperty(SQSMessagingClientConstants.JMSX_GROUP_ID, "messageGroup1");
                    createMessage.setStringProperty(SQSMessagingClientConstants.JMS_SQS_DEDUPLICATION_ID, "1" + System.currentTimeMillis());
                    createMessage.setStringProperty("documentType", payload.getClass().getName());
                    return createMessage;
                } catch (Exception | Error e) {
                	logger.error("Fail to send message {}", payload);
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @PostConstruct
    public void sendMessages() {
    	CombinerInput msg = new CombinerInput();
    	msg.setID("1111");
    	msg.setMessage("Test SQS Message");
        sendToQueueA(msg);
    }
}
