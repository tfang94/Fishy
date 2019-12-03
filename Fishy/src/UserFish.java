import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class UserFish extends FishObject{
	public static final int LENGTH = 25;
	public static final int INIT_X = 300;
	public static final int INIT_Y = 200;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
	
	//The user's size
	public int size;

	

	public UserFish(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, LENGTH, courtWidth,
				courtHeight);
		size = LENGTH;
	}
	
	
	public void grow(EnemyFish fish) {
		size += 0.2*fish.getR();
	}
	
	public int max_x() {
		return FishCourt.COURT_WIDTH - size;
	}
	
	public int max_y() {
		return (int) (FishCourt.COURT_HEIGHT - (2.0/3)*size);
	}
	
	public int getSize() {
		return size;
	}
	
	public void move() {
		pos_x += v_x;
		pos_y += v_y;

		clip();
	}
	
	public void clip(){
		if (pos_x < 0) pos_x = 0;
		else if (pos_x > max_x()) pos_x = max_x();

		if (pos_y < 0) pos_y = 0;
		else if (pos_y > max_y()) pos_y = max_y();
	}



	@Override
	public Area getArea() {
		GeneralPath fish = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		fish.moveTo(0, 0);
		fish.lineTo(5.0/30*size, 7.0/30*size);
		fish.curveTo(14.0/30*size, 0, 21.0/30*size, 0, .9*size, 10.0/30*size);
		fish.curveTo(21.0/30*size, 20.0/30*size, 14.0/30*size, 20.0/30*size, 5.0/30*size,
				13.0/30*size);
		fish.lineTo(0, 20.0/30*size);
		fish.lineTo(0, 0);
				
		Area FISH = new Area(fish);

		AffineTransform flip = new AffineTransform();
		flip.scale(-1, 1);
		
		AffineTransform translate = new AffineTransform();
		translate.setToTranslation(pos_x + size, pos_y);
		
		FISH.transform(flip);
		FISH.transform(translate);
		
		return FISH;
	}


}
