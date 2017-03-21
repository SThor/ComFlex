package tp2_may.store;

import tp2_obj.interfaces.IBank;
import tp2_obj.interfaces.IProvider;
import tp2_obj.interfaces.store.IConsult;
import tp2_obj.interfaces.store.IFastLane;
import tp2_obj.interfaces.store.ILane;

@SuppressWarnings("all")
public abstract class StoreComponent {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IProvider provider();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IBank bank();
  }
  
  public interface Component extends StoreComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IFastLane fastLane();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public ILane lane();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IConsult consult();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements StoreComponent.Component, StoreComponent.Parts {
    private final StoreComponent.Requires bridge;
    
    private final StoreComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_fastLane() {
      assert this.fastLane == null: "This is a bug.";
      this.fastLane = this.implementation.make_fastLane();
      if (this.fastLane == null) {
      	throw new RuntimeException("make_fastLane() in tp2_may.store.StoreComponent shall not return null.");
      }
    }
    
    protected void init_lane() {
      assert this.lane == null: "This is a bug.";
      this.lane = this.implementation.make_lane();
      if (this.lane == null) {
      	throw new RuntimeException("make_lane() in tp2_may.store.StoreComponent shall not return null.");
      }
    }
    
    protected void init_consult() {
      assert this.consult == null: "This is a bug.";
      this.consult = this.implementation.make_consult();
      if (this.consult == null) {
      	throw new RuntimeException("make_consult() in tp2_may.store.StoreComponent shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_fastLane();
      init_lane();
      init_consult();
    }
    
    public ComponentImpl(final StoreComponent implem, final StoreComponent.Requires b, final boolean doInits) {
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
    
    private IFastLane fastLane;
    
    public IFastLane fastLane() {
      return this.fastLane;
    }
    
    private ILane lane;
    
    public ILane lane() {
      return this.lane;
    }
    
    private IConsult consult;
    
    public IConsult consult() {
      return this.consult;
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
  
  private StoreComponent.ComponentImpl selfComponent;
  
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
  protected StoreComponent.Provides provides() {
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
  protected abstract IFastLane make_fastLane();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ILane make_lane();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IConsult make_consult();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected StoreComponent.Requires requires() {
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
  protected StoreComponent.Parts parts() {
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
  public synchronized StoreComponent.Component _newComponent(final StoreComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of StoreComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    StoreComponent.ComponentImpl  _comp = new StoreComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
