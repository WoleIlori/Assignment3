package ie.dit;
import processing.core.*;

public class Coin extends GameObject implements Collectibles 
{
	
	Coin(PApplet papplet, float x, float y, float w)
	{
		super(papplet);
		pos = new PVector(x, y);
		this.w = w;
		this.radius = w *0.5f;
		c = papplet.color(242, 193, 12);
	}
		
	void render()
	{
		papplet.fill(c);
		papplet.ellipse(pos.x, pos.y, w, w);
	}
	
	void update()
	{
		
	}

}
