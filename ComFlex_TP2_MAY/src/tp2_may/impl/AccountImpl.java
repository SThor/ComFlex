package tp2_may.impl;

import tp2_may.bank.AccountComponent;
import tp2_obj.impl.Account;
import tp2_obj.interfaces.IAccount;

public class AccountImpl extends AccountComponent{

	@Override
	protected IAccount make_account() {
		return new Account();
	}

}
