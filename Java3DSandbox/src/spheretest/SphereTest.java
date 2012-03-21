package spheretest;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;

public class SphereTest {

	private SimpleUniverse uni;
	private BranchGroup groups;
	private Sphere sphere;
	private DirectionalLight light;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SphereTest test = new SphereTest();
		test.run();

	}

	public void run() {
		uni = new SimpleUniverse();
		groups = new BranchGroup();
		sphere = new Sphere(0.5f);
		sphere.setCapability(15);
		sphere.setAppearance(createAppearance());
		
		groups.addChild(sphere);
		light = new DirectionalLight();
		groups.addChild(light);
		
		addLights();
	}

	public Appearance createAppearance() {
		Appearance app = new Appearance();
		app.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		Material mat = new Material();
		mat.setCapability(Material.ALLOW_COMPONENT_WRITE);
		mat.setDiffuseColor(new Color3f(1.0f, 0.0f, 0.0f));
		mat.setSpecularColor(new Color3f(1.0f, 0.0f, 0.0f));
		app.setMaterial(mat);
		return app;
	}
	
	public void addLights(){
    	BoundingSphere bounds =	new BoundingSphere (new Point3d (0, 0.0, 5), 5.0);
    	Color3f lightColor = new Color3f (1.0f, 1.0f, 1.0f);
    	Vector3f light1Direction = new Vector3f (0.0f, 0.0f, -1f);
		
    	DirectionalLight light1  = new DirectionalLight (lightColor, light1Direction);
    	light1.setInfluencingBounds (bounds);
    	groups.addChild (light1);

    	AmbientLight ambientLightNode = new AmbientLight (lightColor);
    	ambientLightNode.setInfluencingBounds (bounds);
    	groups.addChild (ambientLightNode);
		
		uni.getViewingPlatform().setNominalViewingTransform();
		uni.addBranchGraph(groups);
		
	}

}
