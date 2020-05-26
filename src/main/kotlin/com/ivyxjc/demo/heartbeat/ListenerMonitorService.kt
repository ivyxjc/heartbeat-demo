package com.ivyxjc.demo.heartbeat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.config.JmsListenerEndpointRegistry
import org.springframework.stereotype.Service

@Service
class ListenerMonitorService {

    @Autowired
    private lateinit var customRegistry: JmsListenerEndpointRegistry


    fun stop(): Int {
        if (customRegistry.isRunning) {
            customRegistry.stop()
        }
        return 1
    }

    fun start(): Int {
        if (!customRegistry.isRunning) {
            customRegistry.start()
        }
        return 1
    }
}