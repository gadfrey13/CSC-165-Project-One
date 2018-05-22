package a1.GameObject;

import java.io.IOException;

import ray.rage.Engine;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.scene.Camera;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class Dolphin {
	
	private String myName, myPath;
	private Engine myEng;
	private SceneManager mySm;
	private SceneNode dolphinN;
	private Camera camera;
	public Dolphin(String name, String path, Engine eng, SceneManager sm, Camera c){
		myName = name;
		myPath = path;
		myEng = eng;
		mySm = sm;
		camera = c;
	}
	
	public SceneNode create() throws IOException{
		Entity dolphinE = mySm.createEntity(myName, myPath);
		dolphinE.setPrimitive(Primitive.TRIANGLES);
		
		dolphinN = mySm.getRootSceneNode().createChildSceneNode(dolphinE.getName() +"Node");
		dolphinN.attachObject(dolphinE);
	    Angle angleDolphin = Degreef.createFrom(180);
	    Vector3f  V = camera.getUp(); 
	    //dolphinN.setLocalPosition(camera.getPo());
	    dolphinN.rotate(angleDolphin,V);
	    dolphinN.setLocalPosition(1.0f, 1.0f, 19.0f);
		return dolphinN;
	}
	
	public SceneNode getDolphin(){
		return dolphinN;
	}
	
	public Vector3 location(){
		return dolphinN.getLocalPosition();
	}
	
	public boolean numberOfAttachObject(){
		int num = dolphinN.getAttachedObjectCount();
		boolean b = false;
		if(num <= 2){
			b = true;
		}
		
		return b;
	}
	
	public int numberOfChildrenObject(){
		return dolphinN.getAttachedObjectCount();
	}
}
