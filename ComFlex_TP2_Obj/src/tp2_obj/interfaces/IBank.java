package tp2_obj.interfaces;

import tp2_obj.exceptions.InsufficientBalanceException;
import tp2_obj.exceptions.UnknownAccountException;

public interface IBank {
	public void transfert(String from, String to, double amount)
	        throws InsufficientBalanceException, UnknownAccountException;
	
	public void init(IAccount... accounts);

}
