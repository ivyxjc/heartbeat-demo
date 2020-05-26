package com.ivyxjc.demo.heartbeat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("private-jdbc.properties")
class HeartbeatDemoApplication {

}

fun main(args: Array<String>) {
    runApplication<HeartbeatDemoApplication>(*args)
}


