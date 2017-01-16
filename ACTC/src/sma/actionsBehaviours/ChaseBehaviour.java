package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import jade.core.behaviours.TickerBehaviour;
import princ.Principal;
import sma.AbstractAgent;

public class ChaseBehaviour extends SecureTickerBehaviour {


	private static final long serialVersionUID = 1L;
	
	public ChaseBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}
	
	@Override
	void Tick() {
		AbstractAgent him = ((AbstractAgent)this.myAgent);
		 if (Principal.printDebug) System.out.println("Tick ChaseBehaviour "+him.getLocalName());
		Vector3f currentpos  = him.getCurrentPosition();
		Vector3f dest = him.getDestination();
		
		if(him.seeEnemy()){
			//System.out.println(" I see my target ");;
			him.moveToEnemy();
			him.observeMap();
			if(him.canShootEnemy())
				him.addBehaviour(new ShootBehaviour(him));
		}
		
		if (dest==null || approximativeEqualsCoordinates(currentpos, dest)) {
			him.randomMoveAction();
		}
		
	}
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-1.5 <= a && a <= b+1.5;
	}


}