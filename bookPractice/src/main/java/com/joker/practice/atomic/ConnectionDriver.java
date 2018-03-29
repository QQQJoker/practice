package com.joker.practice.atomic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class ConnectionDriver {
	
	static class ConnectionHandler implements InvocationHandler{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if(method.getName().equals("commit")) {
				SleepUtils.millseconds(100);
			}
			return null;
		}
		
	}
	//创建一个Connection的代理，在commit的时候休眠100毫秒
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
				new Class<?>[] {Connection.class},new ConnectionHandler());
	}
}
