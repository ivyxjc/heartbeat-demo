package com.ivyxjc.demo.heartbeat

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.Message


@Service
class Queue1Listener {
    companion object {
        private val log = loggerFor(Queue1Listener::class.java)
    }

    @JmsListener(containerFactory = "", destination = "IVY.QUEUE001")
    open fun listen(message: Message) {
        log.info("message is {}", message)
    }

}




