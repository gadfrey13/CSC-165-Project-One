package myGameEngine;

import a1.GameObject.Bomb;
import a1.GameObject.Dolphin;
import a1.GameObject.Prize;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class EventHandler {
	private Camera camera;
	private SceneNode boxNode;
	private SceneNode dolphinNode;
	private Prize[] prize;
	private Bomb[] bomb;
	private SceneNode bombNode;
	int sc = 0;
	int count = 0;
	private SceneNode prizeNode;
	private SceneNode currentAttachNode;
	private String attachNodeName;
	private int lives = 3;
	public EventHandler(Camera cam, SceneNode dolphinN, SceneNode boxN,SceneNode bombN, Prize[] pr, Bomb[] bo){
		camera = cam;
		dolphinNode = dolphinN;
		boxNode = boxN;
		prize = pr;
		bombNode = bombN;
		bomb = bo;
	}
	/*
	public boolean detect(Camera camera, SceneNode node, SceneNode gwDolphin){
		Vector3f camLoc = cam.getPo();
		Vector3f nodeLoc = (Vector3f) nod.getLocalPosition();
		Vector3f dif = (Vector3f) camLoc.sub(nodeLoc);
		float d = dif.length();
		boolean t = false;
		System.out.println("distance" + d);
		if(d < 5f){
			t = true;
		}
		return t;
	}
	*/
	
	public boolean detect(){
		boolean b = false;
		Vector3f dolLoc = (Vector3f) dolphinNode.getLocalPosition();
		Vector3f nodeLoc = (Vector3f) boxNode.getLocalPosition();
		Vector3f dif = (Vector3f) dolLoc.sub(nodeLoc);
		float d = dif.length();
		System.out.println("distance" + d);
		if(d < 2f){
			b = true;
		}
		return b;
	}
	
	public void attachPrizeToBox(boolean boxBol){
		if(boxBol && count > 0){
			SceneNode curNode = (SceneNode) dolphinNode.getChild(attachNodeName);
			curNode.setLocalPosition(-2.0f, 2.0f, 2.0f);
			boxNode.attachChild(curNode);
			dolphinNode.detachChild(curNode);
			count--;
			sc++;
		}
	}
	
	public boolean detectPrizes(){
		boolean t = false;
		Vector3f camLoc = camera.getPo();
		Vector3f dif = null;
		float d = 0;
		for(int i = 0; i < prize.length; i++){
			Vector3f nodeLoc = (Vector3f) prize[i].getPrize().getLocalPosition();
			dif = (Vector3f) camLoc.sub(nodeLoc);
			d = dif.length();
			System.out.println("distance" + d);
			if(d < 3f){
				t = true;
				prizeNode = prize[i].getPrize();
			}
		}
		return t;
	}
	
	public void attachPrizeDolphin(boolean allowToAttach, boolean canAttach){
		System.out.println("Allow to Attach " + allowToAttach + "Can Attach " + canAttach);
		System.out.println("Attach Child " + currentAttachNode);
		System.out.println("Dolphin Location " + dolphinNode.getLocalPosition());
		if(allowToAttach && canAttach){
			if(canAttach && count == 0){
				Vector3f loc = (Vector3f) Vector3f.createFrom(9.0f, 0.0f, 10.0f);
				Vector3f dol = (Vector3f) dolphinNode.getLocalPosition();
				Vector3f attachNodeLoc = (Vector3f) dol.add(loc);
				attachNodeName = prizeNode.getName();
				dolphinNode.attachChild(prizeNode);
				currentAttachNode = (SceneNode) dolphinNode.getChild(attachNodeName);
				System.out.println("Dolphin Node " + dolphinNode.getLocalPosition());
				System.out.println("Attach Node "  + attachNodeLoc);
				currentAttachNode.setLocalPosition(dolphinNode.getLocalPosition());
				count++;
				
			}
		}
	}
	
	public boolean allowToAttach(boolean near, String onOff){
		boolean t = false;
		
		if(near && onOff.equalsIgnoreCase("Off")){
			t = true;
		}
		return t;
	}
	
	public boolean canAtttach(){
		boolean t = false;
		if(dolphinNode.getAttachedObjectCount() < 3){
			t = true;
		}
		return t;
	}
	
	public int score(){
		return sc;
	}
	
	public boolean dolphinNearBomb(){
		boolean t = false;
		Vector3f dolphinN = (Vector3f) dolphinNode.getLocalPosition();
		Vector3f dif = null;
		float d = 0;
		for(int i = 0; i < prize.length; i++){
			Vector3f nodeLoc = (Vector3f) bomb[i].getBomb().getLocalPosition();
			dif = (Vector3f) dolphinN.sub(nodeLoc);
			d = dif.length();
			//System.out.println("distance" + d);
			if(d < 2f){
				t = true;
				bombNode = bomb[i].getBomb();
			}
		}
		return t;
	}
	
	public boolean checkIfNearBomb(){
		boolean b = false;
		for(int i = 0; i < bomb.length; i++){
			if(dolphinNearBomb()){
				b = true;
			}
		}
		return false;
	}
	
	public void losingLives(){
		System.out.println("Check Near Bomb " + checkIfNearBomb());
		if(dolphinNearBomb()){
			bombNode.setLocalPosition(bombNode.getLocalPosition().add(100.0f,100.0f,100.0f));
			lives--;
		}
	}
	
	public int getLives(){
		return lives;
	}
	

}
