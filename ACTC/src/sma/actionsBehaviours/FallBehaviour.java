package sma.actionsBehaviours;



import com.jme3.math.Vector3f;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class FallBehaviour extends SecureTickerBehaviour {

	protected boolean isDone = false;
	protected Vector3f startPosition;
	public Vector3f lastpos;
	private static final long serialVersionUID = 1L;
	
	public FallBehaviour(final AbstractAgent myagent) {
		super(myagent,100);
		lastpos= null;
		startPosition = myagent.realEnv.getPostionsStart().get(myagent.getLocalName());
	}

	public void Tick() {
		//System.out.println("Tick FallBehaviour");
		
		Vector3f currentPosition = ((AbstractAgent)myAgent).getCurrentPosition().clone();
		currentPosition.setY(Math.round(currentPosition.getY()));
//		System.out.println(" ");
//		System.out.println(startPosition);
//		System.out.println("lastpos "+lastpos);	
//		System.out.println("getCurrentPosition "+ currentPosition);
		
		if(lastpos != null && lastpos.equals(currentPosition)){
			stop();
			//System.out.println("Stop FallBehaviour");
		}	
		lastpos = currentPosition;
	}
}