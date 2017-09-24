package com.mxcx.erp.base.commons.service;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class JedisService<K, V> {
	private static Gson gson = new Gson();
	
	@Autowired
	private RedisTemplate redisTemplate;	

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Resource(name = "redisTemplate")
	private ValueOperations<String,Object> valueOps;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOps;

	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;
	
	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> ZSetOps;

	public ListOperations<String, String> getListOps() {
		return listOps;
	}

	public void setListOps(ListOperations<String, String> listOps) {
		this.listOps = listOps;
	}

	public Object getValueOps(String id,Class<?> class1) {
		String str=(String) this.valueOps.get(id);
		Object obj=gson.fromJson(str, class1);
		return obj;
	}

	public void setValueOps(String id,Object obj) {
		String json=gson.toJson(obj);
		this.valueOps.set(id, json);
	}

//	public HashOperations<String, String, Object> getHashOps() {
//		return hashOps;
//	}

//	public void setHashOps(HashOperations<String, String, Object> hashOps) {
//		this.hashOps = hashOps;
//	}
	public Object getHashOps(String temp) {
		Object obj=this.hashOps.keys(temp);
		return obj;
	}

	public void setHashOps(String arg,String arg1,Object obj) {
		this.hashOps.put(arg, arg1, obj);
	}

	public SetOperations<String, String> getSetOps() {
		return setOps;
	}

	public void setSetOps(SetOperations<String, String> setOps) {
		this.setOps = setOps;
	}

	public ZSetOperations<String, String> getZSetOps() {
		return ZSetOps;
	}

	public void setZSetOps(ZSetOperations<String, String> zSetOps) {
		ZSetOps = zSetOps;
	}

	public RedisTemplate<K,V> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<K,V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	
}
