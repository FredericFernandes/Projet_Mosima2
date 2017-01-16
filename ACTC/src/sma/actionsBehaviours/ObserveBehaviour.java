package sma.actionsBehaviours;

import sma.AbstractAgent;
import sma.agents.SmartAgent;

import com.jme3.math.Vector3f;

import env.jme.Situation;
import princ.Principal;

public class ObserveBehaviour extends SecureTickerBehaviour {

	private static final long serialVersionUID = 1L;

	/*
	 * Behaviour principal : L'agent observe la carte et agit en conséquence de
	 * ce qu'il voit.
	 */

	public ObserveBehaviour(final AbstractAgent myagent) {
		super(myagent);
	}

	@Override
	protected void Tick() {

		
		SmartAgent agent = ((SmartAgent) this.myAgent);
		 if (Principal.printDebug){
			 System.out.println("--------------------------");
			 System.out.println("Tick ObserveBehaviour "+agent.getLocalName());
		 }
		
		Vector3f currentpos = agent.getCurrentPosition();
		Vector3f dest = agent.getDestination();
		Situation situation = agent.observeMap();
		if(situation==null) return;
		// Si la liste des agents à portée de vue est non nulle
		if (situation.agents.size() != 0) {

			// Si il est possible d'observer l'agent, on le pourchasse
			if (agent.seeEnemy()) {

				System.out.println("I see Enemy ");

				String nameEnemy = "";
				if (situation.agents.size() != 0){
					nameEnemy = situation.agents.get(0).getSecond();
				}
				Vector3f pos = agent.realEnv.getCurrentPosition(nameEnemy);
				if (pos != null)
					agent.enemyLastPos = pos;

				agent.addBehaviour(new HuntBehaviour(agent));
			} 
			// Sinon, si l'on n'a pas encore visité la derniere position vue de l'agent, on s'y rend
			else if (dest != null && agent.enemyLastPos != null && !approximativeEqualsCoordinates(currentpos,agent.enemyLastPos)){
				agent.moveTo(agent.enemyLastPos);
			}
			// Sinon, on tourne sur soi-même pour observer les alentours
			else {
				agent.enemyLastPos = null;
				agent.addBehaviour(new SpinBehaviour(agent));
			}
		}
		// Sinon, si aucun agent n'est à portée, on effectue le même comportement que lorsqu'on ne peut pas le voir
		else {
			if (dest != null && agent.enemyLastPos != null && !approximativeEqualsCoordinates(currentpos,agent.enemyLastPos)){
				agent.moveTo(agent.enemyLastPos);
			}
			// Sinon, on tourne sur soi-même pour observer les alentours
			else {
				agent.enemyLastPos = null;
				agent.addBehaviour(new SpinBehaviour(agent));
			}
		}
	}

	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}

	private boolean approximativeEquals(float a, float b) {
		return b - 1.5 <= a && a <= b + 1.5;
	}
}