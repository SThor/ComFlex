package tp2_may.store;

import tp2_obj.interfaces.store.IConsult;
import tp2_obj.interfaces.store.IFastLane;
import tp2_obj.interfaces.store.ILane;

@SuppressWarnings("all")
public abstract class ClientComponent {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IFastLane fastLane();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ILane lane();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IConsult consut();
  }
  
  public interface Component extends ClientComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Runnable run();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements ClientComponent.Component, ClientComponent.Parts {
    private final ClientComponent.Requires bridge;
    
    private final ClientComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_run() {
      assert this.run == null: "This is a bug.";
      this.run = this.implementation.make_run();
      if (this.run == null) {
      	throw new RuntimeException("make_run() in tp2_may.store.ClientComponent shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_run();
    }
    
    public ComponentImpl(final ClientComponent implem, final ClientComponent.Requires b, final boolean doInits) {
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
    
    private Runnable run;
    
    public Runnable run() {
      return this.run;
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
  
  private ClientComponent.ComponentImpl selfComponent;
  
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
  protected ClientComponent.Provides provides() {
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
  protected abstract Runnable make_run();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ClientComponent.Requires requires() {
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
  protected ClientComponent.Parts parts() {
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
  public synchronized ClientComponent.Component _newComponent(final ClientComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ClientComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    ClientComponent.ComponentImpl  _comp = new ClientComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
