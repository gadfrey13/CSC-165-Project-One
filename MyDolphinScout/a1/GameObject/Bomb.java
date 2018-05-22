package a1.GameObject;

import java.io.IOException;

import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class Bomb {
	private String myName, myPath;
	private Engine myEng;
	private SceneManager mySm;
	private SceneNode bombN;
	private Vector3f loc;

	
	public Bomb(String name, String path, Engine eng, SceneManager sm, Vector3f l){
		myName = name;
		myPath = path;
		myEng = eng;
		mySm = sm;
		loc = l;
	}
	
	public SceneNode create() throws IOException{
		Entity bombE = mySm.createEntity(myName, myPath);
		Texture tex = mySm.getTextureManager().getAssetByPath("red.jpeg");
		TextureState texState = (TextureState) mySm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		bombE.setRenderState(texState);
		bombE.setPrimitive(Primitive.TRIANGLES);
		
		bombN = mySm.getRootSceneNode().createChildSceneNode(bombE.getName() +"Node");
		bombN.attachObject(bombE);
		bombN.setLocalPosition(loc);
		bombN.scale(0.25f,0.25f,0.25f);
		
		return bombN;
	}
	
	public SceneNode getBomb(){
		return bombN;
	}
	
	public Vector3 location(){
		return bombN.getLocalPosition();
	}
}
