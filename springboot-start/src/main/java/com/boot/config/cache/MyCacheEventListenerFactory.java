package com.boot.config.cache;

import java.util.Properties;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class MyCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		return new MyCacheEventListener();
	}
	
	public static class MyCacheEventListener implements CacheEventListener {

		@Override
		public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
			System.out.println("removed");
		}

		@Override
		public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
			System.out.println("put");
		}

		@Override
		public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
			System.out.println("updated");
		}

		@Override
		public void notifyElementExpired(Ehcache cache, Element element) {
			System.out.println("expired");
		}

		@Override
		public void notifyElementEvicted(Ehcache cache, Element element) {
			System.out.println("evicted");
		}

		@Override
		public void notifyRemoveAll(Ehcache cache) {
			System.out.println("removeAll");
		}

		@Override
		public void dispose() {

		}

		public Object clone() throws CloneNotSupportedException {
			throw new CloneNotSupportedException();
		}

	}

}