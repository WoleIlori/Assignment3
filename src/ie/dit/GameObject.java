package ie.dit;
import processing.core.*;

public abstract class GameObject
{
	PApplet papplet;
	PVector pos;
	
	GameObject(PApplet papplet)
	{
		this.papplet = papplet;
	}
	
	abstract void render();
	//abstract void update();

}
