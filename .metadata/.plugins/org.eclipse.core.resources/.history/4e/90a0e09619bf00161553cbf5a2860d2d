package env.jme;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;


/**
 * Class for the player object's moving.
 * @author WonbinLIM
 *
 */
public class PlayerControl extends CharacterControl{
	
	public TerrainQuad terrain;
	private Camera cam;
	// boolean for the destination-moving function
	private boolean ismoving = false;
	private Vector3f destination;
	// boolean for the direction-moving function
	private boolean ismoving2 = false;
	private final float STEP = 2;
	private Vector3f initialPosition;
	private Vector3f direction;
//	private float destY;
//	private Vector3f lastPosition;
//	private AnimChannel channel;
	
	
	
	public PlayerControl(CollisionShape shape, float stepHeight, TerrainQuad terrain) {
		super(shape, stepHeight);
        this.terrain = terrain;
        	  
    }
	
//	public void setAnim(Spatial spatial) {
//		AnimControl control = spatial.getControl(AnimControl.class);
//	    channel = control.createChannel();
//	    channel.setAnim("stand");	
//	}
	
	public void update(float tpf) {
		super.update(tpf);
		
		if (this.ismoving) {
			if (!equalsCoordinates(this.spatial.getWorldTranslation().x, destination.x) /*|| !equalsCoordinates(player.getWorldTranslation().z, dest.z)*/ || !equalsCoordinates(this.spatial.getWorldTranslation().z, destination.z)) {
				Vector3f dir = this.destination.subtract(this.spatial.getWorldTranslation());
				
				dir.setY(dir.getY()+10);
				
				setViewDirection(dir.clone().setY(0));
				dir.normalizeLocal();
				dir.multLocal(0.8f);
//				dir.setY(terrain.getHeightmapHeight(new Vector2f(dir.x,dir.z))-252);
				setWalkDirection(dir);
				cam.setLocation(this.spatial.getWorldTranslation());
				cam.lookAtDirection(dir, Vector3f.UNIT_Y);
				
			}
			else {
//				System.out.println("arrived");
//				channel.setAnim("stand");
				setViewDirection(this.spatial.getWorldTranslation().clone().setY(0));
				cam.setLocation(this.spatial.getWorldTranslation());
				cam.lookAtDirection(getViewDirection(), Vector3f.UNIT_Y);
				setWalkDirection(new Vector3f(0, 0, 0));
//				setViewDirection(getViewDirection().setY(getViewDirection().y-0.5f));
				this.ismoving = false;
				this.destination = null;
				
			}
		}
		
		if (this.ismoving2) {
			float distance = initialPosition.distance(this.spatial.getWorldTranslation());
			if (distance < STEP) {
//				System.out.println("moving");
				setViewDirection(direction.setY(0));
				direction.normalizeLocal();
				direction.multLocal(0.8f);
				setWalkDirection(direction);
				cam.setLocation(this.spatial.getWorldTranslation());
				cam.lookAtDirection(direction, Vector3f.UNIT_Y);
			}
			else {
//				System.out.println("walked a step");
				cam.setLocation(this.spatial.getWorldTranslation());
				cam.lookAtDirection(getViewDirection(), Vector3f.UNIT_Y);
				setWalkDirection(new Vector3f(0, 0, 0));
				this.initialPosition = null;
				this.direction = null;
				this.ismoving2 = false;
			}
		}
	}
	
	private boolean equalsCoordinates(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}
	
	/**
	 * Moves the player to the destination coordinates in the map.
	 * @param destination
	 */
	public void moveTo(Vector3f destination) {
		this.destination = destination;
		ismoving = true;
//		channel.setAnim("Walk", 0.50f);
	}
	
	public void move(Vector3f direction) {
		initialPosition = this.spatial.getWorldTranslation().clone();
		this.direction = direction;
		ismoving2 = true;
	}
	
	public Vector3f getDestination() {
		return destination;
	}

//	public void setDestination(Vector3f destination) {
//		this.destination = destination;
//		this.destY = destination.y;
//		
//	}
	
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	
	
	
	
}
