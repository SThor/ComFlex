package tp2_may.bank;

import tp2_may.bank.AccountComponent;
import tp2_may.bank.DeskComponent;
import tp2_obj.interfaces.IAccount;
import tp2_obj.interfaces.IBank;

@SuppressWarnings("all")
public abstract class BankComponent {
  public interface Requires {
  }
  
  public interface Component extends BankComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBank bank();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DeskComponent.Component desk();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComponent.Component estore();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComponent.Component anne();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComponent.Component bob();
  }
  
  public static class ComponentImpl implements BankComponent.Component, BankComponent.Parts {
    private final BankComponent.Requires bridge;
    
    private final BankComponent implementation;
    
    public void start() {
      assert this.desk != null: "This is a bug.";
      ((DeskComponent.ComponentImpl) this.desk).start();
      assert this.estore != null: "This is a bug.";
      ((AccountComponent.ComponentImpl) this.estore).start();
      assert this.anne != null: "This is a bug.";
      ((AccountComponent.ComponentImpl) this.anne).start();
      assert this.bob != null: "This is a bug.";
      ((AccountComponent.ComponentImpl) this.bob).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_desk() {
      assert this.desk == null: "This is a bug.";
      assert this.implem_desk == null: "This is a bug.";
      this.implem_desk = this.implementation.make_desk();
      if (this.implem_desk == null) {
      	throw new RuntimeException("make_desk() in tp2_may.bank.BankComponent should not return null.");
      }
      this.desk = this.implem_desk._newComponent(new BridgeImpl_desk(), false);
    }
    
    private void init_estore() {
      assert this.estore == null: "This is a bug.";
      assert this.implem_estore == null: "This is a bug.";
      this.implem_estore = this.implementation.make_estore();
      if (this.implem_estore == null) {
      	throw new RuntimeException("make_estore() in tp2_may.bank.BankComponent should not return null.");
      }
      this.estore = this.implem_estore._newComponent(new BridgeImpl_estore(), false);
    }
    
    private void init_anne() {
      assert this.anne == null: "This is a bug.";
      assert this.implem_anne == null: "This is a bug.";
      this.implem_anne = this.implementation.make_anne();
      if (this.implem_anne == null) {
      	throw new RuntimeException("make_anne() in tp2_may.bank.BankComponent should not return null.");
      }
      this.anne = this.implem_anne._newComponent(new BridgeImpl_anne(), false);
    }
    
    private void init_bob() {
      assert this.bob == null: "This is a bug.";
      assert this.implem_bob == null: "This is a bug.";
      this.implem_bob = this.implementation.make_bob();
      if (this.implem_bob == null) {
      	throw new RuntimeException("make_bob() in tp2_may.bank.BankComponent should not return null.");
      }
      this.bob = this.implem_bob._newComponent(new BridgeImpl_bob(), false);
    }
    
    protected void initParts() {
      init_desk();
      init_estore();
      init_anne();
      init_bob();
    }
    
    protected void init_bank() {
      // nothing to do here
    }
    
    protected void initProvidedPorts() {
      init_bank();
    }
    
    public ComponentImpl(final BankComponent implem, final BankComponent.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    public IBank bank() {
      return this.desk().
      bank()
      ;
    }
    
    private DeskComponent.Component desk;
    
    private DeskComponent implem_desk;
    
    private final class BridgeImpl_desk implements DeskComponent.Requires {
      public final IAccount acc1() {
        return BankComponent.ComponentImpl.this.estore().
        account()
        ;
      }
      
      public final IAccount acc2() {
        return BankComponent.ComponentImpl.this.anne().
        account()
        ;
      }
      
      public final IAccount acc3() {
        return BankComponent.ComponentImpl.this.bob().
        account()
        ;
      }
    }
    
    public final DeskComponent.Component desk() {
      return this.desk;
    }
    
    private AccountComponent.Component estore;
    
    private AccountComponent implem_estore;
    
    private final class BridgeImpl_estore implements AccountComponent.Requires {
    }
    
    public final AccountComponent.Component estore() {
      return this.estore;
    }
    
    private AccountComponent.Component anne;
    
    private AccountComponent implem_anne;
    
    private final class BridgeImpl_anne implements AccountComponent.Requires {
    }
    
    public final AccountComponent.Component anne() {
      return this.anne;
    }
    
    private AccountComponent.Component bob;
    
    private AccountComponent implem_bob;
    
    private final class BridgeImpl_bob implements AccountComponent.Requires {
    }
    
    public final AccountComponent.Component bob() {
      return this.bob;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private BankComponent.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected BankComponent.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected BankComponent.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected BankComponent.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract DeskComponent make_desk();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComponent make_estore();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComponent make_anne();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComponent make_bob();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized BankComponent.Component _newComponent(final BankComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BankComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    BankComponent.ComponentImpl  _comp = new BankComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BankComponent.Component newComponent() {
    return this._newComponent(new BankComponent.Requires() {}, true);
  }
}
