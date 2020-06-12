package flocking;


import java.text.DecimalFormat;

public class JVector{
	
	public double x = 0,
				  y = 0;
	private double doubleError = 0.0001;
	
	public JVector() {
	}
	
	public JVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public JVector(JVector otherJVector) {
		this.x = otherJVector.x;
		this.y = otherJVector.y;
	}
	
	public double mag()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	public double mag2()
	{
		return x*x + y*y;
	}
	
	public JVector normalize() 
	{
		double mag = this.mag();
		return new JVector(this.x/mag, this.y/mag);
	}
	
	public void normalizeEquals() 
	{
		double mag = this.mag();
		this.x /= mag;
		this.y /= mag;
	}
	
	public void clip(double length)
	{
		this.normalizeEquals();
		this.starEquals(length);
	}
	
	public JVector trim(double l, JVector otherJVector)
	{
		double a,b,c,g,p,q,x,y,s,t;  //Quadratic Equation Variables
		
		x = this.x;
		y = this.y;
		p = otherJVector.x;
		q = otherJVector.y;
		
		a = p*p + q*q;
		b = 2*(x*p+q*y);
		c = -(l*l - x*x - y*y);
		
		g = Math.sqrt(b*b - 4*a*c);
		
		s = ((-b)+g)/(2*a);
		t = ((-b)-g)/(2*a);
		
		s = Math.max(s,t);
		
		return new JVector(this.x + p*s, this.y + q*s);
	}
	
	public boolean equals(JVector otherJVector) {
		double otherX = otherJVector.x;
		double otherY = otherJVector.y;
		if ( (this.x <= (otherX + doubleError)) && 
			 (this.x >= (otherX - doubleError)) &&
			 (this.y <= (otherY + doubleError)) &&
			 (this.y >= (otherY - doubleError)) ) { 
			return true; 
		} else {
			return false;
		}
	}
	
	public JVector plus(JVector ... otherJVector) {
		JVector temp = new JVector();
		double tempX = 0,
			   tempY = 0;
		for(int i=0; i<otherJVector.length; i++) {
			tempX += otherJVector[i].x;
			tempY += otherJVector[i].y;
		}
		temp.x = this.x + tempX;
		temp.y = this.y + tempY;
		return temp;
	}
	
	public void plusEquals(JVector ... otherJVector) {
		double tempX = 0,
			   tempY = 0;
		for(int i=0; i<otherJVector.length; i++) {
			tempX += otherJVector[i].x;
			tempY += otherJVector[i].y;
		}
		this.x += tempX;
		this.y += tempY;
	}
	
	public JVector minus(JVector ... otherJVector) {
		JVector temp = new JVector();
		double tempX = 0,
			   tempY = 0;
		for(int i=0; i<otherJVector.length; i++) {
			tempX += otherJVector[i].x;
			tempY += otherJVector[i].y;
		}
		temp.x = this.x - tempX;
		temp.y = this.y - tempY;
		return temp;
	}
	
	public void minusEquals(JVector ... otherJVector) {
		double tempX = 0,
			   tempY = 0;
		for(int i=0; i<otherJVector.length; i++) {
			tempX += otherJVector[i].x;
			tempY += otherJVector[i].y;
		}
		this.x -= tempX;
		this.y -= tempY;
	}
	
	public JVector star(double scaler) {
		return new JVector(x*scaler, y*scaler);
	}
	
	public void starEquals(double scaler)
	{
		x *= scaler;
		y *= scaler;
	}
	
	public JVector divide(double scaler) 
	{
		return new JVector(x/scaler, y/scaler);
	}
	
	public void divideEquals(double scaler)
	{
		x /= scaler;
		y /= scaler;
	}
	
	public double dot(JVector otherJVector) {
		return (this.x * otherJVector.x + this.y * otherJVector.y);
	}
	
	public String toString() { 
		DecimalFormat fmt = new DecimalFormat("0.##");
		return "[" + fmt.format(this.x) + "," + fmt.format(this.y) + "]";
	}
	
	public void copy(JVector otherJVector)
	{
		this.x = otherJVector.x;
		this.y = otherJVector.y;
	}
}