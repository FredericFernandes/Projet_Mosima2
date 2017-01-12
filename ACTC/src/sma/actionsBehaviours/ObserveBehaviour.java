package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import env.jme.Situation;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

public class ObserveBehaviour extends SecureTickerBehaviour {


	private static final long serialVersionUID = 1L;

	public ObserveBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	protected void Tick() {

		System.out.println("Tick ObserveBehaviour");
		AbstractAgent agent = ((AbstractAgent)this.myAgent);
		Vector3f currentpos  = agent.getCurrentPosition();
		Vector3f dest = agent.getDestination();

		Situation situation = agent.observeMap();
		
		if(situation.agents.size() != 0){
			if(agent.seeEnemy()){
				System.out.println("I see Enemy ");
				agent.addBehaviour(new HuntBehaviour(agent));
			}
			else{
				agent.addBehaviour(new SpinBehaviour(agent));
			}
		}
		else{
			agent.addBehaviour(new SpinBehaviour(agent));
		}
	}

	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}

	private boolean approximativeEquals(float a, float b) {
		return b-1.5 <= a && a <= b+1.5;
	}
}