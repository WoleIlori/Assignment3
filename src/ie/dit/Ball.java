package ie.dit;
import processing.core.*;

public class Ball extends GameObject
{
	PVector forward;
	float bRadius;
	float theta;
	
	Ball(PApplet papplet, float y)
	{
		super(papplet);
		this.w = 22.0f;
		bRadius = this.w * 0.5f;
		pos = new PVector(bRadius, (y -  bRadius));
		forward = new PVector(0, 1);
		theta = 0;
		
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
}
