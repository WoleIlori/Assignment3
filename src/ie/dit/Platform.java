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
		c = papplet.color(139, 58, 7);
		lives = 100;
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
				if(lives < 100)
				{
					c = papplet.color(255, 124, 0);
				}
				if(lives < 50 )
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
