package ss.week7.account;

public class Account {
	protected double balance = 0.0;
	
	public Account() {}

	public synchronized void transaction(double amount) {
		balance = balance + amount;
	}
	public double getBalance() {
		return balance;

	}
}
