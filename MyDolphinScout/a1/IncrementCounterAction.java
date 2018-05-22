package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class IncrementCounterAction extends AbstractInputAction {

	private MyGame game;
	
	public IncrementCounterAction(MyGame g){
		game = g;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("counter action iniated");
		//game.incrementCounter();
	}

}
