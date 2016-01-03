import java.awt.event.*;

import java.util.Scanner;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;

import javax.swing.Timer;


//code will show a ball bouncing up and down losing energy
public class Realgravity implements ActionListener 
{
	private Fallball ball;		
	private boolean collided;
	private TransformGroup tGroup;
	
	double time = 0.01; //delta time
	double momentum;
	
	private Transform3D move;
	private Timer timer;


	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the mass. will simulate loss of energy");
		double mass = scan.nextDouble();
	
		System.out.println("Please enter initial velocity");
		double v = scan.nextDouble();
		
		System.out.println("Please enter initial height");
		double h = scan.nextDouble();
		
		System.setProperty("sun.awt.noerasebackground", "true");
		new Realgravity(mass,v,h);
		

	}
	
	public Realgravity(double mass, double v, double h)
	{
		SimpleUniverse uni = new SimpleUniverse();
		BranchGroup scene = createSceneGraph(mass,v,h);
		uni.getViewingPlatform().setNominalViewingTransform();
		uni.addBranchGraph(scene);
		
		timer = new Timer(10, this);
		timer.start();
	}
	
	public BranchGroup createSceneGraph(double mass, double v, double h)
	{
	
		
		BranchGroup bGroup = new BranchGroup(); 
		ball = new Fallball(0.1f, 0, h, 0, mass); //create new fallball object
		ball.setAcc(-9.8);
		ball.setVel(v);
		tGroup = new TransformGroup();
		move = new Transform3D();
		
		move.setTranslation(ball.disp()); //starts out (0, 0.8, 0)
		tGroup.setTransform(move);
		
		tGroup.addChild(ball);
		bGroup.addChild(tGroup);
		
		tGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//lights
		Color3f pink = new Color3f(0.945f,0.473f,0.836f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100); //sphere 100 radius

		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f); 

		DirectionalLight light1 = new DirectionalLight(pink, light1Direction); 
		
		light1.setInfluencingBounds(bounds); //set to bounding leaf
		
		bGroup.addChild(light1);
		
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);

		AmbientLight ambientLightNode = new AmbientLight(ambientColor);

		ambientLightNode.setInfluencingBounds(bounds);
		
		bGroup.addChild(ambientLightNode);
		return bGroup;
		
		 
	}
	public void actionPerformed(ActionEvent e) //called every cycle of the timer
	{	
		
		collision(); //check if currently colliding
		if(!collided) //no collision occuring
		{
				double oldY = ball.disp().getY(); //position last frame
				ball.setDisp(new Vector3d(0, ball.disp().getY() + (ball.vel()*time) + (0.5*ball.acc()*time*time),0));
				ball.setVel(ball.vel() + (time*ball.acc()));
				momentum = ball.vel()*ball.mass();
				//update positon, velocity, momentum
				
				if(oldY > ball.disp().getY()) //check if ball is falling
				{
					ball.setFall(true);
				}
				System.out.println(ball.disp().getY()); //print position
				move.setTranslation(ball.disp());
				tGroup.setTransform(move);
				
		}
		else
		{ //ball is currently hitting ground
			if(ball.falling()) //ball was just falling
			{
				momentum = momentum*(-1); //momentum has swapped
				momentum = momentum*(0.9); //ball loses 81% of its energy from hitting the ground
				ball.setVel(momentum/(ball.mass())); //balls new reverse velocity
				System.out.println("reverse vel " +  ball.vel());
				ball.setFall(false); //ball is no longer falling
				
			}
			//ball should be rising, still in "collision state"
			collided = false; 
		}
	}
	
	
	
	public void collision()
	{
		if(collided == false && !ball.falling()) //check if ball is in "collision" but rising
		{
			return;
		}
		
		if((ball.disp().getY() < -0.90)) //check if ball has hit floor
		{
			collided = true;
		}
		else
		{
			collided = false;
		}
		
	}


}


