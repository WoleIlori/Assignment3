package ie.dit;
import processing.core.*;

public class Ball extends GameObject
{
	PVector forward;
	float radius;
	float theta;
	int score;
	
	Ball(PApplet papplet, float y)
	{
		super(papplet);
		this.w = 22.0f;
		this.radius = this.w * 0.5f;
		pos = new PVector(this.radius, (y -  this.radius));
		forward = new PVector(0, 1);
		c = 255;
		theta = 0;
		score = 0;
		
	}
	
	void render()
	{
		papplet.fill(c);
		papplet.ellipse(pos.x, pos.y, w, w);
	}
	
	public void keyPressed()
	{
		switch(papplet.key)
		{
			case 'w':
			case 'i':
			{
				Main.g = 2;
				break;
			}
			
			case 'a':
			case 'j':
			{
				theta = PApplet.PI/2;
				forward.x = PApplet.sin(-theta);
				forward.y = 0;
				forward.mult(15);
				pos.add(forward);
				break;
			}
			
			case 'd':
			case 'l':
			{
				theta = PApplet.PI/2;
				forward.x = PApplet.sin(theta);
				forward.y = 0;
				forward.mult(15);
				pos.add(forward);
				break;
			} 
		
		}
	}
	
	void update()
	{
		if(pos.x < radius)
		{
			pos.x = radius;
		}
		
	}
	
}
