package com.joker.practice.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
/**
 * 自定义同步组件
 * @author Joker
 *
 */
public class Mutex implements Lock {

	private final Sync sync = new Sync();
	
	@Override
	public void lock() {
		sync.acquire(1);

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);

	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);

	}

	@Override
	public Condition newCondition() {
		return new Sync().newCondition();
	}
	
	
	//静态内部类。自定义同步器
	private static class Sync extends AbstractQueuedSynchronizer{
		
		private static final long serialVersionUID = -3320576771625749739L;

		 Condition newCondition() {
			return new ConditionObject();
		}
		
		//当状态为0的时候获取锁
		@Override
		protected boolean tryAcquire(int arg) {
			
			if(compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		
		//释放锁，将状态设置为0
		@Override
		protected boolean tryRelease(int arg) {
			if(getState() == 0)
				throw new IllegalMonitorStateException();
			
			setExclusiveOwnerThread(null);
			
			setState(0);
			
			return true;
		}
		
		//是否处于占用状态
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1 ;
		}
		
	}

}
