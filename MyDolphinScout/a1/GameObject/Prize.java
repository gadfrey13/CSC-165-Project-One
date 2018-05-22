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

public class Prize {
	private String myName, myPath;
	private Engine myEng;
	private SceneManager mySm;
	private SceneNode prizeN;
	private Vector3f loc;

	
	public Prize(String name, String path, Engine eng, SceneManager sm, Vector3f l){
		myName = name;
		myPath = path;
		myEng = eng;
		mySm = sm;
		loc = l;
	}
	
	public SceneNode create() throws IOException{
		Entity prizeE = mySm.createEntity(myName, myPath);
		Texture tex = mySm.getTextureManager().getAssetByPath("blue.jpeg");
		TextureState texState = (TextureState) mySm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		prizeE.setRenderState(texState);
		prizeE.setPrimitive(Primitive.TRIANGLES);
		
		prizeN = mySm.getRootSceneNode().createChildSceneNode(prizeE.getName() +"Node");
		prizeN.attachObject(prizeE);
		prizeN.setLocalPosition(loc);
		prizeN.scale(0.25f,0.25f,0.25f);
		
		return prizeN;
	}
	
	public SceneNode getPrize(){
		return prizeN;
	}
	
	public Vector3 location(){
		return prizeN.getLocalPosition();
	}
}
