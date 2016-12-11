package sma;


import java.util.Random;

import com.jme3.math.Vector3f;


import env.EnvironmentManager;
import env.jme.Environment;
import env.jme.Situation;

import jade.core.Agent;
import sma.actionsBehaviours.LegalActions.LegalAction;

public class AbstractAgent extends Agent implements EnvironmentManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Environment realEnv;
	private String enemy;

	public AbstractAgent() {
		registerO2AInterface(EnvironmentManager.class, this);
	}

	public Vector3f getCurrentPosition() {
		return realEnv.getCurrentPosition(getLocalName());
	}

	public Vector3f getDestination() {
		return realEnv.getDestination(getLocalName());
	}

	public Situation observeAgents() {
		return realEnv.observe(getLocalName(), 10);
	}

	public void lookAt(LegalAction direction) {
		realEnv.lookAt(getLocalName(), direction);
	}

	public boolean moveTo(Vector3f myDestination) {
		return realEnv.moveTo(getLocalName(), myDestination);
	}

	public boolean cardinalMove(LegalAction direction) {
		return realEnv.cardinalMove(getLocalName(), direction);
	}

	public boolean randomMove() {
		return realEnv.randomMove(getLocalName());
	}

	public boolean shoot(String target) {
		return realEnv.shoot(getLocalName(), target);
	}

	public void randomAction() {
		int randint = new Random().nextInt(LegalAction.values().length);
		LegalAction[] actions = LegalAction.values();
		LegalAction action = actions[randint];
		//System.out.println(getLocalName()+"'s action :"+action);
		executeAction(action);
	}
	public void randomMoveAction() {
		int minAction = LegalAction.MOVE_NORTH.id;
		int maxAction = LegalAction.MOVE_NORTHWEST.id;
		int randint =  new Random().nextInt((maxAction - minAction) + 1) + minAction;	
		LegalAction[] actions = LegalAction.values();
		LegalAction action = actions[randint];
		//System.out.println(getLocalName()+"'s action :"+action);
		executeAction(action);
	}

	/**
	 * Deploy an agent tagged as a player
	 */
	public void deployAgent(Environment env) {
		realEnv = env;
		realEnv.deployAgent(getLocalName(), "player");
		setupEnemy();
	}

	/**
	 * Deploy an agent tagged as an enemy
	 */
	public void deployEnemy(Environment env) {
		realEnv = env;
		realEnv.deployAgent(getLocalName(), "enemy");
		setupEnemy();
	}

	protected void setup() {
		super.setup();
	}
	private void setupEnemy(){
		enemy = (getLocalName().equals("Player1"))? "Player2" : "Player1";
		
	}
	private void executeAction(LegalAction action){
		if (action.id==0) {
			shoot(enemy);
		}
		else if (action.id < 9) {
			cardinalMove(action);
		}
		else {
			lookAt(action);
		}
	}
	
	public boolean seeEnemy(){
		//System.out.println(" seeMyEnemy name : "+getLocalName()+" enemy "+ enemy);
		return realEnv.isVisible(getLocalName(), enemy);
	}
	public void moveToEnemy(){
		Vector3f enemyPos = realEnv.getCurrentPosition(enemy);
		moveTo(enemyPos);
	}
	public void directionalMoveEnemy(){
		realEnv.move(getLocalName(), enemy);
	}
}
