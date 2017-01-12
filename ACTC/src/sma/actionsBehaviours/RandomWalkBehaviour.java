package sma.actionsBehaviours;

import com.jme3.math.Vector3f;
import jade.core.behaviours.TickerBehaviour;
import princ.Principal;
import sma.AbstractAgent;

public class RandomWalkBehaviour extends SecureTickerBehaviour {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RandomWalkBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	protected void Tick() {
		//System.out.println("Tick RandomWalkBehaviour");
		Vector3f currentpos  = ((AbstractAgent)this.myAgent).getCurrentPosition();
		Vector3f dest = ((AbstractAgent)this.myAgent).getDestination();	
		if (dest==null || approximativeEqualsCoordinates(currentpos, dest)) {
			((AbstractAgent)this.myAgent).randomMove();
			//((AbstractAgent)this.myAgent).randomAction(enemy);
		}
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-1.5 <= a && a <= b+1.5;
	}

}
