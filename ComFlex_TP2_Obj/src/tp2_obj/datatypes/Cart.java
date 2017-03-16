package tp2_obj.datatypes;

import java.util.HashMap;
import java.util.Map;

import tp2_obj.interfaces.IClient;

public class Cart {
    
    /** The client owning the cart. */
    public IClient client;
    
    /** The items currently added to the cart. key=item, value=quantity. */
    private Map items = new HashMap();
    
    
    public Cart(IClient client) {
        this.client = client;
    }
    
    public void addItem( Object item, int qty ) {
        int oldQty = 0;
        if ( items.containsKey(item) ) {
            // The item has already been put in the cart
            // Increase the number
            oldQty = ((Integer) items.get(item)).intValue();
        }
        items.put( item, new Integer(qty+oldQty) );
    }
    
    public Map getItems() {
        return items;
    }
}
