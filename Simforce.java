import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import javax.swing.Timer;

import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import javax.swing.Timer;

public class Simforce implements ActionListener 
{
	private TransformGroup tGroup;
	private ExpandSphere sphere;
	private double dTime = 0.1;
	
	private Transform3D move;
	
	private Timer timer;


	public static void main(String[] args) 
	{
		
		System.setProperty("sun.awt.noerasebackground", "true");
		new Force();
		

	}
	
	public Simforce()
	{
		SimpleUniverse uni = new SimpleUniverse();
		BranchGroup scene = createSceneGraph();
		uni.getViewingPlatform().setNominalViewingTransform();
		uni.addBranchGraph(scene);
		
		timer = new Timer(100, this);
		timer.start();
	}
	
	public BranchGroup createSceneGraph()
	{
		
		BranchGroup bGroup = new BranchGroup();
		sphere = new ExpandSphere(0.1f,-0.80,0,0);
		
	
		tGroup = new TransformGroup();
		move = new Transform3D();
		
		move.setTranslation(sphere.disp()); //starts out (-0.8, 0, 0)
		tGroup.setTransform(move);
		
		tGroup.addChild(sphere);
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
		
		float Force = 10f; //change in x of 0.01 equivalent to 1 m, force is 10 N
		float mass = 1f; //1 kg        
		double acc = (Force*0.01/mass);
		sphere.setAcc(acc);
		
		Vector3d disp = sphere.disp();
		double vel = sphere.vel();
		vel = vel + dTime*acc;
		double newX = disp.getX() + 0.5*acc*Math.pow(dTime, 2) + vel*dTime;
		vel = vel + dTime*acc;
		
		sphere.setVel(vel);
		sphere.setDisp(new Vector3d(newX, disp.getY(), disp.getZ()));
		
		move.setTranslation(sphere.disp());
		tGroup.setTransform(move);
	}


}


