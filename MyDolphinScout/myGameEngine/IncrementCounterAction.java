package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class IncrementCounterAction extends AbstractInputAction {

	private MyGameEngine game;
	
	public IncrementCounterAction(MyGameEngine g){
		game = g;
	}
	
	@Override
	public void performAction(float time, Event e) {
		System.out.println("counter action iniated");
		//((MyGame) game).incrementCounter();
	}

}
