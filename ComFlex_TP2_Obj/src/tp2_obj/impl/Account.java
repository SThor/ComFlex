package tp2_obj.impl;

import tp2_obj.exceptions.InsufficientBalanceException;
import tp2_obj.interfaces.IAccount;

public class Account implements IAccount {

    private double amount;
    private String owner;

    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#getOwner()
	 */
    @Override
	public String getOwner() {
        return owner;
    }

    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#setOwner(java.lang.String)
	 */
    @Override
	public void setOwner(String owner) {
        this.owner = owner;
    }

    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#getAmount()
	 */
    @Override
	public double getAmount() {
        return amount;
    }

    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#setAmount(double)
	 */
    @Override
	public void setAmount(double amount) {
        this.amount = amount;
    }
    
    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#credit(double)
	 */
    @Override
	public void credit(double amount) {
        this.amount += amount;        
    }

    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#withdraw(double)
	 */
    @Override
	public void withdraw(double amount) throws InsufficientBalanceException {
        if ( this.amount < amount )
            throw new InsufficientBalanceException(owner);
        this.amount -= amount;
    }
    
    /* (non-Javadoc)
	 * @see tp2_obj.impl.IAccount#equals(java.lang.Object)
	 */
    @Override
	public boolean equals( Object other ) {
        if( ! (other instanceof Account) )
            return false;
        Account otherAccount = (Account) other;
        return ( otherAccount.owner == owner);
    }

	@Override
	public void init(String name, double amount) {
		setOwner(name);
		setAmount(amount);
	}
    
}
