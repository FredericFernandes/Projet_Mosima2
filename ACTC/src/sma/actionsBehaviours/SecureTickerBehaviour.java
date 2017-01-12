package sma.actionsBehaviours;


import jade.core.behaviours.TickerBehaviour;
import princ.Principal;
import sma.AbstractAgent;


public abstract class SecureTickerBehaviour extends TickerBehaviour {

	private static final long serialVersionUID = 1L;

	public SecureTickerBehaviour(final AbstractAgent myagent) {
		super(myagent, 200);
	}
	public SecureTickerBehaviour(final AbstractAgent myagent,long period) {
		super(myagent, period);
	}

	@Override
	protected void onTick() {
		Principal.lockUpdate.lock();
		try {
			AbstractAgent ag = ((AbstractAgent)this.myAgent);
			if(ag.realEnv.isDead(ag.getLocalName()))
				stop();  // On stop le Behaviour si l'agent est mort
			if(!ag.realEnv.isPaused()){
				Tick(); // Si le jeu a commencé 
			}



		} finally {
			Principal.lockUpdate.unlock();
		}

	}
	abstract void Tick();
}
