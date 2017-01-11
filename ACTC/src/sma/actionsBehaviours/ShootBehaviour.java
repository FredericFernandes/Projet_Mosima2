package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

public class ShootBehaviour extends OneShotBehaviour {


	private static final long serialVersionUID = 1L;
	
	public ShootBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		
		AbstractAgent him = ((AbstractAgent)this.myAgent);
		String enemyName = him.observeAgents().get(0).getSecond();
		
		System.out.println("###\n###\nShoot !\n###\n###\n");
		//him.shoot(enemyName);	
	}
}