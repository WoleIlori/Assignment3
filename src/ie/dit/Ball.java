package ie.dit;
import processing.core.*;

public class Ball extends GameObject
{
	float bRadius;
	float ballSize;
	
	Ball(PApplet papplet, float y)
	{
		super(papplet);
		ballSize = 22.0f;
		bRadius = ballSize * 0.5f;
		pos = new PVector(bRadius, (y -  bRadius));
		
	}
	
	void render()
	{
		papplet.ellipse(pos.x, pos.y, ballSize, ballSize);
	}
}
