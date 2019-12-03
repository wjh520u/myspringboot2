package com.boot.config.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.SimpleCacheManager;

import com.alibaba.fastjson.JSONObject;
import com.boot.config.web.configuration.GetBean;



/**
 * @ClassName: CacheHelper.java
 * @author：wh
 * @date: 2016年2月18日 上午9:25:33
 * @version: V1.0
 * @Descripton:
 * 缓存帮助类
 */
public class CacheHelper {
	
	private static CacheManager cacheManager = GetBean.get(EhCacheCacheManager.class);
	
	
	/**
	 * @method: put
	 * @tags: @param key
	 * @tags: @param result
	 * @Descripton:
	 * 设置缓存
	 */
	public static void put(String cacheName,String key,Object result){
		
		if(cacheName==null||cacheName.equals("")){
			cacheName = "defaultCache";
		}
			
		Cache cache = cacheManager.getCache(cacheName);
		if(cache != null){
			cache.put(key, result);
		}else {
			System.out.println( "缓存域：+"+cacheName+"不存在！" );
		}
	}
	
	/**
	 * 
	 * @方法名称: put 
	 * @职责说明: 完成TODO
	 * @参数: @param key
	 * @参数: @param result    
	 */
	public static void put(String key,Object result){
		put(null,key,result);
	}
	
	/**
	 * 
	 * @方法名称: put 
	 * @职责说明: 完成TODO
	 * @参数: @param key
	 * @参数: @param result
	 * @参数: @param duration    
	 */
	@Deprecated
	public static void put(String key,Object result,int duration){
		put(null,key,result);
	}
	
	/**
	 * @method: del
	 * @tags: @param key
	 * 删除缓存
	 */
	public static void del(String cacheName,String key){
		Cache cache = getCache(cacheName);
		if(cache == null){
			return;
		}
		cache.evict(key);
	}
	
	/**
	 * 
	 * @方法名称: del 
	 * @职责说明: 完成TODO
	 * @参数: @param key    
	 */
	public static void del(String key){
		del(null,key);
	} 
	
	
	public static<T> T get(String cacheName,String key,Class< T > type){
		Cache cache = getCache(cacheName);
		if(cache == null){
			return null;
		}
		ValueWrapper valueWrapper = cache.get(key);
		if(valueWrapper != null){
			Object object = valueWrapper.get();
			if ( object != null ) {
				return ( (JSONObject) object ).toJavaObject( type );
			}
		}
		return null;
	}
	
	/**
	 * @method: get
	 * @tags: @param key
	 * @tags: @return
	 * 获取缓存
	 */
	public static Object get(String cacheName,String key){
		Cache cache = getCache(cacheName);
		if(cache == null){
			return null;
		}
		ValueWrapper valueWrapper = cache.get(key);
		if(valueWrapper != null){
			return valueWrapper.get();
		}
		return null;
	}
	/**
	 * 
	 * @方法名称: get 
	 * @职责说明: 完成TODO
	 * @参数: @param key
	 * @参数: @return    
	 */
	public static Object get(String key){
		return get(null,key);
	}
	
	
	/**
	 * @method: hasKey
	 * @tags: @param key
	 * @tags: @return
	 * 缓存是否存在
	 */
	public static Boolean hasKey(String cacheName,String key){
		Cache cache = getCache(cacheName);
		if(cache == null){
			return false;
		}
		ValueWrapper valueWrapper = cache.get(key);
		if(valueWrapper == null || valueWrapper.get() == null){
			return false;
		}
		return true;
	}
	/** 
	 * @方法名称: getCache 
	 * @职责说明: 完成TODO
	 * @参数: @param cacheName
	 * @参数: @return    
	 */ 
	private static Cache getCache(String cacheName) {
		if(cacheName==null||cacheName.equals("")){
			cacheName = "defaultCache";
		}
		Cache cache = cacheManager.getCache(cacheName);
		if(cache==null&&cacheManager instanceof SimpleCacheManager){
			cacheName = "defaultCache";
			cache = cacheManager.getCache(cacheName);
		}
		return cache;
	}

	/**
	 * 
	 * @方法名称: hasKey 
	 * @职责说明: 完成TODO
	 * @参数: @param key
	 * @参数: @return    
	 */
	public static Boolean hasKey(String key){
		return hasKey(null,key);
	}
}
