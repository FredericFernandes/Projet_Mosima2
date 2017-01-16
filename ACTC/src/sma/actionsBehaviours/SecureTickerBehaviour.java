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
			AbstractAgent ag = ((AbstractAgent)this.myAgent);
			if((ag.realEnv.isDead(ag.getLocalName())) || (ag.realEnv.isDead(ag.getEnemy()))){
				stop();  // On stop le Behaviour si un des deux agents est mort
				this.myAgent.doDelete();
			}
			
			if(!ag.realEnv.isPaused()){
				Tick(); // Si le jeu n'est pas en pause 
			}

	}
	abstract void Tick();
}
