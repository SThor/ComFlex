package tp2_obj.impl;

import tp2_obj.interfaces.IBank;
import tp2_obj.interfaces.IConsult;
import tp2_obj.interfaces.IFastLane;
import tp2_obj.interfaces.ILane;
import tp2_obj.interfaces.IProvider;

public class Main {
	public static void main (String [] args) {
		IProvider prov = new Provider();
		IBank bank = new Bank();
		Client cl = new Client();
		Store store = new Store();
		
		IFastLane fastLane = store;
	    ILane lane= store;
	    IConsult consult= store;
	    
		bank.init();
		bank.initAccounts();		
		cl.init(fastLane, lane, consult);
		store.init(prov, bank);
		
		cl.run();
    }
}
