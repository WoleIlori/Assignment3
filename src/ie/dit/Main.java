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
		level = 1;
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
	boolean reset;//redraw platform and coins
	static int level;
	int drawn; //limit no. of coins drawn
	int coinCheck = 0; //total no. of coins
	int score = 0;
	int mode;
	//for level 2 other platforms touching each other will also decay
	//add powerup to slow down decay
	public void draw()
	{
		switch(mode)
		{
			case 0:
			{
				background(0);
				text("1. Play Game", 100, 50);
			    text("2. Instructions", 100, 100);
				break;
			}
			
			case 1:
			{
				background(255);
				fill(0);
				text("Score: "+ ball.score, 300, 30);
				fill(255);
				
				if(reset)
				{
					ball.score = 0;
					level = 1;
					drawPlatforms();
					drawn = 1;
					coinCheck = 0;
					drawCoins();
					
					//resetting the end Platform
					for(int i = gameObjects.size() - 1; i>= 0; i--)
					{
						GameObject go = gameObjects.get(i);
						if(go instanceof Platform && go == endP)
						{
							go.c = color(139, 58, 7);
						}
					}
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
					//gForce along with g initializes the state of the ball 
					//whether its on a platform or its falling or its in a jumping state
					gForce();
				}
				else
				{
					jump(launch);
					landCheck();
					
					//allows the ball to move while jumping
					if(key == 'd' || key == 'l')
					{
						ball.pos.x += speed;
					}
					
					if(key == 'a' || key == 'j')
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
				println((ball.pos.x +ball.radius),(endP.pos.x + endP.w));
				break;
			}
			
			 case 2:
		      {
		        background(0);
		        //textFont(font, 28);
		        text("The objective is to collect all the coins.\nEasy right I forgot to mention that the platforms collapses.\nGood luck!", 100, 200);
		        break;
		      }
		}
	}
	
	public void keyPressed()
	{
		if (key >= '0' && key <='9')
		 {
		    mode = key - '0';
		 }
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
		//checking if ball is on platform
		for(int i = gameObjects.size() - 1; i >= 0; i--)
		{
			GameObject go = gameObjects.get(i);
		    if (go instanceof Platform)
		    {
				if((ball.pos.y + ball.radius) > go.pos.y && (ball.pos.y + ball.radius) < (go.pos.y + go.h) && ball.pos.x > go.pos.x && ball.pos.x < (go.pos.x + go.w))
				{
					g = 0;
					ball.pos.y = go.pos.y - ball.radius;
					
					//platforms excluding start and end start to decay
					if(go != startP && go != endP)
					{
						go.platDecay= true;
					}
				}
		    }
		}
		
		//ball resets when it falls off screen
		if(ball.pos.y > height)
		{
			//remove platforms excluding start and end
			remove();
			ball.pos.x = ball.radius;
			ball.pos.y = startP.pos.y - ball.radius;
			reset = true;
		}
		
		//ball goes to the next level when all coins are collected
		if((ball.pos.x + ball.radius) > width && (ball.pos.y + ball.radius) <= endP.pos.y)
		{
			if(coinCheck > 0)
			{
				;
			}
			else
			{
				ball.pos.x = ball.radius;
				remove();
				levelTransition();
			}
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
	
	//remove all decaying platforms and coins
	public void remove()
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
			
			if(coinCheck > 0)
			{
				if(go instanceof Coin)
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
							score += 50;
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
		
		//counting no.of coins created
		for(int i = gameObjects.size() - 1; i>= 0; i--)
		{
			GameObject go = gameObjects.get(i);
			if(go instanceof Coin)
			{
				coinCheck ++;
			}
		}
	}
	
	void levelTransition()
	{
		level++;
		drawPlatforms();
		drawn = 1;
		coinCheck = 0;
		drawCoins();
		
		//resetting the end Platform
		for(int i = gameObjects.size() - 1; i>= 0; i--)
		{
			GameObject go = gameObjects.get(i);
			if(go instanceof Platform && go == endP)
			{
				go.c = color(139, 58, 7);
			}
		}
	}
}
