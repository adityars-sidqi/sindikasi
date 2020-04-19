package com.sdd.utils;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;

public class QueueUtil {
	public static final String QUEUE_NAME = "progressEvent";
		
	//look up the desktop queue to communicate with another ui controller
	public static EventQueue<QueueMessage> lookupQueue(WebApp webapp) {
		//EventQueue<QueueMessage> queue = EventQueues.lookup(QUEUE_NAME, EventQueues.APPLICATION, true);
		EventQueue<QueueMessage> queue = EventQueues.lookup(QUEUE_NAME, webapp, true);
		return queue;
	}
}
