package a1.GameObject;

import java.io.IOException;

import myGameEngine.EventHandler;
import myGameEngine.Toggle;
import ray.rage.Engine;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class GameWorld {
	private int size = 10;
	private Dolphin dolphin;
	private Engine eng;
	private SceneManager sm;
	private Prize[] prize = new Prize[size];
	private Camera camera;
	private SceneNode node,bnode;
	private ManualMadeGameObjects man;
	private Bomb bomb[] = new Bomb[size];
	public GameWorld(Engine e, SceneManager s,Camera c){
		eng = e;
		sm = s;
		camera = c;
	}
	
	public void init() throws IOException{
		//Create a dolphin
		dolphin = new Dolphin("myDolphin","dolphinHighPoly.obj", eng, sm,camera);
		dolphin.create();
		man = new ManualMadeGameObjects();
		
		man.createAxes(eng, sm);
		man.createBox(eng, sm);
		
		//Create the prizes
		for(int i = 0 ; i < size; i++){
			prize[i] = new Prize("myPrize" + i, "sphere.obj", eng, sm,(Vector3f) randLoc(25).mult(0.5f));
			prize[i].create();
			storePrize(prize[i].getPrize());
			System.out.println(prize[i].location());
			System.out.println(randLoc(25));
		}
		
		for(int i = 0; i < size; i++){
			bomb[i] = new Bomb("myBomb" + i, "sphere.obj", eng, sm,(Vector3f) randLoc(50).mult(0.5f));
			bomb[i].create();
			storeBomb(bomb[i].getBomb());
			System.out.println(prize[i].location());
			System.out.println(randLoc(35));
		}
		
		for(int i = 0; i < size; i++){
			if(nearBomb(prize[i].getPrize(), bomb[i].getBomb())){
				//prize[i].getPrize().getLocalPosition().add(1.0f, 1.0f, 1.0f);
			}
		}
		
	}
	
	public Vector3f randLoc(int size){
		float x = (float) (Math.random() * size);
		float y = (float) (Math.random() * size);
		float z = (float) (Math.random() * size);
		Vector3f prizeLoc = (Vector3f) Vector3f.createFrom(x, 0, z);
		
		return prizeLoc;
	}
	
	public SceneNode getDolphin(){
		return dolphin.getDolphin();
	}
	
	public void storePrize(SceneNode n){
		node = n;
	}
	
	public SceneNode getPrize(){
		return node;
	}
	
	public Prize[] getPrizes(){
		return prize;
	}
	
	public int numberOfChildrenObject(){
		return dolphin.numberOfChildrenObject();
	}
	
	public SceneNode getBox(){
		return man.getBox();
	}
	
	public boolean nearBomb(SceneNode nodePrize, SceneNode Bomb){
		Vector3 pr = nodePrize.getLocalPosition();
		Vector3 b = nodePrize.getLocalPosition();
		boolean t = false;
		
		if(pr.sub(b).length() < 1.0){
			t = true;
		}
		
		return t;
	}
	
	public void storeBomb(SceneNode b){
		bnode = b;
	}
	
	public SceneNode getBomb(){
		return bnode;
	}
	
	public Bomb[] getBombs(){
		return bomb;
	}
}
