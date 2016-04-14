package ie.dit;
import processing.core.*;

public abstract class GameObject
{
	PApplet papplet;
	PVector pos;
	float w;
	float h;
	float radius; //halfW
	int c;
	boolean platDecay;
	
	GameObject(PApplet papplet)
	{
		this.papplet = papplet;
	}
	
	abstract void render();
	abstract void update();

}
