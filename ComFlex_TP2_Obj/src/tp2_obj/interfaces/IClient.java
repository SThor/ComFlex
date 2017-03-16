package tp2_obj.interfaces;


public interface IClient extends Runnable{

	public abstract void init(IFastLane fastLane, ILane lane, IConsult consult);

}