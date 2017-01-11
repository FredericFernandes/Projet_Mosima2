package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import env.jme.Situation;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class HuntBehaviour extends OneShotBehaviour {


	private static final long serialVersionUID = 1L;
	
	public HuntBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void action() {
		SmartAgent agent= ((SmartAgent)this.myAgent);
		Situation s = agent.observeMap();
		
		String nameEnemy;
		if(s.agents.size() != 0)
			nameEnemy = s.agents.get(0).getSecond(); 
		
		if(agent.canShootEnemy()){
			agent.addBehaviour(new ShootBehaviour(agent));
		}
		else{
			agent.moveToEnemy();
		}
	}
}