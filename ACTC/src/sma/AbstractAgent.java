package sma;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import env.EnvironmentManager;
import env.jme.Environment;
import env.jme.Situation;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import sma.actionsBehaviours.LegalActions.LegalAction;

public class AbstractAgent extends Agent implements EnvironmentManager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Environment realEnv;
	
	
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
	
	public void randomAction(String target) {
		int randint = new Random().nextInt(LegalAction.values().length);
		LegalAction[] actions = LegalAction.values();
		LegalAction action = actions[randint];
		System.out.println(getLocalName()+"'s action :"+action);
		if (randint==0) {
			shoot(target);
		}
		else if (randint < 9) {
			cardinalMove(action);
		}
		else {
			lookAt(action);
		}
	}

	/**
	 * Deploy an agent tagged as a player
	 */
	public void deployAgent(Environment env) {
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "player");
	}

	/**
	 * Deploy an agent tagged as an enemy
	 */
	public void deployEnemy(Environment env) {
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "enemy");
	}

	protected void setup() {
		super.setup();
	}
}
