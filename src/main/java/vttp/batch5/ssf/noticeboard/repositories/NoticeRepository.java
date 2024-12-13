package vttp.batch5.ssf.noticeboard.repositories;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.ssf.noticeboard.models.notice;
import vttp.batch5.ssf.noticeboard.services.DateConverter;


@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */
	//HSET NOTICES noticeId notice

	public static final String HASH_KEY = "NOTICES";
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	public String insertNotices(notice notice) {
		String noticeId = UUID.randomUUID().toString();

		redisTemplate.opsForHash().put(HASH_KEY,noticeId,notice);
		return noticeId;
		
	}
	public Object getRandomHashkey(){
		return redisTemplate.opsForHash().randomKey(HASH_KEY);
	}

}



