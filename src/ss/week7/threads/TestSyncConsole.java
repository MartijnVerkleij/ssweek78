package ss.week7.threads;

import java.util.concurrent.locks.ReentrantLock;

public class TestSyncConsole extends Thread{
//	private Thread thread;
//	
//	public static void main(String[] args) {
//		TestSyncConsole one = new TestSyncConsole("Thread A");
//		one.run();
//		
//		TestSyncConsole two = new TestSyncConsole("Thread B");
//		two.run();
//	}
//	
//	public TestSyncConsole (String name) {
//		thread = new Thread(this, name);
//	}
//	
//	public synchronized void sum() {
//		int first = SynConsole.readInt(this.getName() + ": get number 1?");
//		int second = SynConsole.readInt(this.getName() + ": get number 2?");
//		
//		SynConsole.println(this.getName() + ": " + first + " + " + second + " = " + (first + second));
//	}
//	
//	public void run() {
//		sum();
//	}
	
private Thread thread;
private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		TestSyncConsole one = new TestSyncConsole("Thread A");
		one.run();
		
		TestSyncConsole two = new TestSyncConsole("Thread B");
		two.run();
	}
	
	public TestSyncConsole (String name) {
		thread = new Thread(this, name);
	}
	
	public synchronized void sum() {
		lock.lock();
		int first = SynConsole.readInt(this.getName() + ": get number 1?");
		int second = SynConsole.readInt(this.getName() + ": get number 2?");
		
		SynConsole.println(this.getName() + ": " + first + " + " + second + " = " + (first + second));
		lock.unlock();
	}
	
	public void run() {
		sum();
	}

}
