package tp2_may.bank;

import tp2_obj.interfaces.IAccount;

@SuppressWarnings("all")
public abstract class AccountComponent {
  public interface Requires {
  }
  
  public interface Component extends AccountComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IAccount account();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements AccountComponent.Component, AccountComponent.Parts {
    private final AccountComponent.Requires bridge;
    
    private final AccountComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_account() {
      assert this.account == null: "This is a bug.";
      this.account = this.implementation.make_account();
      if (this.account == null) {
      	throw new RuntimeException("make_account() in tp2_may.bank.AccountComponent shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_account();
    }
    
    public ComponentImpl(final AccountComponent implem, final AccountComponent.Requires b, final boolean doInits) {
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
    
    private IAccount account;
    
    public IAccount account() {
      return this.account;
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
  
  private AccountComponent.ComponentImpl selfComponent;
  
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
  protected AccountComponent.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IAccount make_account();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected AccountComponent.Requires requires() {
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
  protected AccountComponent.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized AccountComponent.Component _newComponent(final AccountComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AccountComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    AccountComponent.ComponentImpl  _comp = new AccountComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public AccountComponent.Component newComponent() {
    return this._newComponent(new AccountComponent.Requires() {}, true);
  }
}
