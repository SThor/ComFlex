package tp2_obj.impl;

import tp2_obj.interfaces.IAccount;
import tp2_obj.interfaces.IBank;
import tp2_obj.interfaces.IProvider;
import tp2_obj.interfaces.store.IConsult;
import tp2_obj.interfaces.store.IFastLane;
import tp2_obj.interfaces.store.ILane;

public class Main {
	public static void main (String [] args) {
		IProvider prov = new Provider();
		IBank bank = new Bank();
		Client cl = new Client();
		Store store = new Store();
		IAccount estore = new Account();
		IAccount anne = new Account();
		IAccount bob = new Account();
		
		IFastLane fastLane = store;
	    ILane lane= store;
	    IConsult consult= store;
	    
		estore.init("Estore", 0);
		anne.init("Anne", 30);
		bob.init("Bob", 100);
		
		bank.init(estore, anne, bob);
		
		cl.init(fastLane, lane, consult);
		store.init(prov, bank);
		
		cl.run();
    }
}
