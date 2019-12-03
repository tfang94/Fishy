import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class EnemyFish extends FishObject implements Comparable<EnemyFish> {

	public static Random r = new Random();
	
	public EnemyFish(int courtWidth, int courtHeight) {
		super(randVX(), 0, 0, randY(), randR(), courtWidth, courtHeight);
		pos_x -= length;
	}
	
	public static int randVX() {
		int vx = 1+ r.nextInt(3);
		return vx;
	}
	
	public static int randY() {
		int y = r.nextInt(340);
		return y;
	}
	
	public static int randR() {
		int r1 = r.nextInt(150);
		return r1;
	}
	
	public Area getArea() {
		GeneralPath fish = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		fish.moveTo(0, 0);
		fish.lineTo(5.0/30*length, 7.0/30*length);
		fish.curveTo(14.0/30*length, 0, 21.0/30*length, 0, .9*length, 10.0/30*length);
		fish.curveTo(21.0/30*length, 20.0/30*length, 14.0/30*length, 20.0/30*length, 5.0/30*length,
				13.0/30*length);
		fish.lineTo(0, 20.0/30*length);
		fish.lineTo(0, 0);
				
		Area FISH = new Area(fish);

		AffineTransform translate = new AffineTransform();
		translate.setToTranslation(pos_x, pos_y);
		
		FISH.transform(translate);
		
		return FISH;
	}
	
	public void moveto(int x, int y) {
		pos_x = x;
		pos_y = y;
	}
	
	@Override
	public void move() {
		pos_x += v_x;
		pos_y += v_y;
	}

	@Override
	public int compareTo(EnemyFish o) {
		// TODO Auto-generated method stub
		if (this.pos_y == o.getY() && this.length == o.getR() && 
				this.v_x == o.getvx()) {
		return 0;
		}
		else {
			return 1;
		}
	}

}
