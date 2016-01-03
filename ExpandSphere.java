import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import javax.swing.Timer;

public class ExpandSphere extends Sphere
{
	private double acc;
	private double vel;
	private Vector3d disp;
	private double angle;
	
	public ExpandSphere(float radius, double x, double y, double z)
	{
		super(radius);
		acc = 0;
		vel = 0;
		disp = new Vector3d(x,y,z);
	}
	
	public double acc()
	{
		return acc;
	}
	public double vel()
	{
		return vel;
	}
	public Vector3d disp()
	{
		return disp;
	}
	public void setAcc(double acc)
	{
		this.acc = acc;
	}
	public void setVel(double vel)
	{
		this.vel = vel;
	}
	public void setDisp(Vector3d disp)
	{
		this.disp = disp;
	}
	public double angle()
	{
		return angle;
	}
	public void setAngle(double angle)
	{
		this.angle = angle;
	}

}
