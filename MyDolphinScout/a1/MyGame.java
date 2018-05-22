package a1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import myGameEngine.IncrementCounterAction;
import myGameEngine.MyGameEngine;
import myGameEngine.QuitGameAction;
import myGameEngine.CameraActionInput.MoveBackwardAction;
import myGameEngine.CameraActionInput.MoveForwardAction;
import myGameEngine.CameraActionInput.MoveLeftAction;
import myGameEngine.CameraActionInput.MoveRightAction;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.*;
import ray.rage.game.*;
import ray.rage.rendersystem.*;
import ray.rage.rendersystem.Renderable.*;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.*;
import ray.rage.scene.controllers.*;
import ray.rml.*;
import ray.rage.rendersystem.gl4.GL4RenderSystem;

public class MyGame extends MyGameEngine {
    public MyGame() {
        super();
		System.out.println("press W to go forward");
		System.out.println("press S to go backward ");
		System.out.println("press A to go left");
		System.out.println("press D to go Right");
    }

    public static void main(String[] args) {
        Game game = new MyGame();
        try {
            game.startup();
            game.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
        	game.setState(Game.State.STOPPING);
            game.exit();
        }
    }
}