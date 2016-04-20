package ie.dit;
import processing.core.*;
import java.util.ArrayList;

public class Main extends PApplet 
{
	public void setup()
	{
		size(700, 500);
		background(255);
		ball = new Ball(this, 150.0f, ' ', 'A', 'D');
		startP = new Platform(this, 0, 150.0f, 60, 80);
		endP = new Platform(this, 645.0f, 150.0f, 60, 80);
		g = 1;
		launch = 13;
		reset = true;
		checkPoint = 1;
		level = 1;
		drawn = 1;
		coinCheck = 0;
		font = loadFont("AgencyFB-Reg-48.vlw");
		
	}
	
	
	//create instance of ball
	Ball ball;
	static Platform startP, endP; //start and end platform
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	static int g;
	int launch;
	float gravity;
	boolean reset;
	static int level;
	int drawn; //limit no. of coins drawn
	int coinCheck = 0; //total no. of coins
	int mode;
	static boolean[] keys = new boolean[512];
	int checkPoint;
	PFont font;
	
	public void draw()
	{
		switch(mode)
		{
			case 0:
			{
				background(0);
				textFont(font, 32);
				text("1. Play Game", 100, 100);
			    text("2. Instructions", 100, 200);
			    ball.score = 0;
			    coinCheck = 0;
				break;
			}
			
			case 1:
			{
				if(reset)
				{
					background(255);
					gameObjects.add(ball);
					gameObjects.add(startP);
					gameObjects.add(endP);
					drawPlatforms();
					drawn = 1;
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
					reset = false;
				}
				
				background(255);
				fill(0);
				text("Score: "+ ball.score, 300, 30);
				fill(255);
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

				}
				
				coinCollisions();
				
				//if there are no coins left change the color of endPlatform
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
				break;
			}
			
			 case 2:
		      {
		        background(0);
		        textFont(font, 28);
		        text(" Press 'Space' bar to jump 'A' and 'D'\n to move left and right respectively .\nThe objective is to collect all the coins.\nEasy right I forgot to mention that the platforms collapses.\nGood luck!", 100, 200);
		        break;
		      }
		}
	}
	
	public void keyPressed()
	{
		keys[keyCode] = true;
		
		if (key >= '0' && key <='9')
		 {
		    mode = key - '0';
		 }

		 
	}
	
	public void keyReleased()
	{
		keys[keyCode] = false;
	}
	
	public void gForce()
	{
	
		g = 1;
		
		//if the ball is not on a platform ball drops 
		if(g == 1)
		{
			ball.pos.y += launch;
		}
		
		landCheck();
		
		//if the ball is on a platform gravity is 0
		if(g == 0)
		{
			gravity = 0;
		}
	}
	
	public void jump(int launch)
	{
		//
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
					reset = true;
				    mode = 0;
				   
				      ball.pos.x = ball.radius;
					  ball.pos.y = startP.pos.y - ball.radius;
				    
				
			}
		
		//if the ball is on the end platform check if coins are collected to proceed to next level
		if((ball.pos.x + ball.radius) > width && (ball.pos.y + ball.radius) <= endP.pos.y)
		{
			if(coinCheck > 0)
			{
				ball.pos.x = width - ball.radius;
			}
			else
			{
				ball.pos.x = ball.radius;
				remove();
				levelTransition();
			}
		}
	}
	
	//draw collapsing platform
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
				float x = 483 + (i * 40);
				float y = 150.0f;
				Platform p = new Platform(this, x, y, 42, 20.0f);
				gameObjects.add(p);
			}
		}
		
		if(level == 3)
		{
			 for(int i = 1; i < 4; i++)
			 {
				 float x = 60 + (i * 40);
				 float y = 180;
				 Platform p = new Platform(this, x, y, 42, 20.0f);
				 gameObjects.add(p);
			 }
			 
			 for(int i = 1; i < 5; i++)
			 {

				 float x = 180 + (i * 40);
				 float y = 260;
				 Platform p = new Platform(this, x, y, 42, 20.0f);
				 gameObjects.add(p);
			 }
			 
			 for(int i = 1; i < 4; i++)
			 {
				 float x = 350 + (i * 40);
				 float y = 320;
				 Platform p = new Platform(this, x, y, 42, 20.0f);
				 gameObjects.add(p);
			 }
			 
			 for(int i = 1; i < 5; i++)
			 {
				 float x = 350 + (i * 40);
				 float y = 160;
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
	
	//remove all decaying 
	public void remove()
	{
			for(int i = gameObjects.size()- 1; i>=0; i--)
			{
				GameObject go = gameObjects.get(i);
				gameObjects.remove(go);
		}

	}
	
	//check ball colliding with
	void coinCollisions()
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
						if(go.pos.dist(other.pos) < go.radius + (other.radius + 10))
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
			GameObject coin3 = new Coin(this, 450, 180, 17);
			gameObjects.add(coin3);
		}
		
		if(level == 3)
		{
			GameObject coin1 = new Coin(this, 150, 165, 17);
			gameObjects.add(coin1);
			GameObject coin2 = new Coin(this, 440, 300, 17);
			gameObjects.add(coin2);
			GameObject coin3 = new Coin(this, 480, 135, 17);
			gameObjects.add(coin3);
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
		checkPoint = level;
		coinCheck = 0;
		gameObjects.add(ball);
		gameObjects.add(startP);
		gameObjects.add(endP);		
		drawPlatforms();
		drawn = 1;
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
		
		if(level == 4)
		{
			remove();
			mode = 0;
			level = 1;
		}
	}
}
