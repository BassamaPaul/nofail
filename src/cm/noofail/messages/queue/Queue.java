package cm.noofail.messages.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue<E> {
	private ConcurrentLinkedQueue<E> list;
	
	public Queue() {
		list = new ConcurrentLinkedQueue<>();
	}
	
	public void queue(E e) {
		if (e == null) return;
		list.add(e);
	}
	
	public E dequeue() {
		return list.poll();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
}