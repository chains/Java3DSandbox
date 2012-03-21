package purpledude;

/*
 * OurShape2D.java
 *
 * Created on 25 March 2003, 23:43
 */
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import javax.vecmath.*;

/**
 * 
 * @author Ben Moxon
 */
public class OurShape2D extends javax.media.j3d.Shape3D {

	private static Point3f A = new Point3f(-0.5f, 0.0f, 0.0f);
	private static Point3f B = new Point3f(-0.5f, 1.0f, 0.0f);
	private static Point3f C = new Point3f(0.5f, 1.0f, 0.0f);
	private static Point3f D = new Point3f(0.5f, 0.0f, 0.0f);

	/** Creates a new instance of OurShape2D */
	public OurShape2D() {

		Point3f[] pts = new Point3f[8];
		// front
		pts[0] = C;
		pts[1] = D;
		pts[2] = A;
		pts[3] = B;
		// back
		pts[4] = C;
		pts[5] = B;
		pts[6] = A;
		pts[7] = D;

		int[] stripCounts = new int[2];
		stripCounts[0] = 4;
		stripCounts[1] = 4;

		int[] contourCount = new int[2];
		contourCount[0] = 1;
		contourCount[1] = 1;

		GeometryInfo gInf = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

		gInf.setCoordinates(pts);
		gInf.setStripCounts(stripCounts);
		gInf.setContourCounts(contourCount);

		NormalGenerator ng = new NormalGenerator();
		ng.setCreaseAngle((float) Math.toRadians(30));
		ng.generateNormals(gInf);

		this.setGeometry(gInf.getGeometryArray());

	}

}
