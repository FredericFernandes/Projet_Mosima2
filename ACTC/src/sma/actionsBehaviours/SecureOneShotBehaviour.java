package sma.actionsBehaviours;

import jade.core.behaviours.OneShotBehaviour;
import princ.Principal;
import sma.AbstractAgent;

public abstract class SecureOneShotBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	public SecureOneShotBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		AbstractAgent ag = ((AbstractAgent)this.myAgent);	
		if((ag.realEnv.isDead(ag.getLocalName())) || (ag.realEnv.isDead(ag.getEnemy()))){
			this.myAgent.doDelete();
			return;  // On stop le Behaviour si un des deux agents est mort
		}
			

		// On execute le Behaviour que si l'agent est en vie
		if(!ag.realEnv.isPaused()){
			oneAction(); // Si le jeu n'est pas en pause 
		}	

	}

	abstract void oneAction();
}
