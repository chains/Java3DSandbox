package purpledude;

/*
 * FollowcamMorph.java
 *
 * Created on 21 April 2003, 21:44
 */
import java.util.Enumeration;
import javax.media.j3d.*;
import com.sun.j3d.utils.timer.J3DTimer;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * 
 * @author Phil Clarke
 */
public class FollowcamMorph extends javax.media.j3d.Behavior {

	private boolean walking;
	private boolean fwDown;
	private boolean leftDown;
	private boolean rightDown;

	private int upSum;

	private double weights[];
	private double step = 0.0002;

	private long oldTime;

	private Alpha alpha;
	private Morph morph;

	private Vector3d camVec;

	private Transform3D camTrans;

	private TransformGroup ourTG;
	private TransformGroup camTG;

	private PickTool picker;

	private WakeupOnElapsedFrames framewake = null;
	private WakeupOnAWTEvent starter = null;
	private WakeupOnAWTEvent stopper = null;
	private WakeupCondition wakeUpCondition = null;
	private WakeupCondition keepUpCondition = null;
	private WakeupCriterion[] wakeupArray;
	private WakeupCriterion[] continueArray;

	/**
	 * Creates a new MoveMorph with the Alpha, Morph and TransformGroup objects
	 * it has been given.
	 * 
	 * @param a
	 *            The alpha to base the behaviour on.
	 * @param m
	 *            The morph object we are animating.
	 * @param moveMe
	 *            The TransformGroup we are to move.
	 * @param px
	 *            A Picktool to find our height.
	 * @param vista
	 *            The transformGroup for the ViewingPlatform
	 */
	public FollowcamMorph(Alpha a, Morph m, TransformGroup moveMe, PickTool px,
			TransformGroup vista) {
		picker = px;
		alpha = a;
		morph = m;
		ourTG = moveMe;
		camTG = vista;
		initCamera();
		weights = morph.getWeights();
		starter = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		stopper = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
		framewake = new WakeupOnElapsedFrames(0);
		continueArray = new WakeupCriterion[2];
		continueArray[0] = stopper;
		continueArray[1] = framewake;
		keepUpCondition = new WakeupOr(continueArray);
	}

	/**
	 * initializes the behaviour. Our intial starting condition is a keypress.
	 */
	public void initialize() {
		wakeupOn(starter);
	}

	/**
	 * Overrides behaviour's processStimulus method. Triggered by a keypress or
	 * release or on every frame, depending on whether the animation is running
	 * or not.
	 */
	public void processStimulus(Enumeration criteria) {

		WakeupCriterion ster;

		while (criteria.hasMoreElements()) {
			ster = (WakeupCriterion) criteria.nextElement();
			if (ster instanceof WakeupOnAWTEvent)
				processAWTEvent(((WakeupOnAWTEvent) ster).getAWTEvent());
		}

		if (walking) {
			double val = alpha.value();
			if (val < 0.5) {
				double a = val * 2.0;
				weights[0] = 1.0 - a;
				weights[1] = a;
				weights[2] = 0.0;
			} else {
				double a = (val - 0.5) * 2.0;
				weights[0] = 0.0;
				weights[1] = 1.0f - a;
				weights[2] = a;
			}
			morph.setWeights(weights);

		}
		if (upSum > 0)
			moveStep();
		else
			wakeupOn(starter);
	}

	private void moveStep() {
		if (rightDown || leftDown || fwDown) {

			upSum = 0;
			double stepper = getStep();
			Transform3D tempTrans = new Transform3D();
			Transform3D ourTrans = new Transform3D();
			ourTG.getTransform(ourTrans);

			if (rightDown || leftDown) {
				if (rightDown) {
					tempTrans.rotY(0 - stepper);
					upSum++;

				} else {
					tempTrans.rotY(stepper);
					upSum++;
				}
			}
			if (fwDown) {

				upSum++;

				Point3d pickStart = new Point3d();
				Vector3d down = new Vector3d(0.0, -1.0, 0.0);
				Transform3D newT3d = new Transform3D();
				newT3d.setTranslation(new Vector3d(0.0, 1.0, stepper));
				newT3d.mul(ourTrans);
				newT3d.transform(pickStart);

				picker.setShapeRay(pickStart, down);

				PickResult picked = picker.pickClosest();
				PickIntersection intersect = picked.getIntersection(0);
				Point3d nextpoint = intersect.getPointCoordinates();
				double pickY = (pickStart.y);

				pickY = pickY - 1.0;

				if (nextpoint != null) {
					if (nextpoint.y != pickY) {
						Vector3d translate = new Vector3d();
						ourTrans.get(translate);
						translate.y = nextpoint.y;
						ourTrans.setTranslation(translate);
						// ourTrans.setTranslation(new Vector3d(pickStart.x,
						// nextpoint.y, pickStart.z));
					}
				}

				tempTrans.setTranslation(new Vector3d(0.0d, 0.0d, stepper));
				System.err.println("      Stepper is " + stepper);

			}
			ourTrans.mul(tempTrans);
			ourTG.setTransform(ourTrans);
			translateCamera(ourTrans);
			wakeupOn(keepUpCondition);
		}
	}

	private void initCamera() {
		camTrans = new Transform3D();
		camVec = new Vector3d();
		camTG.getTransform(camTrans);
		camTrans.get(camVec);
	}

	private void translateCamera(Transform3D ourTrans) {
		Vector3d ourVec = new Vector3d();
		Vector3d up = new Vector3d(0, 1, 0);
		ourTrans.get(ourVec);
		ourVec.add(camVec);

		Point3d cam = new Point3d();
		Point3d man = new Point3d();

		camTrans.setTranslation(ourVec);
		ourTrans.transform(man);
		camTrans.transform(cam);

		camTrans.lookAt(cam, man, up);
		camTrans.invert();

		camTG.setTransform(camTrans);
	}

	/**
	 * Returns the size of the step for the current frame based on the time of
	 * the previous step.
	 * 
	 * @return The size of the step;
	 */
	public double getStep() {
		long echo = J3DTimer.getValue();
		double wrongun = ((echo - oldTime) / 1000);
		wrongun = wrongun * step;
		oldTime = echo;
		return wrongun;
	}

	private void processAWTEvent(AWTEvent[] events) {
		for (int n = 0; n < events.length; n++) {
			if (events[n] instanceof KeyEvent) {
				KeyEvent ek = (KeyEvent) events[n];

				if (ek.getID() == KeyEvent.KEY_PRESSED) {
					System.err.println("key event " + ek.getKeyChar());
					if (ek.getKeyChar() == 'w') {
						alpha.setStartTime(System.currentTimeMillis());
						walking = true;
						fwDown = true;
						getStep();
						upSum++;
					} else if (ek.getKeyChar() == 'a') {
						leftDown = true;
						upSum++;
					} else if (ek.getKeyChar() == 'd') {
						rightDown = true;
						upSum++;
					}
				} else if (ek.getID() == KeyEvent.KEY_RELEASED) {
					if (ek.getKeyChar() == 'w') {
						walking = false;
						fwDown = false;
						upSum--;

					} else if (ek.getKeyChar() == 'a') {
						leftDown = false;
						upSum--;
					} else if (ek.getKeyChar() == 'd') {
						rightDown = false;
						upSum--;
					}
				}
			}
		}
	}

}
