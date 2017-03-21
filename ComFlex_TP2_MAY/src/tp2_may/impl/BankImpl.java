package tp2_may.impl;

import tp2_may.bank.AccountComponent;
import tp2_may.bank.BankComponent;
import tp2_may.bank.DeskComponent;

public class BankImpl extends BankComponent{
	AccountComponent acc1 = new AccountImpl();
	AccountComponent acc2 = new AccountImpl();
	AccountComponent acc3 = new AccountImpl();

	@Override
	protected DeskComponent make_desk() {
		return new DeskImpl();
	}

	@Override
	protected AccountComponent make_estore() {
		return acc1;
	}

	@Override
	protected AccountComponent make_anne() {
		return acc2;
	}

	@Override
	protected AccountComponent make_bob() {
		return acc3;
	}

}
