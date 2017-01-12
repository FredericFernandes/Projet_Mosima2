package sma.actionsBehaviours;


import java.util.Random;

import com.jme3.math.Vector3f;

import env.jme.Situation;
import sma.AbstractAgent;
import sma.actionsBehaviours.LegalActions.LegalAction;
import sma.agents.SmartAgent;

public class ClimbBehaviour extends SecureOneShotBehaviour {


	private static final long serialVersionUID = 1L;

	/*
	 * Behaviour d'ecalade : L'agent essaye d'atteindre le point
	 * d'altitude le plus haut .
	 */
	
	public ClimbBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void oneAction() {
		System.out.println("Tick ClimbBehaviour");
		
		SmartAgent agent= ((SmartAgent)this.myAgent);
		Situation s = agent.observeMap();
		if(agent.highestPos!=null){ // on monte
			agent.moveTo(agent.highestPos);
		}else{
			agent.lookAt(LegalAction.values()[(new Random()).nextInt(8)+9]);
		}
	
		
	}
}