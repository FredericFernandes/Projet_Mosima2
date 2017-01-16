package env.jme;

import java.util.List;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import sma.actionsBehaviours.LegalActions.LegalAction;


/**
 * Class representing a situation at a given moment.
 * 
 * @author WonbinLIM
 *
 */
public class Situation {
	public int fieldOfViewLimit;
	//public String direction;
	public LegalAction lastAction;
//	public Vector3f supAltitude;
	public Vector3f agentAltitude;
	public Vector3f minAltitude;
	public Vector3f maxAltitude;
	public float avgAltitude;
	public int fieldOfView;
	public float maxDepth;
	public double consistency;
	public List<Tuple2<Vector3f, String>> agents;
	
	
	public Situation(int fieldOfViewLimit, LegalAction action, /*Vector3f supA,*/ Vector3f agentA, Vector3f minA, Vector3f maxA, float avgA, int fieldOfview, float maxDepth, double consistency) {
		this.fieldOfViewLimit = fieldOfViewLimit;
		this.lastAction = action;
		/*this.supAltitude = supA;*/
		this.agentAltitude = agentA;
		this.minAltitude = minA;
		this.maxAltitude = maxA;
		this.avgAltitude = avgA;
		this.fieldOfView = fieldOfview;
		this.maxDepth = maxDepth;
		this.consistency = consistency;
	}
	
	public Situation(int fieldOfViewLimit, LegalAction action, /*Vector3f supA,*/ Vector3f agentA, Vector3f minA, Vector3f maxA, float avgA, int fieldOfview, float maxDepth, double consistency, List<Tuple2<Vector3f, String>> agents) {
		this.fieldOfViewLimit = fieldOfViewLimit;
		this.lastAction = action;
		/*this.supAltitude = supA;*/
		this.agentAltitude = agentA;
		this.minAltitude = minA;
		this.maxAltitude = maxA;
		this.avgAltitude = avgA;
		this.fieldOfView = fieldOfview;
		this.maxDepth = maxDepth;
		this.consistency = consistency;
		this.agents = agents;
	}

	@Override
	public String toString() {
		return "Situation [fieldOfViewLimit=" + fieldOfViewLimit + "\n, lastAction=" + lastAction + "\n, agentAltitude="
				+ agentAltitude + "\n, minAltitude=" + minAltitude + "\n, maxAltitude=" + maxAltitude + "\n, avgAltitude="
				+ avgAltitude + "\n, fieldOfView=" + fieldOfView + "\n, maxDepth=" + maxDepth + "\n, consistency="
				+ consistency + "\n, agents=" + agents + "]";
	}
	
	
}
