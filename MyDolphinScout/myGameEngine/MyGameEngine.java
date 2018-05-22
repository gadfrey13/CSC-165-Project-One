package myGameEngine;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.w3c.dom.events.EventException;

import a1.MyGame;
import a1.GameObject.Dolphin;
import a1.GameObject.GameWorld;
import a1.GameObject.ManualMadeGameObjects;
import a1.GameObject.Prize;
import myGameEngine.CameraActionInput.MoveBackwardAction;
import myGameEngine.CameraActionInput.MoveForwardAction;
import myGameEngine.CameraActionInput.MoveForwardBackwardAction;
import myGameEngine.CameraActionInput.MoveLeftAction;
import myGameEngine.CameraActionInput.MoveRightAction;
import myGameEngine.CameraActionInput.MoveRightLeftAction;
import myGameEngine.CameraActionInput.PitchAction;
import myGameEngine.CameraActionInput.PitchDownAction;
import myGameEngine.CameraActionInput.PitchUpAction;
import myGameEngine.CameraActionInput.YawAction;
import myGameEngine.CameraActionInput.YawLeftAction;
import myGameEngine.CameraActionInput.YawRightAction;
import myGameEngine.DolphinActionInput.MoveBackwardNodeAction;
import myGameEngine.DolphinActionInput.MoveDownPitchNodeAction;
import myGameEngine.DolphinActionInput.MoveForwardBackwardNodeAction;
import myGameEngine.DolphinActionInput.MoveForwardNodeAction;
import myGameEngine.DolphinActionInput.MoveLeftNodeAction;
import myGameEngine.DolphinActionInput.MoveLeftYawNodeAction;
import myGameEngine.DolphinActionInput.MovePitchNodeAction;
import myGameEngine.DolphinActionInput.MoveRightLeftNodeAction;
import myGameEngine.DolphinActionInput.MoveRightNodeAction;
import myGameEngine.DolphinActionInput.MoveRightYawNodeAction;
import myGameEngine.DolphinActionInput.MoveUpPitchNodeAction;
import myGameEngine.DolphinActionInput.MoveYawNodeAction;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.*;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.game.*;
import ray.rage.rendersystem.*;
import ray.rage.rendersystem.Renderable.*;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.*;
import ray.rage.scene.controllers.*;
import ray.rage.util.BufferUtil;
import ray.rml.*;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.rendersystem.shader.GpuShaderProgram;
import ray.rage.rendersystem.states.FrontFaceState;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;


public class MyGameEngine extends VariableFrameRateGame {
	
		private Game game;
	//Private Data Fields
		private InputManager im,am;
		private Action quitGameAction, incrementCounterAction, moveForwardAction, moveBackwardAction,moveLeftAction,moveRightAction, moveYawLeftAction,
		moveYawRightAction,movePitchDownAction,movePitchUpAction,movePitchAction,moveYawAction;
		private Action toggleAction;
		private Action moveNodeForwardAction, moveNodeBackwardAction, moveNodeRightAction,moveNodeLeftAction, moveNodeForwardBackwardAction, moveNodeRightLeftAction,
		moveNodePitchUpAction, moveNodePitchDownAction, moveNodeYawLeftAction, moveNodeYawRightAction, moveNodePitchAction, moveNodeYawAction;
		private Action moveForwardBackwardAction, moveRightLeftAction;
		private Action gameOverAction;
		
		private Camera camera;
		
		private GameWorld gw;
		private EventHandler ev;
		private SceneNode cameraNode;
		// to minimize variable allocation in update()
		
		GL4RenderSystem rs;
		float elapsTime = 0.0f;
		String elapsTimeStr, counterStr, dispStr;
		int elapsTimeSec, counter = 0;
		
		@Override
		protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge) {
			rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
		}

	    @Override
	    protected void setupCameras(SceneManager sm, RenderWindow rw) {
	        SceneNode rootNode = sm.getRootSceneNode();
	        camera = sm.createCamera("MainCamera", Projection.PERSPECTIVE);
	        rw.getViewport(0).setCamera(camera);
			
			camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
			camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
			camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
			
			camera.setPo((Vector3f)Vector3f.createFrom(1.0f, 1.0f, 20.0f));

	        cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node");
	        cameraNode.attachObject(camera);
	    }
		
	    @Override
	    protected void setupScene(Engine eng, SceneManager sm) throws IOException {
	    	gw = new GameWorld(eng,sm,camera);
	    	
	            
	         gw.init();
	 		
	         ev = new EventHandler(camera,gw.getDolphin(),gw.getBox(), gw.getBomb(),gw.getPrizes(),gw.getBombs());
	
	        Entity earthE = sm.createEntity("myEarth", "earth.obj");
	        earthE.setPrimitive(Primitive.TRIANGLES);
	        SceneNode earthN = sm.getRootSceneNode().createChildSceneNode(earthE.getName() + "Node");
	        earthN.attachObject(earthE);
	        earthN.setLocalPosition(-2.0f, 6.0f, -1.0f);
	        earthN.setLocalScale(1.2f,1.2f,1.2f);
			
		    RotationController rc = new RotationController(Vector3f.createUnitVectorY(),.03f);
			rc.addNode(earthN);
			sm.addController(rc);

	       

	        sm.getAmbientLight().setIntensity(new Color(.2f, .2f, .2f));
			
			Light plight = sm.createLight("testLamp1", Light.Type.POINT);
			plight.setAmbient(new Color(.9f, .9f, .9f));
	        plight.setDiffuse(new Color(.5f, .5f, .5f));
			plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
	        plight.setRange(100f);
			
			
			SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
	        plightNode.attachObject(plight);
			plightNode.setLocalPosition(8.0f,5.0f,12.0f);
			/*
			Entity pointE = sm.createEntity("myPoint", "sphere.obj");
	        earthE.setPrimitive(Primitive.TRIANGLES);
	        SceneNode pointN = sm.getRootSceneNode().createChildSceneNode(pointE.getName() + "Node");
	        pointN.attachObject(pointE);
	        pointN.setLocalPosition(plightNode.getLocalPosition());
	        pointN.setLocalScale(0.1f,0.1f,0.1f);
			*/
	        toggleAction = new Toggle(camera,cameraNode,gw.getDolphin());
	        setupInput(gw.getDolphin());
	    }

	    @Override
	    protected void update(Engine engine) {
			// build and set HUD
			rs = (GL4RenderSystem) engine.getRenderSystem();
			elapsTime += engine.getElapsedTimeMillis();
			elapsTimeSec = Math.round(elapsTime/1000.0f);
			elapsTimeStr = Integer.toString(elapsTimeSec);
			counterStr = Integer.toString(counter);
			dispStr = "Time = " + elapsTimeStr + "  Player On Dolphin = " + onOff() + " Score " + ev.score() + " DolphinLives " + ev.getLives() ;
			rs.setHUD(dispStr, 15, 15);
			if(onOff().equalsIgnoreCase("off")){
				im.update(elapsTime);
			}else{
				am.update(elapsTime);
			}
		
			boolean bol = ev.allowToAttach(ev.detectPrizes(), onOff());
			ev.attachPrizeDolphin(bol, ev.canAtttach());
			ev.attachPrizeToBox(ev.detect());
			ev.losingLives();
			
			System.out.println(rs.getCanvas().getHeight() );
			if(ev.getLives() == 0){
				String gameOver = "Game Over";
				rs.setHUD(gameOver, rs.getCanvas().getWidth()/2, rs.getCanvas().getHeight()/2);
				this.setState(Game.State.STOPPING);
			}
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	        Entity dolphin = getEngine().getSceneManager().getEntity("myDolphin");
	        switch (e.getKeyCode()) {
	            case KeyEvent.VK_L:
	                dolphin.setPrimitive(Primitive.LINES);
	                break;
	            case KeyEvent.VK_T:
	                dolphin.setPrimitive(Primitive.TRIANGLES);
	                break;
	            case KeyEvent.VK_P:
	                dolphin.setPrimitive(Primitive.POINTS);
	                break;
	            
			
	        }
	        super.keyPressed(e);
	    }
	    /**
	     * This method set up the inputs for the keyboard
	     * @return 
	     * @param
	     */
	    public void setupInputsCamera(SceneNode node){
	    	im = new GenericInputManager();
	    	String kbName = im.getKeyboardName();
	    	String gpName = im.getFirstGamepadName();
	    	
	    	
	    	//build action objects corresponding to the Game
	    	quitGameAction = new QuitGameAction(this);
	    	//incrementCounterAction = new IncrementCounterAction(this);
	    	
	    	//build action objects corresponding to the Camera
	    	moveForwardAction = new MoveForwardAction(camera);
	    	moveBackwardAction = new MoveBackwardAction(camera);
	    	moveLeftAction = new MoveLeftAction(camera);
	    	moveRightAction = new MoveRightAction(camera);
	    	moveYawLeftAction = new YawLeftAction(camera);
	    	moveYawRightAction = new YawRightAction(camera);
	    	movePitchDownAction = new PitchDownAction(camera);
	    	movePitchUpAction = new PitchUpAction(camera);
	    	
	    
	    	//Quit Game
	    	
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.ESCAPE, quitGameAction,InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	    	
	    	//Counter
	    	//im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.C, incrementCounterAction, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	    	
	    	//MoveForward
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.W,moveForwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    		
	    	//MoveBackward
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.S,moveBackwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveLeft
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.A,moveLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveRight
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.D,moveRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveYawLeft
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.LEFT,moveYawLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveYawRight
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.RIGHT,moveYawRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MovePitchDown
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.DOWN,movePitchDownAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MovePitchUp
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.UP,movePitchUpAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//Toggle
	    	im.associateAction(kbName,net.java.games.input.Component.Identifier.Key.SPACE,toggleAction, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	    	try{
	    		//attach the action objects to keyboard and gamepad components
		    	moveForwardBackwardAction = new MoveForwardBackwardAction(camera);
		    	moveRightLeftAction = new MoveRightLeftAction(camera);
		    	movePitchAction = new PitchAction(camera);
		    	moveYawAction = new YawAction(camera);
	    	
	    	//MoveForwardBackward Control
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Y,moveForwardBackwardAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	  
	    	//MoveRightLeft Control
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.X,moveRightLeftAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MovePitch
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.RZ,movePitchAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	//MoveYaw
	    	im.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Z,moveYawAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	    	}catch(Exception e){
      		System.out.println("Could not find controller");
	    	}
	    }
	    
	    public void setupInputNode(SceneNode node){
	    	am = new GenericInputManager();
	    	String kbName = am.getKeyboardName();
	    	String gpName = am.getFirstGamepadName();
	    	
	    	moveNodeForwardAction = new MoveForwardNodeAction(camera,node);
	    	moveNodeBackwardAction = new MoveBackwardNodeAction(camera,node);
	    	moveNodeRightAction = new MoveRightNodeAction(camera, node);
	    	moveNodeLeftAction = new MoveLeftNodeAction(camera, node);
	    	moveNodePitchUpAction = new MoveUpPitchNodeAction(camera,node);
	    	moveNodePitchDownAction = new MoveDownPitchNodeAction(camera,node);
	    	moveNodeYawLeftAction = new MoveLeftYawNodeAction(camera,node);
	    	moveNodeYawRightAction = new MoveRightYawNodeAction(camera,node);
	    	
	    	//MoveForward
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.W,moveNodeForwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveBackward
	      	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.S,moveNodeBackwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	      	//MoveRight
	      	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.D,moveNodeRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveLeft
	      	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.A,moveNodeLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	      	//MovePitchUp
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.UP,moveNodePitchUpAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MovePitchDown
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.DOWN,moveNodePitchDownAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveYawLeft
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.LEFT,moveNodeYawLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveYawRight
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.RIGHT,moveNodeYawRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MoveForwardBackward Control
	      	try{
	      	moveNodeForwardBackwardAction =new MoveForwardBackwardNodeAction(camera, node);
		    moveNodeRightLeftAction = new MoveRightLeftNodeAction(camera, node);
	    	moveNodePitchAction = new MovePitchNodeAction(camera,node);
	    	moveNodeYawAction = new MoveYawNodeAction(camera,node);
		   //MoveForwardAndBackward
	    	am.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Y,moveNodeForwardBackwardAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
	    	//MoveRightLeft Control
	    	am.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.X,moveNodeRightLeftAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	      	//MovePitch
	    	am.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.RZ,moveNodePitchAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	//MovePitch
	    	am.associateAction(gpName,net.java.games.input.Component.Identifier.Axis.Z,moveNodeYawAction,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	    	
	      	}catch(Exception e){
	      		System.out.println("Could not find controller");
	      	}
	    	//ToggleKey
	    	am.associateAction(kbName,net.java.games.input.Component.Identifier.Key.SPACE,toggleAction, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	      	
	    }
	    
	    public void setupInput(SceneNode node){	
	   		setupInputsCamera(node);      	
	   		setupInputNode(node);
	    }
	    
	    public String onOff(){
	    	String str = "";
	    	if(((Toggle) toggleAction).getOnOff()){
	    		str = "ON";
	    	}else{
	    		str = "OFF";
	    	}
	    	return str;
	    }
	    
	  
}
