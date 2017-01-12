package sma.agents;

import env.jme.Environment;
import jade.core.behaviours.SequentialBehaviour;
import sma.AbstractAgent;
import sma.actionsBehaviours.ChaseBehaviour;
import sma.actionsBehaviours.FallBehaviour;

public class ChaseAgent extends AbstractAgent {

	private static final long serialVersionUID = -8223709352673984179L;


	/**
	 * True to create a friend, false otherwise 
	 */
	public boolean friendorFoe;
	public ChaseBehaviour chaseBehav;
	
	protected void setup(){

			super.setup();			
			//get the parameters given into the object[]. In the current case, the environment where the agent will evolve
			final Object[] args = getArguments();
			if(args[0]!=null && args[1]!=null){

				this.friendorFoe = ((boolean)args[1]);

				if (friendorFoe) {
					deployAgent((Environment) args[0]);
				} else {
					deployEnemy((Environment) args[0]);
				}
				initStartPostion();
			}else{
				System.err.println("Malfunction during parameter's loading of agent"+ this.getClass().getName());
				System.exit(-1);
			}
			
			chaseBehav = new ChaseBehaviour(this);
			seq.addSubBehaviour(chaseBehav);
			
			addBehaviour(seq);
			String type = friendorFoe? "Agent":"Enemy";
			System.out.println("the "+this.getLocalName()+ " is started. Type: " +type);





	}




}