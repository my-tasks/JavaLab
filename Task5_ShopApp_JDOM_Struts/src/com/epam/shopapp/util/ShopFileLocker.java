package com.epam.shopapp.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShopFileLocker extends ReentrantReadWriteLock {
	private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private ShopFileLocker(){
	}
	
	public static ReadLock getReadLock() {
		return lock.readLock();
	}

	public static WriteLock getWriteLock() {
		return lock.writeLock();
	}
}
