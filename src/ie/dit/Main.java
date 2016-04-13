package ie.dit;
import processing.core.*;
import java.util.ArrayList;

public class Main extends PApplet 
{
	public void setup()
	{
		size(700, 500);
		background(255);
		ball = new Ball(this, 150.0f);
		gameObjects.add(ball);
		startP = new Platform(this, 0, 150.0f, 60, 80);
		gameObjects.add(startP);
		endP = new Platform(this, 638.0f, 150.0f, 60, 80);
		gameObjects.add(endP);
		g = 1;
		launch = 15;
		speed = 4.0f;
	}
	
	//create instance of ball
	Ball ball;
	//start and end platform
	Platform startP, endP;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	static int g;
	int launch;
	float gravity;
	float speed;
	
	public void draw()
	{
		background(255);
		stroke(0);
		
		fill(255);
		for(int i = gameObjects.size() - 1; i >= 0; i--)
		{
			GameObject go = gameObjects.get(i);
		    go.render();
		}
		
		if(g < 2)
		{
			gForce();
		}
		else
		{
			jump(launch);
			landCheck();
			if(key == 'd')
			{
				ball.pos.x += speed;
			}
			
			if(key == 'a')
			{
				ball.pos.x += -speed;
			}
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
		for(int i = gameObjects.size() - 1; i >= 0; i--)
		{
			GameObject go = gameObjects.get(i);
		    if (go instanceof Platform)
		    {
				if((ball.pos.y + ball.bRadius) > go.pos.y && (ball.pos.y + ball.bRadius) < (go.pos.y + go.h) && ball.pos.x > go.pos.x && ball.pos.x < (go.pos.x + go.w))
				{
					ball.pos.y = go.pos.y - ball.bRadius;
					g = 0;
				}	
		    }
		}
	}
	
	public static void main(String[] args)
	{
		String[] a = {"MAIN"};
	    PApplet.runSketch( a, new Main());
	}
}
