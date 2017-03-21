package tp2_obj.impl;

import tp2_obj.exceptions.InsufficientBalanceException;
import tp2_obj.exceptions.UnknownAccountException;
import tp2_obj.interfaces.IAccount;
import tp2_obj.interfaces.IBank;

public class Bank implements IBank{

	private IAccount estore;
	private IAccount anne, bob;

	public Bank () {}
	
	@Override
     public void transfert(String from, String to, double amount)
        throws InsufficientBalanceException, UnknownAccountException {
        IAccount Afrom=null, Ato=null;        
 
        if (from.equals("E-Store")) Afrom = estore;
        	if (from.equals("Anne")) Afrom = anne;
        	if (from.equals("Bob")) Afrom = bob;
        	
        	if (to.equals("E-Store")) Ato = estore;
        	if (to.equals("Anne")) Ato = anne;
        	if (to.equals("Bob")) Ato = bob;
        	
            // Get the balance of the account to widthdraw
            double fromBalance = Afrom.getAmount();
            
            // Check whether the account is sufficiently balanced
            if ( fromBalance < amount )
                throw new InsufficientBalanceException(from.toString());
            
            // Get the balance of the account to credit
            double toBalance = Ato.getAmount();
            
            // Perform the transfert
			Afrom.setAmount( fromBalance - amount );
			Ato.setAmount( toBalance + amount );
    }

	@Override
	public void init(IAccount... accounts) {
		estore = accounts[0];
		anne = accounts[1];
		bob = accounts[2];
	}
    
 }
