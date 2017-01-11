package sma.actionsBehaviours;

import jade.core.behaviours.OneShotBehaviour;
import sma.AbstractAgent;
import sma.actionsBehaviours.LegalActions.LegalAction;
import sma.agents.SmartAgent;
import env.jme.Situation;

public class SpinBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SpinBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		SmartAgent ag = ((SmartAgent)this.myAgent);
		
		ag.lookAt(LegalAction.values()[9]);
		Situation highest = ag.observeMap();
		
		Situation s;
		int i = -1;
		for(i = 9; i < 17 ; i++){
			ag.lookAt(LegalAction.values()[i]);
			
			s = ag.observeMap();
			
			if(s.agents.size() != 0){
				String nameEnemy = s.agents.get(0).getSecond(); 
				if(ag.realEnv.isVisible(ag.getLocalName(), nameEnemy))
					break;
			}
			if(s.maxAltitude != null && highest.maxAltitude == null)
				highest = s;
			else if(s.maxAltitude != null && highest.maxAltitude != null && s.maxAltitude.y > highest.maxAltitude.y){
				highest = s;
			}
		}
		
		// Si i != 17 , on a trouvé l'ennemi en spinnant
		if(i != 17){
			ag.addBehaviour(new HuntBehaviour(ag));
		}else{
			ag.highestPos = highest.maxAltitude;
			ag.addBehaviour(new ClimbBehaviour(ag));
		}
		
	}
}
