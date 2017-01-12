package sma.actionsBehaviours;

import java.util.List;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import jade.core.behaviours.OneShotBehaviour;
import sma.AbstractAgent;

public class ShootBehaviour extends SecureOneShotBehaviour {


	private static final long serialVersionUID = 1L;
	public ShootBehaviour(final AbstractAgent myagent) {	
		super(myagent);
	}

	@Override
	public void oneAction() {
		AbstractAgent him = ((AbstractAgent)this.myAgent);
		List<Tuple2<Vector3f, String>> list = him.observeAgents();
		if(list==null |list.size()==0) return;
		String enemyName = him.observeAgents().get(0).getSecond();

		System.out.println("###\nShoot !\n###");
		him.shoot(enemyName);
	}
}