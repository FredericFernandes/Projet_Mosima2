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
		return this.realEnv.getCurrentPosition(getLocalName());
	}

	public Vector3f getDestination() {
		return this.realEnv.getDestination(getLocalName());
	}

	public Situation observeAgents() {
		return this.realEnv.observe(getLocalName(), 10);
	}

	public void lookAt(LegalAction direction) {
		this.realEnv.lookAt(getLocalName(), direction);
	}

	public boolean moveTo(Vector3f myDestination) {
		return this.realEnv.moveTo(getLocalName(), myDestination);
	}

	public boolean cardinalMove(LegalAction direction) {
		return this.realEnv.cardinalMove(getLocalName(), direction);
	}

	public boolean randomMove() {
		return this.realEnv.randomMove(getLocalName());
	}

	public boolean shoot(String target) {
		return this.realEnv.shoot(getLocalName(), target);
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
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "player");
		setupEnemy();
	}

	/**
	 * Deploy an agent tagged as an enemy
	 */
	public void deployEnemy(Environment env) {
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "enemy");
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
	
	public boolean seeMyEnemy(){
		//System.out.println(" seeMyEnemy name : "+getLocalName()+" enemy "+ enemy);
		return this.realEnv.isVisible(getLocalName(), enemy);
	}
}
