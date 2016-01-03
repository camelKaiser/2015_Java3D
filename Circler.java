import java.awt.event.*;
import java.util.Scanner;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;

import javax.swing.Timer;
public class Circler implements ActionListener
{
	private ExpandSphere ball;
	
	public static double mass;
	public static double radius;
	public static double torque;
	
	private double time = 0.01;
	private Timer timer;
	private Transform3D trans;
	private Transform3D circle;
	private double angle = 0;
	private TransformGroup tGroup;
	
	public static void main(String[] arg)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("mass of ball? ");
		mass = scan.nextDouble();
		System.out.println("distance from center?");
		radius = scan.nextDouble();
		System.out.println("torque?");
		torque = scan.nextDouble();
		
		System.setProperty("sun.awt.noerasebackground", "true");
		new Circler();
		
	}
	
	public Circler()
	{
		BranchGroup scene = createBranchGroup();
		SimpleUniverse uni = new SimpleUniverse();
		

		uni.addBranchGraph(scene);
		uni.getViewingPlatform().setNominalViewingTransform();
		
		timer = new Timer(100, this);
		timer.start();
		
	}
	
	public BranchGroup createBranchGroup() 
	{
		BranchGroup bGroup = new BranchGroup();
		
		ball = new ExpandSphere(0.1f,0,0,0);
		tGroup = new TransformGroup();
		circle = new Transform3D();
		trans = new Transform3D();
		trans.set(new Vector3f((float)(radius/10), 0.0f, 0.0f));
		tGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		circle = trans;
	
		tGroup.addChild(ball);
		tGroup.setTransform(circle);
		bGroup.addChild(tGroup);
		
		Color3f pink = new Color3f(0.945f,0.473f,0.836f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10); //sphere 10 radius

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
	
	public void actionPerformed(ActionEvent e)
	{
		double inertia = 0.5*mass*(Math.pow(radius, 2));
		double alpha = torque/inertia;
		ball.setAcc(alpha);
		ball.setAngle(ball.angle() + ball.vel()*time + (0.5*alpha*Math.pow(time,2)));
		ball.setVel(ball.vel() + ball.acc()*time);
		
		Transform3D rot = new Transform3D();
		rot.rotZ(ball.angle());
		circle.mul(rot, trans);
		tGroup.setTransform(circle);
		System.out.println(ball.angle());
		//angle = angle + 0.001;
		
	}
	
}
