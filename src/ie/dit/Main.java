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
		endP = new Platform(this, 640.0f, 150.0f, 60, 80);
		gameObjects.add(endP);
		g = 1;
		launch = 13;
		speed = 4.0f;
		reset = false;
		level = 2;
		drawn = 1;
		drawPlatforms();
		drawCoins();
		
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
	boolean reset;
	static int level;
	int drawn;
	static int coinCheck = 0; //total no. of coins
	
	public void draw()
	{
		background(255);
		stroke(0);
		
		fill(255);
		
		if(reset)
		{
			drawPlatforms();
			reset = !reset;
		}
		
		for(int i = gameObjects.size() - 1; i >= 0; i--)
		{
			GameObject go = gameObjects.get(i);
		    go.render();
		    go.update();
		}
		
		if(g < 2)
		{
			//gForce initializes the state of the ball 
			//whether its on a platform or its falling or its in a jumping state
			gForce();
		}
		else
		{
			jump(launch);
			landCheck();
			
			//allows the ball to move while jumping
			if(key == 'd')
			{
				ball.pos.x += speed;
			}
			
			if(key == 'a')
			{
				ball.pos.x += -speed;
			}
		}
		checkCollisions();
		
		//checking if there are no coins left
		if(coinCheck == 0)
		{
			for(int i = gameObjects.size() - 1; i>= 0; i--)
			{
				GameObject go = gameObjects.get(i);
				if(go instanceof Platform && go == endP)
				{
					go.c = color(0,255, 0);
				}
			}
		}
		println(gameObjects.size(),coinCheck);
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
				if((ball.pos.y + ball.radius) > go.pos.y && (ball.pos.y + ball.radius) < (go.pos.y + go.h) && ball.pos.x > go.pos.x && ball.pos.x < (go.pos.x + go.w))
				{
					g = 0;
					ball.pos.y = go.pos.y - ball.radius;
					
					/*
					if(go != startP && go != endP)
					{
						go.platDecay= true;
					}
					*/
					
				}
		    }
		}
		
		if(ball.pos.y > height)
		{
			//remove platforms excluding start and end
			removePlatforms();
			ball.pos.x = ball.radius;
			ball.pos.y = startP.pos.y - ball.radius;
			reset = true;
		}
	}
	
	void drawPlatforms()
	{
		if(level == 1)
		{
			for(int i = 1; i < 4; i++)
			{
				float x = 90 + (i * 40);
				float y = 200.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
			
			for(int i = 1; i < 4; i++)
			{
				float x = 240 + (i * 40);
				float y = 150.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
			
			for(int i = 1; i < 4; i++)
			{
				float x = 400 + (i * 40);
				float y = 200.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
		}
		
		if(level == 2)
		{
			for(int i = 1; i < 5; i++)
			{
				float x = 90 + (i * 40);
				float y = 250.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
			
			for(int i = 1; i < 4; i++)
			{
				float x = 310 + (i * 40);
				float y = 200.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
			
			for(int i = 1; i < 5; i++)
			{
				float x = 150 + (i * 40);
				float y = 120.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
			
			for(int i = 1; i < 4; i++)
			{
				float x = 478 + (i * 40);
				float y = 150.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
		}
	}
	
	public static void main(String[] args)
	{
		String[] a = {"MAIN"};
	    PApplet.runSketch( a, new Main());
	}
	
	public void removePlatforms()
	{
		for(int i = gameObjects.size()- 1; i>=0; i--)
		{
			GameObject go = gameObjects.get(i);
			if(go instanceof Platform)
			{
				if(go != startP && go != endP)
				{
					gameObjects.remove(go);
				}
			}
		}
	}
	
	void checkCollisions()
	{
		for(int i = gameObjects.size() - 1; i >= 0; i--)
		{
			GameObject go = gameObjects.get(i);
			if(go instanceof Ball)
			{
				for(int j = gameObjects.size() - 1; j >= 0; j--)
				{
					GameObject other = gameObjects.get(j);
					if(other instanceof Collectibles)
					{
						//bounding circle collisions
						if(go.pos.dist(other.pos) < go.radius + other.radius)
						{
							((Collectibles)other).applyTo((Ball)go);
							gameObjects.remove(other);
							coinCheck --;
		
						}
					}
				}
			}
		}
	}
	
	public void drawCoins()
	{
		if(level == 1)
		{
			if(drawn == 1)
			{
				GameObject coin1 = new Coin(this, 180, 140, 17);
				gameObjects.add(coin1);
				GameObject coin2 = new Coin(this, 320, 100, 17);
				gameObjects.add(coin2);
				GameObject coin3 = new Coin(this, 520, 140, 17);
				gameObjects.add(coin3);
				drawn = 0;
			}
		}
		
		if(level == 2)
		{
			GameObject coin1 = new Coin(this, 170, 200, 17);
			gameObjects.add(coin1);
			GameObject coin2 = new Coin(this, 270, 70, 17);
			gameObjects.add(coin2);
		}
		
		//count no.of coins created
		for(int i = gameObjects.size() - 1; i>= 0; i--)
		{
			GameObject go = gameObjects.get(i);
			if(go instanceof Coin)
			{
				coinCheck ++;
			}
		}
	}
}
