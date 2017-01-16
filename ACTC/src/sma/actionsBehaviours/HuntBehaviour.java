package sma.actionsBehaviours;

import com.jme3.math.Vector3f;

import env.jme.Environment;
import env.jme.PlayerControl;
import env.jme.Situation;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import princ.Principal;
import sma.AbstractAgent;
import sma.agents.SmartAgent;

public class HuntBehaviour extends SecureOneShotBehaviour {

	private static final long serialVersionUID = 1L;

	/*
	 * Behaviour de chasse : Le comportement à adopter par l'agent
	 * s'il voit l'ennemi.
	 */
	
	public HuntBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	public void oneAction() {
		SmartAgent agent= ((SmartAgent)this.myAgent);
		 if (Principal.printDebug)System.out.println("Tick HuntBehaviour "+agent.getLocalName());
		
		Situation s = agent.observeMap();
		if(s==null)return;
		String nameEnemy = "";
		if(s.agents.size() != 0)
			nameEnemy = s.agents.get(0).getSecond(); 
		
		// On regarde dans la direction de l'ennemi
		if(agent.realEnv.getCurrentPosition(nameEnemy) != null)
			agent.realEnv.getPlayers().get(agent.getLocalName()).getControl(PlayerControl.class).cam.lookAtDirection(agent.realEnv.getCurrentPosition(nameEnemy), Vector3f.UNIT_Y);
		
		// S'il est a portée de tir, on tire sur l'ennemi
		if(agent.canShootEnemy()){
			System.out.println("shoot");
			agent.addBehaviour(new ShootBehaviour(agent));
		}
		// Sinon, on se déplace vers lui
		else{
			agent.moveToEnemy();
		}
	}
}