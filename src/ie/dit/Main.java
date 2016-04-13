package ie.dit;
import processing.core.*;

public class Main extends PApplet 
{
	public void setup()
	{
		size(700, 500);
		background(255);
		ball = new Ball(this, height);
	}
	
	//create instance of ball
	Ball ball;
	
	public void draw()
	{
		background(255);
		stroke(0);
		
		fill(255);
		ball.render();
	}
	
	public static void main(String[] args)
	{
		String[] a = {"MAIN"};
	    PApplet.runSketch( a, new Main());
	}
}
