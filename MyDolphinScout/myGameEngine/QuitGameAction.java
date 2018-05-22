package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.game.Game;

public class QuitGameAction extends AbstractInputAction {
	private MyGameEngine game;
	
	public QuitGameAction(MyGameEngine g){
		game = g;
	}
	
	@Override
	public void performAction(float time, Event e) {
		System.out.println("shutdown requested");
		game.setState(Game.State.STOPPING);
	}
	
}
