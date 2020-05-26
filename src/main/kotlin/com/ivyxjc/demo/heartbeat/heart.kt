package com.ivyxjc.demo.heartbeat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.set
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

data class Heart(var lockId: Int, val lockClass: Int, val lockHost: String, var term: Int, var lastTimestamp: Long)


@Repository
class HeartDao {
    @Autowired
    private lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    companion object {
        private const val QUERY_BY_LOCK_ID =
                "select * from libra.heartbeat where lock_class=:lock_class and last_timestamp > :last_timestamp"
        private const val UPDATE_HEARTBEAT = """
            update libra.heartbeat set term=:term,last_timestamp = :last_timestamp
            where lock_id=:lock_id; 
        """
        private const val INSERT_HEARTBEAT = """
            insert libra.heartbeat (lock_class,lock_host, term, last_timestamp) 
            value(:lock_class,:lock_host ,:term, :last_timestamp)
        """
    }

    fun listHeartTimeLimit(pLockClass: Int): List<Heart> {
        val params = MapSqlParameterSource()
        params["lock_class"] = pLockClass
        params["last_timestamp"] = System.currentTimeMillis() - 20 * 1000
        return jdbcTemplate.query(QUERY_BY_LOCK_ID, params) { rs, rowNum ->
            val lockId = rs.getInt("lock_id")
            val lockClass = rs.getInt("lock_class")
            val lockHost = rs.getString("lock_host")
            val term = rs.getInt("term")
            val lastTimestamp = rs.getLong("last_timestamp")
            return@query Heart(lockId, lockClass, lockHost, term, lastTimestamp)
        }
    }

    @Transactional
    fun updateHeart(heart: Heart): Int {
        val params = MapSqlParameterSource()
        params["lock_id"] = heart.lockId
        params["term"] = heart.term
        params["last_timestamp"] = heart.lastTimestamp
        return jdbcTemplate.update(UPDATE_HEARTBEAT, params)
    }

    @Transactional
    fun insertHeart(heart: Heart): Int {
        val params = MapSqlParameterSource()
        params["lock_class"] = heart.lockClass
        params["lock_host"] = heart.lockHost
        params["term"] = heart.term
        params["last_timestamp"] = heart.lastTimestamp
        return jdbcTemplate.update(INSERT_HEARTBEAT, params)
    }


}