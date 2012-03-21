package purpledude;

/*
 * MoveMorph.java
 *
 * Created on 10 April 2003, 22:46
 */
import java.util.Enumeration;
import javax.media.j3d.*;
import com.sun.j3d.utils.timer.J3DTimer;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Ben Moxon
 */
public class MoveMorph extends javax.media.j3d.Behavior {

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

	private TransformGroup ourTG;

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
	 */
	public MoveMorph(Alpha a, Morph m, TransformGroup moveMe) {
		alpha = a;
		morph = m;
		ourTG = moveMe;
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
			/*
			 * This is some debugging code I used to find whether upSum was the
			 * expected value (it wasn't) - I leave it here as an example of the
			 * kind of thing you will probably find handy if you don't know what
			 * is going wrong with your program.
			 * 
			 * System.err.print("upSum = "+upSum+", "); if (rightDown)
			 * System.err.print("right, "); if (leftDown)
			 * System.err.print("left, "); if (fwDown)
			 * System.err.print("forward, ");
			 * System.err.println(" - - -responding...");
			 */
			upSum = 0;
			// Vector3d ourRelation = new Vector3d();
			// ourTG.get(ourRelation);
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
				tempTrans.setTranslation(new Vector3d(0.0d, 0.0d, stepper));

			}
			ourTrans.mul(tempTrans);
			ourTG.setTransform(ourTrans);
			wakeupOn(keepUpCondition);
		}
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
