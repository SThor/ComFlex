package tp2_may.impl;

import tp2_may.store.ClientComponent;
import tp2_obj.impl.Client;

public class ClientImpl extends ClientComponent{
	private Client client = new Client();

	@Override
	protected Runnable make_run() {
		return client;
	}
	
	@Override
	protected void start(){
		client.init(requires().fastLane(), requires().lane(), requires().consut());
		super.start();
	}

}
