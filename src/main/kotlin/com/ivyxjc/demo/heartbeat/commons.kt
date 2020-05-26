@file:JvmName("CoreCommons")

package com.ivyxjc.demo.heartbeat

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T> loggerFor(clz: Class<T>): Logger = LoggerFactory.getLogger(clz)

fun loggerFor(clzName: String): Logger = LoggerFactory.getLogger(clzName)

internal val topLevelClass = object : Any() {}.javaClass.enclosingClass

