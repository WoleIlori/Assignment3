package ie.dit;
import processing.core.*;

public class Platform extends GameObject
{
	int lives;
	
	Platform(PApplet papplet, float x, float y, float w, float h)
	{
		super(papplet);
		pos = new PVector(x, y);
		this.w = w;
		this.h = h;
		c = 255;
		lives = 40;
		platDecay = false;
	}
	
	void render()
	{
		papplet.fill(c);
		papplet.rect(pos.x, pos.y, w, h);
	}
	
	void update()
	{
		if(platDecay)
		{
			if(lives > 0)
			{
				lives --;
			}
			if(lives < 20)
			{
				c = papplet.color(255, 124, 0);
			}
			if(lives < 10)
			{
				c = papplet.color(255, 0, 0);
			}
			if(lives == 0)
			{
				pos.y += 9.8;
			}
		}
	}
}
