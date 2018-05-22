package myGameEngine.DolphinActionInput;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MoveForwardNodeAction extends AbstractInputAction {
	
	private SceneNode node,cameraNode;
	private Camera camera;
	public MoveForwardNodeAction(Camera c,SceneNode n){
		node = n;
		camera = c;
	}

	@Override
	public void performAction(float time, Event e) {
		System.out.println("Move Node Forward");
		node.moveForward(0.05f);
		System.out.println(node.getLocalPosition());
		Vector3f addPosition = (Vector3f) Vector3f.createFrom(0.0f, 0.3f, 0.8f);
		Vector3f newPo = (Vector3f) node.getLocalPosition();
		newPo = (Vector3f) newPo.add(addPosition);
		camera.setPo((Vector3f) newPo);
		
		System.out.println("Rotation " + node.getLocalRotation());
		System.out.println("Z axis " + node.getLocalForwardAxis());
		System.out.println("X axis " + node.getLocalRightAxis());
		System.out.println("Y axis " + node.getLocalUpAxis());		
		
	}

}
