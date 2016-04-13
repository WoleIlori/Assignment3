package ie.dit;
import processing.core.*;

public class Platform extends GameObject
{
	
	Platform(PApplet papplet, float x, float y, float w, float h)
	{
		super(papplet);
		pos = new PVector(x, y);
		this.w = w;
		this.h = h;
		c = 255;
	}
	
	void render()
	{
		papplet.fill(c);
		papplet.rect(pos.x, pos.y, w, h);
	}
}
