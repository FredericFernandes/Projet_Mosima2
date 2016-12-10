package sma.actionsBehaviours;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import env.jme.Environment;
import env.jme.PlayerControl;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.actionsBehaviours.LegalActions.LegalAction;

public class RandomWalkBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RandomWalkBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
	}

	@Override
	protected void onTick() {
		
		Environment env = (Environment) this.myAgent.getArguments()[0];
		// TODO Auto-generated method stub
		Vector3f currentpos  = ((AbstractAgent)this.myAgent).getCurrentPosition();
		Vector3f dest = ((AbstractAgent)this.myAgent).getDestination();
		
	//	AbstractAgent me = ((AbstractAgent)this.myAgent);

//		if (this.myAgent.getLocalName().equals("Player1")) {
//			me.moveTo(new Vector3f(60,0,60));
//			// A faire : camera
//		}
		
		if (dest==null || approximativeEqualsCoordinates(currentpos, dest)) {
			((AbstractAgent)this.myAgent).randomMove();
			String enemy = "";
			if (this.myAgent.getLocalName().equals("Player1")) {
				enemy = "Player2";
			}
			else {
				enemy = "Player1";
			}
			//((AbstractAgent)this.myAgent).randomAction(enemy);
		}
		
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}

}
