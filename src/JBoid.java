package flocking;

import java.util.ArrayList;

public class JBoid {

	private JFlock flock;
	private JVector pos,
					vel,
					accel;
	private ArrayList currTile;
						
	private double maxAccel = 0.3,
				   maxSpeed = 2;
	
	private ArrayList nearestNeighbours;
						
	public JBoid () {
		init(0,0,0,0);
	}
	
	public JBoid (double xPos, double yPos) 
	{
		init(xPos,yPos,0,0);
	}
	
	public JBoid (double xPos, double yPos, double xVel, double yVel)
	{
		init(xPos, yPos, xVel, yVel);
	}
	
	public void init(double xPos, double yPos, double xVel, double yVel)
	{
		pos = new JVector(xPos, yPos);
		vel = new JVector(xVel, yVel);
		accel = new JVector();
	}
	
	public void setPos (double xPos, double yPos) {
		this.pos.x = xPos;
		this.pos.y = yPos;
	}
	
	public void setPos (JVector posVector) {
		this.pos.x = posVector.x;
		this.pos.y = posVector.y;
	}
	
	public JVector getPos () {
		return pos;
	}
	
	public void setAccel (double x, double y) {
		accel.x = x;
		accel.y = y;
		
		if(accel.mag() > maxAccel)
		{
			accel.clip(maxAccel);
		}
	}
	
	public void setAccel (JVector accelVector) 
	{
		this.setAccel(accelVector.x,accelVector.y);
	}
	
	public JVector getAccel () 
	{
		return accel;
	}
	
	public void setMaxAccel (double maxAccel) 
	{
		this.maxAccel = maxAccel;
	}
	
	public double getMaxAccel()
	{
		return maxAccel;
	}
	
	public void setSpeed (double x, double y) 
	{
		this.vel.x = x;
		this.vel.y = y;
		if (vel.mag() > maxSpeed)
		{
			vel.clip(maxSpeed);
		}
	}
	
	public void setVel (JVector speedVector)
	{
		this.setSpeed(speedVector.x, speedVector.y);		
	}
	
	public JVector getVel () 
	{
		return vel;
	}
	
	public void setMaxSpeed (double maxSpeed) 
	{
		this.maxSpeed = maxSpeed;
	}
	
	public double getMaxSpeed()
	{
		return maxSpeed;
	}
	
	public void setFlock(JFlock flock)
	{
		this.flock = flock;
	}
	
	public void setNearestNeighbours()
	{
		nearestNeighbours = flock.getNearestNeighboursTo(this);
		nearestNeighbours.remove(this);
	}
	
	public ArrayList getNearestNeighbours()
	{
		return nearestNeighbours;
	}
	
	public void update () {
		vel.plusEquals(accel);
		if (vel.mag() > maxSpeed) {
			vel.clip(maxSpeed);
		}
		pos.plusEquals(vel);
	}
		
	public String toString()
	{
		return "Pos:" + pos + " Vel: " + vel;
	}

	/**
	 * @return Returns the currTile.
	 */
	public ArrayList getCurrTile() {
		return currTile;
	}

	/**
	 * @param currTile The currTile to set.
	 */
	public void setCurrTile(ArrayList currTile) {
		this.currTile = currTile;
	}
}
	
	