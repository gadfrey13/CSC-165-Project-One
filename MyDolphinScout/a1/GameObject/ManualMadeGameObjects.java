package a1.GameObject;

import java.awt.Color;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import ray.rage.Engine;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.Renderable.DataSource;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.shader.GpuShaderProgram;
import ray.rage.rendersystem.states.FrontFaceState;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.ManualObject;
import ray.rage.scene.ManualObjectSection;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.util.BufferUtil;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class ManualMadeGameObjects{
		private SceneNode boxMade;
	
	   public ManualObject makeBox(Engine eng, SceneManager sm) throws IOException{
	    	ManualObject box = sm.createManualObject("Holding Box");
	    	ManualObjectSection boxSec = box.createManualSection("Box Section");
	    	box.setGpuShaderProgram(sm.getRenderSystem().getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
	    	
	    	/*
	    	//The vertices of the object
	    	float[] vertices = new float[]{
	    		-1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, //front
	    		 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, //right
	    		 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, //back
	    		-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, //left
	    		-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, //LF
	    		 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f //RR
	    	};
	    	*/
	    	//Box Counter Clockwise Coordinate
	    	float[] vertices = new float[]{
	    		/*
	    		-1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, //first half front
	    		 1.0f,  1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f,//second half front
	    		 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, //first half right
	    		 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, //second half right
	    		 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,//first half back
	    		 -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,//Second half back
	    		 -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,//first half left
	    		 -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f,//second half left
	    		 -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f,//first half bottom
	    		 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f //Second half bottom
	    		 */
	    		-1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, //first half front
		    	 1.0f,  1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f,//second half front
		    	 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, //first half right
	    		 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, //second half right
	    		 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,//first half back
	    		 -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,//Second half back
	    		 -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f,//first half left
	    		 -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f,//second half left
		    	-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, //LF
		         1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f //RR
	    	};
	    	
	    	/*
	    	//The text coordinate of the object
	    	float[] texcoords = new float[]{ 
	    		 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
	    		 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
	    		 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
	    		 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
	    		 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
	    	};
	    	*/
	    	
	    	//Box Texture Counter Clockwise Coordinate
	    	float[] texcoords = new float[]{
	    
	    		 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
		         1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		         0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
	    		 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
	    		 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
	    		 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
	    		 
	    	};
	    	//The normalize coordinate of the object
	    	float[] normals = new float[]{ 
	    		 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
	    		 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
	    		 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f,
	    		-1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f,
	    		 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
	    		 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f
	    	};
	    	//The indices of the object
	    	//int[] indices = new int[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17 };
	    	int[] indices = new int[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29 };
	    	
	    	FloatBuffer vertBuf = BufferUtil.directFloatBuffer(vertices);
	    	FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
	    	FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
	    	IntBuffer   indexBuf = BufferUtil.directIntBuffer(indices);
	    	
	    	boxSec.setVertexBuffer(vertBuf);
	    	boxSec.setTextureCoordsBuffer(texBuf);
	    	boxSec.setNormalsBuffer(normBuf);
	    	boxSec.setIndexBuffer(indexBuf);
	    	
	    	Texture tex = eng.getTextureManager().getAssetByPath("hexagons.jpeg");
	    	TextureState textState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
	    	textState.setTexture(tex);
	    	
	    	FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().createRenderState(RenderState.Type.FRONT_FACE);
	    	faceState.setVertexWinding(FrontFaceState.VertexWinding.COUNTER_CLOCKWISE);
	    	box.setDataSource(DataSource.INDEX_BUFFER);
	    	box.setRenderState(textState);
	    	box.setRenderState(faceState);
	    	
	    	return box;
	    }
	    
	    protected ManualObject makeZLine(Engine eng, SceneManager sm) throws IOException{
	    	ManualObject zLine = sm.createManualObject("Z Line");
	    	ManualObjectSection zLineSec = zLine.createManualSection("Z Line");
	    	zLine.setGpuShaderProgram(sm.getRenderSystem().getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
	    	
	        //Z axis Line
		    float[] vertices = {
		    	0.0f, 0.0f, 0.0f, 
		    	0.0f, 0.0f, 10000.0f //X-axis
		    };
		    
		    //Z axis texture
		    float[] texcoords = {
		    		0.0f,0.0f, 0.0f, 0.0f,
		    		0.0f,10000.0f//Texture red line
		    };
		    
		  //The normalize coordinate of the object
	    	float[] normals = new float[]{ 
	    		 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
	    	};
	    	
	    	int[] indices = new int[] { 0,1};
		    
		    FloatBuffer vertBuf = BufferUtil.directFloatBuffer(vertices);
	    	FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
	    	FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
	    	IntBuffer   indexBuf = BufferUtil.directIntBuffer(indices);
	    	
	    	zLineSec.setVertexBuffer(vertBuf);
	    	zLineSec.setTextureCoordsBuffer(texBuf);
	    	zLineSec.setNormalsBuffer(normBuf);
	    	zLineSec.setIndexBuffer(indexBuf);
	    	
	    	Material mat = sm.getMaterialManager().getAssetByPath("default.mtl");
	    	mat.setEmissive(Color.RED);
	    	//mat.setSpecular(Color.RED);
	    	
	    	//Texture tex = eng.getTextureManager().getAssetByPath(mat.getTextureFilename());
	    	Texture tex = eng.getTextureManager().getAssetByPath("Z_Axis.png");
	    	TextureState textState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
	    	textState.setTexture(tex);
	    	FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().createRenderState(RenderState.Type.FRONT_FACE);
	    	//faceState.setVertexWinding(FrontFaceState.VertexWinding.CLOCKWISE);
	    	
	    	zLine.setRenderState(textState);
	    	//zLine.setMaterial(mat);
	    	zLine.setRenderState(faceState);
	    	
	    	return zLine;
	    }
	    
	    
	    
	    protected ManualObject makeXLine(Engine eng, SceneManager sm) throws IOException{
	    	ManualObject xLine = sm.createManualObject("X Line");
	    	ManualObjectSection xLineSec = xLine.createManualSection("X Line");
	    	xLine.setGpuShaderProgram(sm.getRenderSystem().getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
	    	
	        //X axis Line
		    float[] vertices = {
		    	0.0f, 0.0f, 0.0f, 
		    	10000.0f, 0.0f, 0.0f  //X-axis
		    };
		    
		    //X axis texture
		    float[] texcoords = {
		    		0.0f,0.0f, 0.0f, 0.0f,
		    		10000.0f, 0.0f //Texture red line
		    };
		    
		  //The normalize coordinate of the object
	    	float[] normals = new float[]{ 
	    		 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
	    	};
	    	
	    	int[] indices = new int[] { 0,1};
		    
		    FloatBuffer vertBuf = BufferUtil.directFloatBuffer(vertices);
	    	FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
	    	FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
	    	IntBuffer   indexBuf = BufferUtil.directIntBuffer(indices);
	    	
	    	xLineSec.setVertexBuffer(vertBuf);
	    	xLineSec.setTextureCoordsBuffer(texBuf);
	    	xLineSec.setNormalsBuffer(normBuf);
	    	xLineSec.setIndexBuffer(indexBuf);
	    	
	    	Material mat = sm.getMaterialManager().getAssetByPath("default.mtl");
	    	mat.setEmissive(Color.BLUE);
	    	//mat.setSpecular(Color.BLUE);
	    	
//	    	Texture tex = eng.getTextureManager().getAssetByPath(mat.getTextureFilename());
	    	Texture tex = eng.getTextureManager().getAssetByPath("X_Axis.png");
	    	TextureState textState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
	    	textState.setTexture(tex);
	    	FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().createRenderState(RenderState.Type.FRONT_FACE);
	    	//faceState.setVertexWinding(FrontFaceState.VertexWinding.CLOCKWISE);
	    	
	    	xLine.setRenderState(textState);
	    	//xLine.setMaterial(mat);
	    	xLine.setRenderState(faceState);
	    	
	    	return xLine;
	    }
	    
	    protected ManualObject makeYLine(Engine eng, SceneManager sm) throws IOException{
	    	ManualObject yLine = sm.createManualObject("Y Line");
	    	ManualObjectSection yLineSec = yLine.createManualSection("Y Line");
	    	yLine.setGpuShaderProgram(sm.getRenderSystem().getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
	    	
	        //Y axis Line
		    float[] vertices = {
		    	0.0f, 0.0f, 0.0f, 
		    	0.0f, 10000.0f, 0.0f  //y-axis
		    };
		    
		    //Y axis texture
		    float[] texcoords = {
		    		0.0f,0.0f, 0.0f, 0.0f,
		    		10000.0f, 0.0f //Texture red line
		    };
		    
		  //The normalize coordinate of the object
	    	float[] normals = new float[]{ 
	    		 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
	    	};
	    	
	    	int[] indices = new int[] { 0,1};
		    
		    FloatBuffer vertBuf = BufferUtil.directFloatBuffer(vertices);
	    	FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
	    	FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
	    	IntBuffer   indexBuf = BufferUtil.directIntBuffer(indices);
	    	
	    	yLineSec.setVertexBuffer(vertBuf);
	    	yLineSec.setTextureCoordsBuffer(texBuf);
	    	yLineSec.setNormalsBuffer(normBuf);
	    	yLineSec.setIndexBuffer(indexBuf);
	    	
	    	Material mat = sm.getMaterialManager().getAssetByPath("default.mtl");
	    	mat.setEmissive(Color.BLUE);
	    	//mat.setSpecular(Color.BLUE);
	    	
//	    	Texture tex = eng.getTextureManager().getAssetByPath(mat.getTextureFilename());
	    	Texture tex = eng.getTextureManager().getAssetByPath("Y_Axis.png");
	    	TextureState textState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
	    	textState.setTexture(tex);
	    	FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().createRenderState(RenderState.Type.FRONT_FACE);
	    	//faceState.setVertexWinding(FrontFaceState.VertexWinding.CLOCKWISE);
	    	
	    	yLine.setRenderState(textState);
	    	//xLine.setMaterial(mat);
	    	yLine.setRenderState(faceState);
	    	
	    	return yLine;
	    }
	    
	    
	    
	    
	    //Create the world Axis
	    public void createAxes(Engine eng, SceneManager sm) throws IOException{
	    
	    	 ManualObject zLine = makeZLine(eng,sm);  
	         zLine.setPrimitive(Primitive.LINES);
		     SceneNode zLineN = sm.getRootSceneNode().createChildSceneNode("ZLine");
		     zLineN.attachObject(zLine);
		     zLineN.scale(0.2f, 0.2f, 0.2f);
	         zLineN.setLocalPosition(0.0f, 0.0f, 0.0f);
	         
	         
	         ManualObject xLine = makeXLine(eng,sm);  
	         xLine.setPrimitive(Primitive.LINES);
		     SceneNode xLineN = sm.getRootSceneNode().createChildSceneNode("XLine");
		     xLineN.attachObject(xLine);
		     xLineN.scale(0.02f, 0.02f, 0.02f);
	         xLineN.setLocalPosition(0.0f, 0.0f, 0.0f);
	         
	         ManualObject yLine = makeYLine(eng,sm);  
	         yLine.setPrimitive(Primitive.LINES);
		     SceneNode yLineN = sm.getRootSceneNode().createChildSceneNode("YLine");
		     yLineN.attachObject(yLine);
		     yLineN.scale(0.02f, 0.02f, 0.02f);
	         yLineN.setLocalPosition(0.0f, 0.0f, 0.0f);
		     
		   
	 }
	    
	    
	 public void createBox(Engine eng, SceneManager sm) throws IOException{
	        //Manual Object Box
	        Vector3f rotXAxis = (Vector3f) Vector3f.createFrom(1.0f, 0.0f, 0.0f);
	        Angle angle = Degreef.createFrom(90);
	        ManualObject box = makeBox(eng,sm);
	        FrontFaceState csfaceState = (FrontFaceState) sm.getRenderSystem().createRenderState(RenderState.Type.FRONT_FACE);
	        csfaceState.setVertexWinding(FrontFaceState.VertexWinding.COUNTER_CLOCKWISE);
	        box.setRenderState(csfaceState); 
	        box.setPrimitive(Primitive.TRIANGLES);
	        SceneNode boxN = sm.getRootSceneNode().createChildSceneNode("BoxNode");
	        boxN.attachObject(box);
	        //boxN.rotate(angle,rotXAxis );
	        boxN.scale(0.5f,0.5f,0.5f);
	        boxN.setLocalPosition(2.0f, 0.5f, -1.0f);
	        boxMade = boxN;
	 }

	public SceneNode getBox(){
		return boxMade;
	}
}
