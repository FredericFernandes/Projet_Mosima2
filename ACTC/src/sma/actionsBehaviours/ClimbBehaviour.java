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
		// Si on a atteint le point d'altitude maximum et qu'on est en patrouille, on patrouille
		if(agent.patrol > 0){
			agent.justOnTop = true;
			
			System.out.println("####\n PATROUILLE \n####");
			agent.cardinalMove(LegalAction.values()[agent.patrolDirection - 8]);
			agent.patrol--;
		}
		// Si on n'est pas encore au point d'altitude maximum, et qu'on n'est pas en patrouille, on monte
		else if(agent.highestPos!=null){
			agent.moveTo(agent.highestPos);
		}
		// Si on est au point d'alt max, on effectue une patrouille après y etre resté pendant 5 secondes
		else{
			agent.patrolDirection = (new Random()).nextInt(8)+9;
			agent.lookAt(LegalAction.values()[agent.patrolDirection]);
			
			if( agent.timeTop == -1 || agent.justOnTop){
				agent.timeTop = System.currentTimeMillis();
				agent.justOnTop = false;
			}
			if(System.currentTimeMillis() - agent.timeTop > 5000){
				// On effectue une patrouille pendant 20 ticks
				agent.patrol = 20;

				agent.timeTop = System.currentTimeMillis();
			}	
		}
	
		
	}
}