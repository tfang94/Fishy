import java.awt.geom.Area;

public interface Fish {

	Area getArea();
	int getX();
	int getY();
	
	//r is the length of the side of the bounding box
	int getR();
	int getvx();
	int getvy();
	void move();
}
