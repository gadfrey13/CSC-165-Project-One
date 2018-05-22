package myGameEngine;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.Node;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SceneObject;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class Toggle extends AbstractInputAction {
	private Camera camera;
	
	private SceneNode dolphinNode,cameraNode;
	private boolean t;
	public Toggle(Camera ca, SceneNode c, SceneNode n){
		camera = ca;
		cameraNode = c;
		dolphinNode = n;
	}

	@Override
	public void performAction(float arg0, Event arg1) {
		// TODO Auto-generated method stub
		cameraParent();
		
		if(t){
			dolphinNode.attachObject(camera);
			Vector3f addPosition = (Vector3f) Vector3f.createFrom(0.0f, 0.3f, 0.8f);
			Vector3f newPo = (Vector3f) dolphinNode.getLocalPosition();
			newPo = (Vector3f) newPo.add(addPosition);
			camera.setPo((Vector3f) newPo);
			System.out.println(camera.getParentNode());
		}
		
		if(!t){
			cameraNode.attachObject(camera);
			Vector3f addPosition = (Vector3f) Vector3f.createFrom(1.0f, 0.3f, 1.8f);
			Vector3f newPo = (Vector3f) dolphinNode.getLocalPosition();
			newPo = (Vector3f) newPo.add(addPosition);
			camera.setPo((Vector3f) newPo);
		}
		
	}
	
	public boolean cameraParent(){
		if(camera.getParentNode().equals(dolphinNode)){
			t = false;
		}else{
			t = true;
		}
		
		return t;	
	}
	
	/**
	 * This method returns a boolean value. Wither
	 * the camera is attach to the dolphin.
	 * @return
	 */
	public boolean getOnOff(){
		return t;
	}
	
}
