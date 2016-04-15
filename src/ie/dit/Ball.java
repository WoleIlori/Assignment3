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
		theta = 0;
		score = 50;
		
	}
	
	void render()
	{
		papplet.ellipse(pos.x, pos.y, w, w);
	}
	
	public void keyPressed()
	{
		if(papplet.key =='w')
		{
			Main.g = 2;
		}
		
		if(papplet.key =='a')
		{
			theta = PApplet.PI/2;
			forward.x = PApplet.sin(-theta);
			forward.y = 0;
			forward.mult(15);
			pos.add(forward);
		}
		
		if(papplet.key =='d')
		{
			theta = PApplet.PI/2;
			forward.x = PApplet.sin(theta);
			forward.y = 0;
			forward.mult(15);
			pos.add(forward);
		}
	}
	
	void update()
	{
		if(pos.x < radius)
		{
			pos.x = radius;
		}
		
		if(pos.x > papplet.width)
		{
			if(Main.coinCheck > 0)
			{
				pos.x = papplet.width - radius;
			}
			Main.level = 0;
		}
		
	}
	
}
