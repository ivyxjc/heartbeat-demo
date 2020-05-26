//package com.ivyxjc.demo.heartbeat
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.ApplicationContext
//import org.springframework.jms.config.JmsListenerConfigUtils
//import org.springframework.jms.config.JmsListenerEndpointRegistry
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//
//@RestController
//@RequestMapping("/jms")
//class LifecycleController {
//    @Autowired
//    private lateinit var context: ApplicationContext
//
//    @GetMapping("stop")
//    fun stop(): String {
//
//        val customRegistry: JmsListenerEndpointRegistry =
//            context.getBean(
//                JmsListenerConfigUtils.JMS_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
//                JmsListenerEndpointRegistry::class.java
//            )
//        customRegistry.stop()
//        return "Jms Listener Stopped"
//    }
//
//    @GetMapping("start")
//    fun start(): String {
//
//        val customRegistry: JmsListenerEndpointRegistry =
//            context.getBean(
//                JmsListenerConfigUtils.JMS_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
//                JmsListenerEndpointRegistry::class.java
//            )
//        customRegistry.start()
//        return "Jms Listener Started"
//    }
//}