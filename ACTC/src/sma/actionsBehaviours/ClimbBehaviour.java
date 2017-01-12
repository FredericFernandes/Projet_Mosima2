package sma.actionsBehaviours;


import com.jme3.math.Vector3f;

import env.jme.Situation;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class ClimbBehaviour extends SecureOneShotBehaviour {


	private static final long serialVersionUID = 1L;
	
	public ClimbBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void oneAction() {
		SmartAgent agent= ((SmartAgent)this.myAgent);
		Situation s = agent.observeMap();
		System.out.println(agent.highestPos);
		agent.moveTo(agent.highestPos);
//		if(agent.highestPos!=null){ // on monte
//			agent.moveTo(agent.highestPos);
//		}else{
//			// à voir
//			agent.addBehaviour(new ObserveBehaviour(agent));
//		}
	
		
	}
}