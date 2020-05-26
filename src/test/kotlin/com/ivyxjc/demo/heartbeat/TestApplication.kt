package com.ivyxjc.demo.heartbeat

import org.springframework.context.annotation.PropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@PropertySource(value = ["private-jdbc.properties"])
open class TestApplication