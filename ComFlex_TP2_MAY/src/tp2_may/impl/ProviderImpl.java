package tp2_may.impl;

import tp2_may.store.ProviderComponent;
import tp2_obj.impl.Provider;
import tp2_obj.interfaces.IProvider;

public class ProviderImpl extends ProviderComponent{

	@Override
	protected IProvider make_provider() {
		return new Provider();
	}

}
