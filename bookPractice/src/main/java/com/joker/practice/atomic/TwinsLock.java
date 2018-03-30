package com.joker.practice.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {

	private final Sync sync = new Sync(2);

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	private static final class Sync extends AbstractQueuedSynchronizer {

		Sync(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("count must large than zero.");
			}
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int reduceCount) {
			for (;;) {
				int current = getState();
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int reduceCount) {
			for (;;) {
				int current = getState();
				int newCount = current + reduceCount;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}

	}
}
