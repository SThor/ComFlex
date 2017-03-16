package tp2_obj.interfaces;

import tp2_obj.exceptions.UnknownItemException;
import tp2_obj.impl.Store;

public interface IProvider {
	/**
     * Get the price of an item provided by this provider.
     * 
     * @param item
     * @return
     */
    public double getPrice( Object item ) throws UnknownItemException;
    
    /**
     * Emit an order for items.
     * The provider returns the delay for delivering the items.
     * 
     * @param store  the store that emits the order
     * @param item   the item ordered
     * @param qty    the quantity ordered
     * @return       the delay (in hours)
     */
    public int order(Object item, int qty )
    throws UnknownItemException;
}
