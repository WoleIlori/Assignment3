package ie.dit;
import processing.core.*;

public class Ball extends GameObject
{
	PVector forward;
	float radius;
	float theta;
	int score;
	char jump;
	char left;
	char right;
	
	Ball(PApplet papplet, float y, char jump, char right, char left)
	{
		super(papplet);
		this.w = 22.0f;
		this.radius = this.w * 0.5f;
		pos = new PVector(this.radius, (y -  this.radius));
		forward = new PVector(0, 1);
		c = 255;
		theta = 0;
		score = 0;
		this.jump = jump;
		this.right = right;
		this.left = left;
		
	}
	
	void render()
	{
	
		papplet.fill(c);
		papplet.ellipse(pos.x , pos.y, w, w);
	}
	
	
	void update()
	{
		//check collision with left edge of screen
		if(pos.x < radius)
		{
			pos.x = radius;
		}
		
		//if "jump" key is pressed allow ball to jump
		if(Main.keys[jump])
		{
			Main.g = 2;
		}
		
		//move left
		if(Main.keys[left])
		{
			theta = PApplet.PI/2;
			forward.x = PApplet.sin(theta);
			forward.y = 0;
			forward.mult(4);
			pos.add(forward);
		}
		
		//move right
		if(Main.keys[right])
		{
			theta = PApplet.PI/2;
			forward.x = PApplet.sin(-theta);
			forward.y = 0;
			forward.mult(4);
			pos.add(forward);
		}
	}
	
}
