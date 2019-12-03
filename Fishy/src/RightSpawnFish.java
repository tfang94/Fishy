import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class RightSpawnFish extends EnemyFish implements Comparable<EnemyFish>{
	
	public static Random r = new Random();
	
	public RightSpawnFish(int courtWidth, int courtHeight) {
		super(courtWidth, courtHeight);
		pos_x = 600 + length;
		v_x *= -1;
	}
	
	@Override
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

		AffineTransform flip = new AffineTransform();
		flip.scale(-1, 1);
		
		AffineTransform translate = new AffineTransform();
		translate.setToTranslation(pos_x, pos_y);
		
		FISH.transform(flip);
		FISH.transform(translate);
		
		return FISH;
	}
	
}