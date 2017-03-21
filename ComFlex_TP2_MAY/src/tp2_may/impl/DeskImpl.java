package tp2_may.impl;

import tp2_may.bank.DeskComponent;
import tp2_obj.impl.Bank;
import tp2_obj.interfaces.IBank;

public class DeskImpl extends DeskComponent{
	
	IBank bank = new Bank();

	@Override
	protected IBank make_bank() {
		return bank;
	}
	
	@Override
	protected void start(){
		requires().acc1().init("Estore", 0);
		requires().acc2().init("Anne", 30);
		requires().acc3().init("Bob", 100);
		bank.init(requires().acc1(),requires().acc2(),requires().acc3());
		super.start();
	}

}
