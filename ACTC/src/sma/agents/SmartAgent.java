package sma.agents;

import com.jme3.math.Vector3f;

import jade.core.behaviours.SequentialBehaviour;

import sma.AbstractAgent;
import sma.actionsBehaviours.FallBehaviour;
import sma.actionsBehaviours.ObserveBehaviour;
import env.jme.Environment;

public class SmartAgent extends AbstractAgent {


	public Vector3f highestPos;
	
	public Vector3f enemyLastPos;
	
	public double patrol = 0;
	public int patrolDirection = 0;

	public boolean justOnTop = true;
	public double timeTop = -1;
	
	private static final long serialVersionUID = -8223709352673984179L;


	/**
	 * True to create a friend, false otherwise 
	 */
	public boolean friendorFoe;

	public ObserveBehaviour obsBehaviour;

	protected void setup(){
				super.setup();	
				// SETUP
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
				
				// BEHAVIOURS		
				obsBehaviour = new ObserveBehaviour(this);
				seq.addSubBehaviour(obsBehaviour);
				addBehaviour(seq);
				
				String type = friendorFoe? "Agent":"Enemy";
				System.out.println("the "+this.getLocalName()+ " is started. SmartAgent ,  Type: " +type);
		


	}




}