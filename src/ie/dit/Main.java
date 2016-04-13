package ie.dit;
import processing.core.*;

public class Main extends PApplet 
{
	public void setup()
	{
		size(700, 500);
		background(255);
		ball = new Ball(this, height);
		g = 1;
		launch = 15;
	}
	
	//create instance of ball
	Ball ball;
	static int g;
	int launch;
	float gravity;
	
	public void draw()
	{
		background(255);
		stroke(0);
		
		fill(255);
		ball.render();
		
		if(g < 2)
		{
			gForce();
		}
		else
		{
			jump(launch);
			landCheck();
		}
	}
	
	public void keyPressed()
	{
		ball.keyPressed();
	}
	
	public void gForce()
	{
		g = 1;
		
		if(g == 1)
		{
			ball.pos.y += launch;
		}
		
		landCheck();
		
		if(g == 0)
		{
			gravity = 0;
		}
	}
	
	public void jump(int launch)
	{
		int tVel = launch * 2;
		ball.pos.y -= launch - gravity;
		
		if(g < tVel)
		{
			gravity ++;
		}
	}
	
	public void landCheck()
	{
		if(ball.pos.y > height)
		{
			ball.pos.y = height - ball.bRadius;
			g = 0;
		}
	}
	
	public static void main(String[] args)
	{
		String[] a = {"MAIN"};
	    PApplet.runSketch( a, new Main());
	}
}
