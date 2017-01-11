package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class FallBehaviour extends TickerBehaviour {

	boolean isDone = false;

	private static final long serialVersionUID = 1L;
	
	public FallBehaviour(final AbstractAgent myagent) {
		super(myagent,200);
	}

	@Override
	public void onTick() {
		SmartAgent ag = ((SmartAgent)this.myAgent);
		if(ag.lastpos != null && ag.lastpos == ag.getCurrentPosition() && (ag.lastpos.y < -1))
			stop();
		ag.lastpos = ag.getCurrentPosition();
	}
}