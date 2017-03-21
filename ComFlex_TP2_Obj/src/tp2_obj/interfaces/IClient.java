package tp2_obj.interfaces;

import tp2_obj.interfaces.store.IConsult;
import tp2_obj.interfaces.store.IFastLane;
import tp2_obj.interfaces.store.ILane;


public interface IClient extends Runnable{

	public abstract void init(IFastLane fastLane, ILane lane, IConsult consult);

}