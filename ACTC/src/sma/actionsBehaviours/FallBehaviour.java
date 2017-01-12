package sma.actionsBehaviours;



import com.jme3.math.Vector3f;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class FallBehaviour extends SecureTickerBehaviour {

	protected boolean isDone = false;
	protected Vector3f startPosition;
	private static final long serialVersionUID = 1L;
	
	public FallBehaviour(final AbstractAgent myagent) {
		super(myagent,200);
		startPosition = myagent.realEnv.getPostionsStart().get(myagent.getLocalName());
	}

	public void Tick() {
		
		Vector3f currentPosition = ((AbstractAgent)myAgent).getCurrentPosition().clone();
		currentPosition.setY(Math.round(currentPosition.getY()));
//		System.out.println(" ");
//		System.out.println(startPosition);
//		System.out.println("lastpos "+lastpos);	
//		System.out.println("getCurrentPosition "+ currentPosition);
		SmartAgent ag = ((SmartAgent)this.myAgent);
		if(ag.lastpos != null && ag.lastpos.equals(currentPosition)){
			stop();
			System.out.println("Stop FallBehaviour");
		}	
		ag.lastpos = currentPosition;
	}
}