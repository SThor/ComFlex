package tp2_obj.interfaces;

import tp2_obj.exceptions.InsufficientBalanceException;

public interface IAccount {

	public abstract String getOwner();

	public abstract void setOwner(String owner);

	public abstract double getAmount();

	public abstract void setAmount(double amount);

	public abstract void credit(double amount);

	public abstract void withdraw(double amount)
			throws InsufficientBalanceException;

	/**
	 * Two AccountImpl instances are considered equals
	 * if they share the same owner.
	 * Of course, in a more realistic implementation,
	 * we should have a account number.
	 */
	public abstract boolean equals(Object other);
	
	public void init(String name, double amount);

}