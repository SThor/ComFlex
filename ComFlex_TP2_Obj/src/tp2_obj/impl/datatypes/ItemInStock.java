package tp2_obj.impl.datatypes;

import tp2_obj.impl.Provider;
import tp2_obj.interfaces.IProvider;

public class ItemInStock {
    
    /** The total number of ItemInStock instances created. */
    private static int numItems;
    
    /** The index of this item. */
    private int num;
    
    public Object item;
    public int quantity;
    public double price;
    public IProvider provider;

    private ItemInStock() {
        num = numItems++;        
    }
    
    public ItemInStock(Object item, int quantity, double price,
            IProvider provider) {
        this();
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.provider = provider;
    }
    
    public String toString() {
        return "Item #"+num;
    }

}
