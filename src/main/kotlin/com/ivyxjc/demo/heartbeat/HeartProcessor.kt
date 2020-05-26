package com.ivyxjc.demo.heartbeat

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HeartProcessor : InitializingBean {
    @Value("\${lockClass}")
    private var lockClass = -1

    @Value("\${lockHost}")
    private lateinit var lockHost: String

    @Autowired
    private lateinit var heartDao: HeartDao

    @Autowired
    private lateinit var listenerMonitorService: ListenerMonitorService


    override fun afterPropertiesSet() {
        val runnable = HeartRunnable(lockClass, lockHost, heartDao, listenerMonitorService)
        Thread(runnable).start()
    }
}


class HeartRunnable(
        val lockClass: Int,
        val lockHost: String,
        val heartDao: HeartDao,
        val listenerMonitorService: ListenerMonitorService
) : Runnable {
    companion object {
        @JvmStatic
        private val log = loggerFor(HeartRunnable::class.java)

//        @JvmStatic
//        private fun whetherStop(list: List<Heart>): Boolean {
//            var term = -1
//            list.forEach {
//                if (term != it.term && term != -1) {
//                    return false
//                }
//                term = it.term
//            }
//            return true
//        }

        @JvmStatic
        private fun maxTerm(list: List<Heart>): Int {
            var maxTerm = -1
            list.forEach {
                if (maxTerm < it.term) {
                    maxTerm = it.term
                }
            }
            return maxTerm
        }

        @JvmStatic
        private fun termEquals(list: List<Heart>): Boolean {
            var term = -1
            list.forEach {
                if (term != it.term && term != -1) {
                    return false
                }
            }
            return true
        }

        @JvmStatic
        private fun find(lockHost: String, list: List<Heart>): Heart {

            return list.find {
                if (lockHost == it.lockHost) {
                    return@find true
                }
                return@find false
            }!!

        }
    }

    private var lastList = listOf<Heart>()
    private var heart: Heart? = null
    private var status: Boolean = false

    override fun run() {
        var count = 0
        while (true) {
            while (true) {
                log.info("+++++++++++++++++")
                if (status) {
                    log.info("working")
                } else {
                    log.info("sleeping")
                }
                log.info("work nodes size: {}", lastList.size)
                count++
                Thread.sleep(2000)
                if (count % 5 == 0) {
                    break
                }
            }

            while (heart == null || lastList.isEmpty()) {
                lastList = heartDao.listHeartTimeLimit(lockClass)
                log.info("last list is empty")
                heart = Heart(-1, lockClass, lockHost, maxTerm(lastList) + 1, System.currentTimeMillis())
                heartDao.insertHeart(heart!!)
                lastList = heartDao.listHeartTimeLimit(lockClass)
                heart = find(lockHost, lastList)
            }

            val thisList = heartDao.listHeartTimeLimit(lockClass)

            if (lastList.size != thisList.size) {
                log.info("an machine is down, stop it")
                // use the lastList
                heart!!.term = maxTerm(thisList) + 1
                status = false
            } else if (!termEquals(thisList)) {
                log.info("an machine is up, stop it")
                heart!!.term = maxTerm(thisList)
                listenerMonitorService.stop()
                status = false
            } else {
                log.info("start it ")
                listenerMonitorService.start()
                status = true
            }
            lastList = thisList
            heart!!.lastTimestamp = System.currentTimeMillis()
            heartDao.updateHeart(heart!!)
        }
    }

}