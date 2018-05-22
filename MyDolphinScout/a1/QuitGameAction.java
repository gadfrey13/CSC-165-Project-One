package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.game.Game;

public class QuitGameAction extends AbstractInputAction {
	private MyGame game;
	
	public QuitGameAction(MyGame g){
		game = g;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("shutdown requested");
		game.setState(Game.State.STOPPING);;
	}
	
}
