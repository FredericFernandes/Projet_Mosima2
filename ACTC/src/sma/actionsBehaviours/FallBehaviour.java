package sma.actionsBehaviours;



import com.jme3.math.Vector3f;
import sma.AbstractAgent;


public class FallBehaviour extends SecureTickerBehaviour {

	protected boolean isDone = false;
	protected Vector3f startPosition;
	private static final long serialVersionUID = 1L;

	/*
	 * Behaviour initial : Sert à bloquer les autres tant que
	 * les agents n'ont pas touché le sol
	 */
	
	public FallBehaviour(final AbstractAgent myagent) {
		super(myagent,200);
		
	}

	public void Tick() {
		AbstractAgent ag = ((AbstractAgent)this.myAgent);
		if (startPosition==null)
			startPosition = ag.realEnv.getPostionsStart().get(ag.getLocalName());
		
		Vector3f currentPosition = ag.getCurrentPosition().clone();
		currentPosition.setY(Math.round(currentPosition.getY()));

		
		if(ag.lastpos != null && ag.lastpos.equals(currentPosition)){
			stop();
			System.out.println("Stop FallBehaviour");
		}	
		ag.lastpos = currentPosition;
	}
}