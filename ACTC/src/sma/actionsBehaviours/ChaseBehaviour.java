package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

public class ChaseBehaviour extends TickerBehaviour {


	private static final long serialVersionUID = 1L;
	
	public ChaseBehaviour(final AbstractAgent myagent) {
		super(myagent, 200);
	}

	@Override
	protected void onTick() {
		
		AbstractAgent him = ((AbstractAgent)this.myAgent);
		Vector3f currentpos  = him.getCurrentPosition();
		Vector3f dest = him.getDestination();	
		
		if(him.seeEnemy()){
			//System.out.println(" I see my target ");
			him.moveToEnemy();
			//him.directionalMoveEnemy();
		}
		
		if (dest==null || approximativeEqualsCoordinates(currentpos, dest)) {
			//him.randomMove();		
			him.randomMoveAction();
		}
		
		
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}
}