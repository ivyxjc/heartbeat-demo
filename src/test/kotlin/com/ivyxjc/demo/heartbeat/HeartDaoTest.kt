package com.ivyxjc.demo.heartbeat

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [TestApplication::class])
class HeartDaoTest {

    @Autowired
    private lateinit var heartDao: HeartDao

    @Test
    fun testInsert() {
        val heart = Heart(-1, 100, "host_1", 0, System.currentTimeMillis())
        heartDao.insertHeart(heart)

    }
}