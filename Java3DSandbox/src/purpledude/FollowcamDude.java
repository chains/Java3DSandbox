package purpledude;

/*
 * FollowcamDude.java
 *
 * Created on 21 April 2003, 21:44
 */
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.loaders.Scene;
import com.glyphein.j3d.loaders.milkshape.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.geometry.*;

/**
 * 
 * @author Ben Moxon
 */
public class FollowcamDude extends java.applet.Applet {

	private SimpleUniverse u;
	private BoundingSphere bounds;
	private ViewingPlatform ourView;

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		OrbitBehavior B;
		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D c = new Canvas3D(config);
		add("Center", c);
		SimpleUniverse u = new SimpleUniverse(c);
		ourView = u.getViewingPlatform();
		Transform3D locator = new Transform3D();
		locator.setTranslation(new Vector3f(0, 3f, -3f));
		locator.lookAt(new Point3d(0d, 3d, -3d), new Point3d(0d, 0d, 5d),
				new Vector3d(0d, 1d, 0d));
		locator.invert();
		ourView.getViewPlatformTransform().setTransform(locator);

		BranchGroup scene = createSceneGraph();
		u.addBranchGraph(scene);

		System.out.println("Should be visible now.");

	}

	private BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
		TransformGroup dudeGroup = new TransformGroup();

		dudeGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		dudeGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		String filePath = "file://localhost/" + System.getProperty("user.dir")
				+ "/";

		MS3DLoader loader = new MS3DLoader();

		Scene scene = null;
		Shape3D shape;
		GeometryArray geoms[] = new GeometryArray[3];

		for (int i = 0; i < 3; i++) {
			geoms[i] = null;
			;
		}

		try {
			java.net.URL objFiles[] = new java.net.URL[3];
			objFiles[0] = new java.net.URL(filePath
					+ "models/msFigureStep1.ms3d");
			objFiles[1] = new java.net.URL(filePath
					+ "models/msFigureStanding.ms3d");
			objFiles[2] = new java.net.URL(filePath
					+ "models/msFigureStep2.ms3d");

			for (int i = 0; i < 3; i++) {
				try {
					scene = loader.load(objFiles[i]);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}

				BranchGroup b = scene.getSceneGroup();
				shape = (Shape3D) b.getChild(0);
				geoms[i] = (GeometryArray) shape.getGeometry();
			}
		} catch (Exception e) {
			System.err.println("could not load file! " + filePath);
			e.printStackTrace();
		}

		Morph ourMorph = new Morph(geoms);
		ourMorph.setCapability(Morph.ALLOW_WEIGHTS_READ);
		ourMorph.setCapability(Morph.ALLOW_WEIGHTS_WRITE);
		ourMorph.setCapability(Morph.ALLOW_GEOMETRY_ARRAY_READ);

		Appearance app = new Appearance();
		Color3f objColor = new Color3f(0.7f, 0.2f, 0.8f);
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		app.setMaterial(new Material(objColor, black, objColor, black, 80.0f));
		ourMorph.setAppearance(app);

		Alpha morphAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE
				| Alpha.DECREASING_ENABLE, 0, 0, 600, 100, 100, 600, 100, 100);

		dudeGroup.addChild(ourMorph);

		String texturePath = filePath + "stone.jpg"; // file://localhost/"+System.getProperty("user.dir")+"/stone.jpg";
		SimpleLand s = new SimpleLand(10, 10, texturePath);

		PickTool p = new PickTool(objRoot);
		p.setCapabilities(s, PickTool.INTERSECT_FULL);

		FollowcamMorph mBeh = new FollowcamMorph(morphAlpha, ourMorph,
				dudeGroup, p, ourView.getViewPlatformTransform());
		mBeh.setSchedulingBounds(bounds);

		dudeGroup.addChild(mBeh);
		dudeGroup.setPickable(false);

		Color3f alColour = new Color3f(0.4f, 0.4f, 0.4f);
		AmbientLight aLgt = new AmbientLight(alColour);
		aLgt.setInfluencingBounds(bounds);

		Color3f DirColour = new Color3f(0.35f, 0.35f, 0.3f);
		Vector3f DirVec = new Vector3f(0.5f, -0.2f, 0.6f);

		DirectionalLight DirLig = new DirectionalLight(DirColour, DirVec);
		DirLig.setInfluencingBounds(bounds);

		Background bg = new Background();
		bg.setApplicationBounds(bounds);

		BranchGroup bgGeometry = new BranchGroup();

		Appearance App = new Appearance();
		try {
			Texture tex = new TextureLoader(new java.net.URL(filePath
					+ "skysec.jpg"), this).getTexture();
			App.setTexture(tex);
			System.out.println("Looks like the background loaded!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Sphere outerWorld = new Sphere(1.0f, Primitive.GENERATE_TEXTURE_COORDS
				| Primitive.GENERATE_NORMALS_INWARD, App);
		bgGeometry.addChild(outerWorld);

		bg.setGeometry(bgGeometry);
		objRoot.addChild(bg);
		objRoot.addChild(DirLig);
		objRoot.addChild(aLgt);
		objRoot.addChild(dudeGroup);
		objRoot.addChild(s);
		objRoot.compile();
		return objRoot;
	}

	// public void destroy() {
	// u.removeAllLocales();
	// }

	public static void main(String[] args) {
		new MainFrame(new FollowcamDude(), 700, 700);
	}
}
