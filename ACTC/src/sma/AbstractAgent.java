package sma;


import java.util.List;
import java.util.Random;

import javax.swing.text.Position;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import env.EnvironmentManager;
import env.jme.Environment;
import env.jme.Situation;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import sma.actionsBehaviours.FallBehaviour;
import sma.actionsBehaviours.LegalActions.LegalAction;

public class AbstractAgent extends Agent implements EnvironmentManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Environment realEnv;
	private String enemy;
	private Vector3f startPostion;
	protected SequentialBehaviour seq;
	protected FallBehaviour fallBehaviour;
	public Vector3f lastpos;
	
	public AbstractAgent() {
		registerO2AInterface(EnvironmentManager.class, this);
	}

	public Vector3f getCurrentPosition() {
		return realEnv.getCurrentPosition(getLocalName());
	}

	public Vector3f getDestination() {
		return realEnv.getDestination(getLocalName());
	}

	public Situation observeMap() {
		Situation situation =  realEnv.observe2(getLocalName(), 10);
		return situation;
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
		seq = new SequentialBehaviour(this);
		fallBehaviour = new FallBehaviour(this);
		seq.addSubBehaviour(fallBehaviour);
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
	public boolean canShootEnemy(){
		//System.out.println(" seeMyEnemy name : "+getLocalName()+" enemy "+ enemy);
		return realEnv.isVisibleForShoot(getLocalName(), enemy);
	}
	public void moveToEnemy(){
		Vector3f enemyPos = realEnv.getCurrentPosition(enemy);
		moveTo(enemyPos);
	}
	public void directionalMoveEnemy(){
		realEnv.move(getLocalName(), enemy);
	}
	public List<Tuple2<Vector3f, String>> observeAgents() {
		return realEnv.observeAgents(getLocalName());
	}
	
	public void initStartPostion(){
		System.out.println("initStartPostion");
		startPostion = realEnv.getPostionsStart().get(getLocalName());
	}

	public Vector3f getStartPostion() {
		return startPostion;
	}
	
	public int getHP(){
		return realEnv.getHP(this.getLocalName());
	}
	
	public int getEnemyHP(){
		return realEnv.getHP("Player 2");
	}
	
}
