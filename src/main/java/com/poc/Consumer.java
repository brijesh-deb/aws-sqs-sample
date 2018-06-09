package com.poc;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.poc.model.input.CombinerInput;

@Slf4j
@Component
public class Consumer
{

	private static Logger logger = LogManager.getLogger(Consumer.class);
	
    @JmsListener(destination = "${queue.test.input}")
    public void processMessageA(@Payload final Message<CombinerInput> message) {
    	logger.info("Processing {} in queue a", message.getPayload());
        CombinerInput msg = message.getPayload();
        logger.info("ID received: "+msg.getID() );
        logger.info("Message received: "+msg.getMessage() );
    }
}
