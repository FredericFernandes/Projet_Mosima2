package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import env.jme.Situation;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class ClimbBehaviour extends OneShotBehaviour {


	private static final long serialVersionUID = 1L;
	
	public ClimbBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		SmartAgent agent= ((SmartAgent)this.myAgent);
		Situation s = agent.observeMap();
		
		agent.moveTo(agent.highestPos);
	}
}