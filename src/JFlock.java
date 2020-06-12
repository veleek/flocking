package flocking;

import java.util.ArrayList;
import java.util.Random;

import flocking.panels.FlockWorld;
import flocking.panels.FlockingBehaviors;

public class JFlock {
	
	private final int DEFAULT_FLOCK_SIZE = 10;
	
	private ArrayList<JBoid> flock = new ArrayList<JBoid>();
	private double maxAccel = 0.5,
				   maxSpeed = 10;
	private JVector avgVelocity = new JVector();
	
	private FlockingBehaviors fb;
	private FlockWorld fw;
	
	private Random rand = new Random();
	
	public JFlock(FlockWorld fw, FlockingBehaviors fb) {
		init(DEFAULT_FLOCK_SIZE,fw,fb);
	}
	
	public JFlock(int numBoids, FlockWorld fw, FlockingBehaviors fb) {
		init(numBoids,fw,fb);
	}
		
	public void init(int numBoids, FlockWorld fw, FlockingBehaviors fb) {
		this.fw = fw;
		this.fb = fb;
		setFlockSize(numBoids);
	}
		
	public void setFlockSize(int flockSize) {
		while( flock.size() < flockSize)
		{
			JBoid newBoid = new JBoid(rand.nextInt(400),rand.nextInt(400));
			newBoid.setSpeed(rand.nextDouble()*10-5, rand.nextDouble()*10-5);
			flock.add(newBoid);
			newBoid.setFlock(this);
			fw.register(newBoid);
		}
		while( flock.size() > flockSize)
		{
			JBoid removedBoid = flock.remove(flock.size()-1);
			fw.unregister(removedBoid);
		}
	}
	
	public JBoid getBoidAt(int index) {
		return flock.get(index);
	}
	
	public int getFlockSize() {
		return flock.size();
	}
	
	public void setMaxAccel(double newMaxAccel) {
		maxAccel = newMaxAccel;
		for(int i=0; i<flock.size(); i++) {
			((JBoid)flock.get(i)).setMaxAccel(newMaxAccel);
		}
	}
	
	public double getMaxAccel() {
		return maxAccel;
	}
	
	public void setMaxSpeed(double newMaxSpeed) {
		maxSpeed = newMaxSpeed;
		for(int i=0; i<flock.size(); i++) {
			((JBoid)flock.get(i)).setMaxSpeed(newMaxSpeed);
		}
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public void calcAvgVelocity() {
		JVector velocitySum = new JVector();
		for(int i=0; i<flock.size(); i++) {
			velocitySum.plusEquals(((JBoid)flock.get(i)).getVel());
		}
		avgVelocity = velocitySum.star(1/flock.size());
	}
	
	public JVector getAvgVelocity() {
		return avgVelocity;
	}
	
	public ArrayList getNearestNeighboursTo(JBoid boid)
	{
		return fw.getBoidsNear(boid.getPos());
	}
	
	public void update() {
		for(int i=0; i< flock.size(); i++)
		{
			getBoidAt(i).setNearestNeighbours();
		}
		for(int i=0; i < flock.size();i++)
		{
			JBoid thisBoid = getBoidAt(i);
			JVector behaviorAccel = fb.getAcceleration(thisBoid);
			thisBoid.setAccel(behaviorAccel);
			thisBoid.update();
		}
	}
}
		
