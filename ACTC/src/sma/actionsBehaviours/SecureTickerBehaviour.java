package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import jade.core.behaviours.TickerBehaviour;
import princ.Principal;
import sma.AbstractAgent;

public abstract class SecureTickerBehaviour extends TickerBehaviour {
	
private static final long serialVersionUID = 1L;
	
	public SecureTickerBehaviour(final AbstractAgent myagent) {
		super(myagent, 200);
	}

	@Override
	protected void onTick() {
		
		Principal.entrantLock.lock();
		Tick();
		Principal.entrantLock.unlock();
	}
	abstract void Tick();
}
