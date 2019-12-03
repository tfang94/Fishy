import java.awt.Graphics;
import java.awt.geom.Area;

public class FishObject extends Area implements Fish {
	public int pos_x;
	public int pos_y;
	public int length;
	public int v_x;
	public int v_y;

	
	public FishObject(int v_x, int v_y, int pos_x, int pos_y, 
		int length, int court_width, int court_height) {
		this.v_x = v_x;
		this.v_y = v_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.length = length;
		

	}
	
	//The subclass methods will overwrite this
	public Area getArea() {
		return null;
	}
	
	public int getX() {
		return pos_x;
	}
	
	public int getY() {
		return pos_y;
	}
	
	public int getR() {
		return length;
	}
	
	public int getvx() {
		return v_x;
	}
	
	public int getvy() {
		return v_y;
	}
	

	
	public boolean intersects(FishObject obj) {
		Area thisArea = new Area(this.getArea());
		Area objArea = new Area(obj.getArea());
		Area objCollide = new Area(obj.getArea());
		objCollide.subtract(thisArea);
		if (objArea.equals(objCollide)) {
			return false;
		}
		
		else {
			return true;
		}
	}
	
	public void draw(Graphics g) {
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}



}
