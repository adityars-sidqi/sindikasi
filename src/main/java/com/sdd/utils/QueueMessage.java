package com.sdd.utils;

import org.zkoss.zk.ui.event.Event;

public class QueueMessage extends Event {
	private static final long serialVersionUID = 1L;

	static public enum Type {
		ADD, UPDATE, DELETE;
	}
	
	private Type type;
	private Object data;
	
	public QueueMessage(Type type) {
		super("onCalendarMessage");
		this.type = type;
	}
	
	public QueueMessage(Type type, Object data) {
		this(type);
		this.data = data;
	}

	public Type getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
}
