package fr.emn.oadymppac.widgets;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Range Slider 
 *
 * @author Felix Sukhenko
 * @version 0.1 2/04/98
 * Description: A scrollbar like slider only with two thumbs which allows the
 *    user to visually specify a range. Available in both VERTICAL and
 *    HORIZONTAL orientations (look @ constructors for details). 
 *
 * Problem(s): When given to a layout manager is usually given either an 
 *   extremely short width or height. Need to fix that so that there is at
 *   least a certain minimum for both values.
 * 
 */

public class TMRangeSlider extends BorderPanel implements Adjustable {

	class RangeSliderMotionAdapter extends MouseMotionAdapter {
		boolean leftThumbDragMode, rightThumbDragMode, midDragMode;
		int initialPos;
		TMRangeSlider slider;

		RangeSliderMotionAdapter(final TMRangeSlider slider) {
			super();
			this.slider = slider;
		}

		public void mouseDragged(final MouseEvent e) {
			if (this.leftThumbDragMode) {
				if (TMRangeSlider.this.orientation == Adjustable.HORIZONTAL) {
					this.slider.dragLeftThumb(e.getX());
				}
				else {
					this.slider.dragLeftThumb(e.getY());
				}
			}
			else if (this.rightThumbDragMode) {
				if (TMRangeSlider.this.orientation == Adjustable.HORIZONTAL) {
					this.slider.dragRightThumb(e.getX());
				}
				else {
					this.slider.dragRightThumb(e.getY());
				}
			}
			else if (this.midDragMode) {
				int drag;
				if (TMRangeSlider.this.orientation == Adjustable.HORIZONTAL) {
					drag = e.getX();
				}
				else {
					drag = e.getY();
				}
				this.slider.dragBar(this.initialPos, drag);
				//initialPos = drag;
			}
		}

		public void setInitialPos(final int p) {
			this.initialPos = p;
		}

		public void setLeftThumbDragMode(final boolean mode) {
			this.leftThumbDragMode = mode;
		}

		public void setMidDragMode(final boolean mode, final int pos) {
			this.midDragMode = mode;
			this.setInitialPos(pos);
		}

		public void setRightThumbDragMode(final boolean mode) {
			this.rightThumbDragMode = mode;
		}
	}
	class RangeSliderMouseAdapter extends MouseAdapter {

		TMRangeSlider slider;
		RangeSliderMotionAdapter motionListener;
		Polygon leftIArrow, rightIArrow, leftDArrow, rightDArrow;
		Rectangle leftThumb, rightThumb, leftBlock, rightBlock, midBlock;

		RangeSliderMouseAdapter(
			final TMRangeSlider slider,
			final RangeSliderMotionAdapter motionListener) {
			super();
			this.slider = slider;
			this.motionListener = motionListener;
		}

		public void mouseClicked(final MouseEvent e) {
			final Point clickedAt = e.getPoint();
			if (this.leftDArrow.contains(clickedAt)) {
				this.slider.unitIncrementLeft();
			}
			else if (this.rightIArrow.contains(clickedAt)) {
				this.slider.unitIncrementRight();
			}
			else if (this.leftIArrow.contains(clickedAt)) {
				this.slider.unitDecrementLeft();
			}
			else if (this.rightDArrow.contains(clickedAt)) {
				this.slider.unitDecrementRight();
			}
			else if (this.leftBlock.contains(clickedAt)) {
				this.slider.blockIncrementLeft();
			}
			else if (this.rightBlock.contains(clickedAt)) {
				this.slider.blockIncrementRight();
			}
		}

		public void mousePressed(final MouseEvent e) {
			final Point pressedAt = e.getPoint();
			if (this.leftThumb.contains(pressedAt)) {
				this.motionListener.setLeftThumbDragMode(true);
			}
			else if (this.rightThumb.contains(pressedAt)) {
				this.motionListener.setRightThumbDragMode(true);
			}
			else if (this.midBlock.contains(pressedAt)) {
				if (TMRangeSlider.this.orientation == Adjustable.HORIZONTAL) {
					this.motionListener.setMidDragMode(true, e.getX());
				}
				else {
					this.motionListener.setMidDragMode(true, e.getY());
				}
			}

		}

		public void mouseReleased(final MouseEvent e) {
			this.motionListener.setLeftThumbDragMode(false);
			this.motionListener.setRightThumbDragMode(false);
			if (TMRangeSlider.this.orientation == Adjustable.HORIZONTAL) {
				this.motionListener.setMidDragMode(false, e.getX());
			}
			else {
				this.motionListener.setMidDragMode(false, e.getY());
			}

		}

		public void setLeftBlock(final Rectangle r) {
			this.leftBlock = r;
		}

		public void setLeftDArrow(final Polygon p) {
			this.leftDArrow = p;
		}

		public void setLeftIArrow(final Polygon p) {
			this.leftIArrow = p;
		}

		public void setLeftThumb(final Rectangle r) {
			this.leftThumb = r;
		}

		public void setMidBlock(final Rectangle r) {
			this.midBlock = r;
		}

		public void setRightBlock(final Rectangle r) {
			this.rightBlock = r;
		}

		public void setRightDArrow(final Polygon p) {
			this.rightDArrow = p;
		}

		public void setRightIArrow(final Polygon p) {
			this.rightIArrow = p;
		}

		public void setRightThumb(final Rectangle r) {
			this.rightThumb = r;
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3683413294785810736L;

	// PUBLIC VARIABLES
	/** @var RIGHT_THUMB signifies that the right thumb moved
	 *  @var LEFT_THUMB  signifies that the left thumb moved */
	public final static int RIGHT_THUMB = 1;
	public final static int LEFT_THUMB = -1;
	AdjustmentListener aListener;

	boolean rightMoved, leftMoved, rightValueChanged, leftValueChanged,
			restrictedDrag;

	int width, height, lastThumbChanged, inset = 3, leftValue, rightValue,
			leftPosition, rightPosition, thumbField, thumbWidth, thumbRangeMin,
			thumbRangeMax, thumbRange, actualRangeMin, actualRangeMax,
			actualRange, unitIncrement, blockIncrement, orientation,
			m_arrow_length;
	// Modification added by Laurent. In order to display the bounding boxes
	// of range sliders, I added some extra integer values to remember the
	// positions:
	int rightBoxPosition, leftBoxPosition, leftBoxValue, rightBoxValue;
	static int basicWidth = 5;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/

	RangeSliderMotionAdapter motionListener;

	RangeSliderMouseAdapter mouseListener;

	/********************
	 * PUBLIC FUNCTIONS *
	 ********************/

	/** Constructor for the Range Slider. Creates a HORIZONTAL range slider.
	  @param rangeMin integer value for your minimum range value
	  @param rangeMax integer value for your maximum range value
	  @param lowerValue integer value for your initial value of the left thumb
	  @param upperValue integer value for your initial value of the right thumb*/

	public TMRangeSlider(
		final int rangeMin,
		final int rangeMax,
		final int lowerValue,
		final int upperValue) {
		super(BorderStyle.RIDGED);
		this.width = 100;
		this.height = 20;
		this.rightMoved = this.leftMoved = this.restrictedDrag = false;
		this.rightValueChanged = this.leftValueChanged = true;

		this.actualRangeMin = rangeMin;
		this.actualRangeMax = rangeMax;
		this.leftValue = lowerValue;
		this.rightValue = upperValue;
		this.lastThumbChanged = 0;

		this.rangeSetup();
		this.generalSetup();
		this.orientation = Adjustable.HORIZONTAL;
	}

	/** 
	 * Constructor which allows to create either a vertical or a horizontal 
	 * range slider.
	 */
	public TMRangeSlider(
		final int range_min,
		final int range_max,
		final int low_value,
		final int up_value,
		final int orientation) {
		super(BorderStyle.RIDGED);
		this.width = 100;
		this.height = 20;
		this.rightMoved = this.leftMoved = this.restrictedDrag = false;
		this.rightValueChanged = this.leftValueChanged = true;

		this.actualRangeMin = range_min;
		this.actualRangeMax = range_max;
		this.leftValue = low_value;
		this.rightValue = up_value;
		this.lastThumbChanged = 0;

		this.rangeSetup();
		this.generalSetup();
		this.orientation = Adjustable.HORIZONTAL;
		if (orientation == Adjustable.VERTICAL) {
			this.width = 20;
			this.height = 100;
			this.orientation = Adjustable.VERTICAL;
		}
	}

	/** Look at ScrollBar or Adjustable interface for definition.*/
	public synchronized void addAdjustmentListener(final AdjustmentListener l) {
		this.aListener = l;
	}

	private void adjustBoxPosition() {
		//    if (rightValueChanged) 
		this.rightBoxPosition =
			(int) ((double) (this.rightBoxValue - this.actualRangeMin)
					/ this.actualRange * this.thumbRange)
					+ this.thumbRangeMin;
		// if (leftValueChanged)
		this.leftBoxPosition =
			(int) ((double) (this.leftBoxValue - this.actualRangeMin)
					/ this.actualRange * this.thumbRange)
					+ this.thumbRangeMin;
		//    rightValueChanged = leftValueChanged = false;

		if (this.orientation == Adjustable.HORIZONTAL) {
			if (this.rightBoxPosition > this.thumbRangeMax) {
				this.rightBoxPosition = this.thumbRangeMax;
			}
			if (this.leftBoxPosition < this.thumbRangeMin) {
				this.leftBoxPosition = this.thumbRangeMin;
			}
		}
		else {
			if (this.rightBoxPosition < this.thumbRangeMax) {
				this.rightBoxPosition = this.thumbRangeMax;
			}
			if (this.leftBoxPosition > this.thumbRangeMin) {
				this.leftBoxPosition = this.thumbRangeMin;
			}
		}
	}

	private void adjustBoxValue() {
		/*    int pRightV = rightValue;
		int pLeftV = leftValue;

		if (rightMoved) {
		  rightValue = (int)Math.round(((double)(rightPosition - thumbRangeMin)/thumbRange)*actualRange) + actualRangeMin;
		  rightValueChanged = true;
		}
		if (leftMoved) {
		  leftValue =(int)Math.round(((double)(leftPosition - thumbRangeMin)/thumbRange)*actualRange) +actualRangeMin;
		  leftValueChanged = true;
		}*/

		this.adjustBoxPosition();
	}

	/** Function called to re-adjust the position values after the range values of the function have been changed.*/
	private void adjustPosition() {
		/*if (rightValue == leftValue) {
		  if (lastThumbChanged == RIGHT_THUMB)
		rightValue+=1;
		  else
		leftValue-=1;
		}*/
		//System.out.println("Right Value "+rightValue);
		//System.out.println("Left Value "+leftValue);
		// Want them to go together so that it would also act as an item slider
		if (this.rightValueChanged) {
			this.rightPosition =
				(int) ((double) (this.rightValue - this.actualRangeMin)
						/ this.actualRange * this.thumbRange)
						+ this.thumbRangeMin;
		}
		if (this.leftValueChanged) {
			//      leftPosition = (int)(((double)(leftValue - actualRangeMin)/actualRange)*thumbRange) + thumbRangeMin -thumbWidth;
			this.leftPosition =
				(int) ((double) (this.leftValue - this.actualRangeMin)
						/ this.actualRange * this.thumbRange)
						+ this.thumbRangeMin;
		}
		this.rightValueChanged = this.leftValueChanged = false;
		//System.out.println("LP "+leftPosition);
		if (this.orientation == Adjustable.HORIZONTAL) {
			if (this.rightPosition > this.thumbRangeMax) {
				this.rightPosition = this.thumbRangeMax;
			}
			if (this.leftPosition < this.thumbRangeMin) {
				this.leftPosition = this.thumbRangeMin;
			}
		}
		else {
			if (this.rightPosition < this.thumbRangeMax) {
				this.rightPosition = this.thumbRangeMax;
			}
			if (this.leftPosition > this.thumbRangeMin) {
				this.leftPosition = this.thumbRangeMin;
			}
		}
		//repaint();
		//System.out.println("RP "+rightPosition);
	}

	/** Function called to re-adjust the range values after the position values of the function have been changed.*/
	private void adjustValue() {
		final int pRightV = this.rightValue;
		final int pLeftV = this.leftValue;
		// The next two variables are in case of a change of value without moving
		// the thumbs.
		final boolean rightChange = this.rightValueChanged, leftChange =
			this.leftValueChanged;

		//AGAIN the following is needed for doing changes if value changes without 
		// movement of thumbs (e.g. changing value from inside the aplication)
		if (rightChange) {
			this.lastThumbChanged = TMRangeSlider.RIGHT_THUMB;
		}
		if (leftChange) {
			this.lastThumbChanged = TMRangeSlider.LEFT_THUMB;
		}

		if (this.rightMoved) {
			this.rightValue =
				(int) Math
					.round((double) (this.rightPosition - this.thumbRangeMin)
							/ this.thumbRange * this.actualRange)
						+ this.actualRangeMin;
			this.rightValueChanged = true;
		}
		if (this.leftMoved) {
			this.leftValue =
				(int) Math
					.round((double) (this.leftPosition - this.thumbRangeMin)
							/ this.thumbRange * this.actualRange)
						+ this.actualRangeMin;
			this.leftValueChanged = true;
		}
		this.rightMoved = this.leftMoved = false;
		/* Since sometimes when the range is large and you pull the two thumbs
		   together even a difference of one will yield the same position. But
		   since we always want to keep at least a difference of one between 
		   the thumbs, the following will take care of that. */

		// Since we want them to act as an item slider, next is commented out
		/* if (rightValue == leftValue) {
		  if (lastThumbChanged==RIGHT_THUMB)
		rightValue+=1;
		  else
		leftValue-=1;
		}*/
		this.adjustPosition();

		if (this.aListener != null) {
			if (pRightV != this.rightValue || rightChange) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED,
					this.rightValue));
			}
			if (pLeftV != this.leftValue || leftChange) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED,
					this.leftValue));
			}
		}
		//     System.out.println("Right Value : "+rightValue);
		//     System.out.println("Left Value : "+leftValue);
	}

	/** Function increments the value of the left thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void blockIncrementLeft() {
		this.lastThumbChanged = TMRangeSlider.LEFT_THUMB;
		if (this.leftValue - this.blockIncrement >= this.actualRangeMin) {
			this.leftValue -= this.blockIncrement;
		}
		else {
			this.leftValue = this.actualRangeMin;
		}
		if (this.aListener != null) {
			this.aListener.adjustmentValueChanged(new AdjustmentEvent(
				this,
				AdjustmentEvent.ADJUSTMENT_FIRST,
				AdjustmentEvent.BLOCK_INCREMENT,
				this.leftValue));
		}
		this.leftValueChanged = true;
		this.refresh();
	}

	/** Function checks and calculates all the base values used throughout
	 * the class. */
	/** Function block increments the value of the right thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void blockIncrementRight() {
		this.lastThumbChanged = TMRangeSlider.RIGHT_THUMB;
		if (this.rightValue + this.blockIncrement <= this.actualRangeMax) {
			this.rightValue += this.blockIncrement;
			if (this.aListener != null) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.BLOCK_INCREMENT,
					this.rightValue));
			}
		}
		else {
			this.rightValue = this.actualRangeMax;
		}
		this.rightValueChanged = true;
		this.refresh();
	}

	/** Function responsible for handling the Draging of the Bar. Was not quite working as of  3/3/98. ***** SHOULD BE FIXED *****. Again a listener calls this function so YOU DON'T CALL THIS FUNCTION YOURSELF.*/
	private void dragBar(int initialPos, final int drag) {
		int oldValueRight, oldValueLeft;
		oldValueRight = this.rightValue;
		oldValueLeft = this.leftValue;
		if (this.orientation == Adjustable.HORIZONTAL) {
			if (initialPos > this.thumbRangeMax) {
				initialPos = this.thumbRangeMax;
			}
			else if (initialPos < this.thumbRangeMin) {
				initialPos = this.thumbRangeMin;
			}
			if (drag > initialPos) {
				int dragAmount = drag - initialPos;
				if (this.rightPosition >= this.thumbRangeMax) {
					this.restrictedDrag = true;
				}
				if (!this.restrictedDrag) {
					if (this.rightPosition + dragAmount >= this.thumbRangeMax) {
						dragAmount = this.thumbRangeMax - this.rightPosition;
					}
					this.dragRightThumb(this.rightPosition + dragAmount);
					if (this.rightValue != oldValueRight) {
						final int difference = this.rightValue - oldValueRight;
						this.leftValue += difference;
						this.leftValueChanged = true;
						this.motionListener.setInitialPos(drag);
						this.refresh();
						//dragLeftThumb(leftPosition +dragAmount);
					}
				}
			}
			else {
				int dragAmount = initialPos - drag;
				if (this.leftPosition <= this.thumbRangeMin) {
					this.restrictedDrag = true;
				}
				if (!this.restrictedDrag) {
					if (this.leftPosition - dragAmount <= this.thumbRangeMin) {
						dragAmount = this.leftPosition - this.thumbRangeMin;
					}
					this.dragLeftThumb(this.leftPosition - dragAmount);
					if (this.leftValue != oldValueLeft) {
						final int difference = oldValueLeft - this.leftValue;
						this.rightValue -= difference;
						this.rightValueChanged = true;
						this.motionListener.setInitialPos(drag);
						this.refresh();
						//dragRightThumb(rightPosition - dragAmount);
					}
				}
			}
		}
		else {
			if (initialPos < this.thumbRangeMax) {
				initialPos = this.thumbRangeMax;
			}
			else if (initialPos > this.thumbRangeMin) {
				initialPos = this.thumbRangeMin;
			}
			if (drag > initialPos) {
				int dragAmount = drag - initialPos;
				if (this.leftPosition >= this.thumbRangeMin) {
					this.restrictedDrag = true;
				}
				if (!this.restrictedDrag) {
					if (this.leftPosition + dragAmount >= this.thumbRangeMin) {
						dragAmount = this.thumbRangeMin - this.leftPosition;
					}
					this.dragLeftThumb(this.leftPosition + dragAmount);
					if (this.leftValue != oldValueLeft) {
						final int difference = oldValueLeft - this.leftValue;
						this.rightValue -= difference;
						this.rightValueChanged = true;
						this.motionListener.setInitialPos(drag);
						this.refresh();
						//dragLeftThumb(leftPosition +dragAmount);
					}
				}
			}
			else {
				int dragAmount = initialPos - drag;
				if (this.rightPosition <= this.thumbRangeMax) {
					this.restrictedDrag = true;
				}
				if (!this.restrictedDrag) {
					if (this.rightPosition - dragAmount <= this.thumbRangeMax) {
						dragAmount = this.rightPosition - this.thumbRangeMax;
					}
					this.dragRightThumb(this.rightPosition - dragAmount);
					if (this.rightValue != oldValueRight) {
						final int difference = this.rightValue - oldValueRight;
						this.leftValue += difference;
						this.leftValueChanged = true;
						this.motionListener.setInitialPos(drag);
						this.refresh();
						//dragRightThumb(rightPosition - dragAmount);
					}
				}
			}
		}
		this.restrictedDrag = false;

		if (this.aListener != null) {
			this.aListener.adjustmentValueChanged(new AdjustmentEvent(
				this,
				AdjustmentEvent.ADJUSTMENT_FIRST,
				AdjustmentEvent.TRACK,
				this.rightValue));
		}

	}

	/** Function responsible for handling the Drag Left Event. Again a listener calls this function so YOU DON'T CALL THIS FUNCTION YOURSELF.*/
	private void dragLeftThumb(final int drag) {
		this.lastThumbChanged = TMRangeSlider.LEFT_THUMB;
		//   int correctionToDrag = drag+thumbWidth;
		final int correctionToDrag = drag;
		if (this.orientation == Adjustable.HORIZONTAL) {
			if (drag >= this.thumbRangeMin
					&& correctionToDrag < this.rightPosition) {
				this.leftPosition = drag;
			}
			else if (correctionToDrag >= this.rightPosition) {
				//leftPosition = rightPosition - thumbWidth;
				this.leftPosition = this.rightPosition;
			}
			else {
				this.leftPosition = this.thumbRangeMin;
			}
		}
		else {
			if (drag <= this.thumbRangeMin && drag > this.rightPosition) {
				this.leftPosition = drag;
			}
			else if (drag <= this.rightPosition) {
				this.leftPosition = this.rightPosition;
			}
			else {
				this.leftPosition = this.thumbRangeMin;
			}
		}
		this.leftMoved = true;
		this.refresh();
	}

	/** Function responsible for handling the Drag Right Event. Again a listener calls this function so YOU DON'T CALL THIS FUNCTION YOURSELF.*/
	private void dragRightThumb(final int drag) {
		this.lastThumbChanged = TMRangeSlider.RIGHT_THUMB;
		if (this.orientation == Adjustable.HORIZONTAL) {
			if (drag <= this.thumbRangeMax && drag > this.leftPosition) {
				this.rightPosition = drag;
			}
			else if (drag <= this.leftPosition) {
				this.rightPosition = this.leftPosition;
			}
			else {
				this.rightPosition = this.thumbRangeMax;
			}
		}
		else {
			if (drag >= this.thumbRangeMax && drag < this.leftPosition) {
				this.rightPosition = drag;
			}
			else if (drag >= this.leftPosition) {
				this.rightPosition = this.leftPosition;
			}
			else {
				this.rightPosition = this.thumbRangeMax;
			}
		}
		this.rightMoved = true;
		this.refresh();
	}

	/** This function draws the arrows on the Range Slider and gives
	  them their 3D look. **/
	private void drawHorizontalArrows(final Graphics g) {
		final int xpoints[] = new int[3], ypoints[] = new int[3];

		// Drawing Background
		g.setColor(new Color(150, 150, 150));
		//g.setColor((Color.green).darker());
		g.fillRect(
			this.inset - 1,
			this.inset - 1,
			this.width - this.inset + 1,
			this.height - 1);
		//g.fillRect(0, 0,width, height);

		// Left Arrow Decrement
		// Set up the points 
		xpoints[0] = xpoints[1] = this.m_arrow_length;
		xpoints[2] = this.inset;
		ypoints[0] = this.inset;
		ypoints[1] = this.height;
		ypoints[2] = this.height / 2;
		// Drawing it
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setLeftDArrow(new Polygon(xpoints, ypoints, 3));

		// Giving the arrow a 3D look
		g.setColor(Color.white);
		//Top
		g.drawLine(xpoints[2], ypoints[2], xpoints[0], ypoints[0]);
		g.drawLine(xpoints[2] + 1, ypoints[2], xpoints[0], ypoints[0] + 1);
		g.setColor(new Color(128, 128, 128));
		//Bottom
		g.drawLine(xpoints[2] + 1, ypoints[2] + 1, xpoints[1], ypoints[1]);
		g.drawLine(xpoints[2] + 2, ypoints[2] + 1, xpoints[1], ypoints[1] - 1);
		//Side
		g.drawLine(xpoints[0], ypoints[0] + 1, xpoints[1], ypoints[1] - 1);
		g.drawLine(
			xpoints[0] - 1,
			ypoints[0] + 2,
			xpoints[1] - 1,
			ypoints[1] - 2);

		//Left Arrow Increment
		//Set up the points
		xpoints[0] = xpoints[1] = this.m_arrow_length + 1;
		xpoints[2] = 2 * this.m_arrow_length + 1;
		ypoints[0] = this.inset;
		ypoints[1] = this.height;
		ypoints[2] = this.height / 2;
		// Drawing it
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setLeftIArrow(new Polygon(xpoints, ypoints, 3));
		// Giving the arrow a 3D look
		g.setColor(Color.white);
		//Top && Side
		g.drawLine(xpoints[0], ypoints[0], xpoints[2], ypoints[2]);
		g.drawLine(xpoints[0], ypoints[0] + 1, xpoints[2] - 1, ypoints[2]);

		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
		g.drawLine(
			xpoints[0] + 1,
			ypoints[0] + 1,
			xpoints[1] + 1,
			ypoints[1] - 1);
		//Bottom
		g.drawLine(xpoints[1] + 2, ypoints[1] - 1, xpoints[2], ypoints[2]);
		g.drawLine(xpoints[1] + 2, ypoints[1] - 2, xpoints[2] - 1, ypoints[2]);

		//RIGHT ARROW Decrement
		// Setting up points
		xpoints[0] = xpoints[1] = this.width - this.m_arrow_length - 1;
		xpoints[2] = this.width - 2 * this.m_arrow_length - 1;
		ypoints[0] = this.inset;
		ypoints[1] = this.height;
		ypoints[2] = this.height / 2;
		// Drawing the Arrow
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setRightDArrow(new Polygon(xpoints, ypoints, 3));
		// Giving the arrow a 3D look
		g.setColor(Color.white);
		//Top
		g.drawLine(xpoints[2], ypoints[2], xpoints[0], ypoints[0]);
		g.drawLine(xpoints[2] + 1, ypoints[2], xpoints[0], ypoints[0] + 1);
		g.setColor(new Color(128, 128, 128));
		//Bottom
		g.drawLine(xpoints[2] + 1, ypoints[2] + 1, xpoints[1], ypoints[1]);
		g.drawLine(xpoints[2] + 2, ypoints[2] + 1, xpoints[1], ypoints[1] - 1);
		//Side
		g.drawLine(xpoints[0], ypoints[0] + 1, xpoints[1], ypoints[1] - 1);
		g.drawLine(
			xpoints[0] - 1,
			ypoints[0] + 2,
			xpoints[1] - 1,
			ypoints[1] - 2);

		//RIGHT ARROW Increment
		// Setting up points
		xpoints[0] = xpoints[1] = this.width - this.m_arrow_length;
		xpoints[2] = this.width;
		ypoints[0] = this.inset;
		ypoints[1] = this.height;
		ypoints[2] = this.height / 2;
		// Drawing the Arrow
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setRightIArrow(new Polygon(xpoints, ypoints, 3));

		// Giving the arrow a 3D look
		g.setColor(Color.white);
		//Top && Side
		g.drawLine(xpoints[0], ypoints[0], xpoints[2], ypoints[2]);
		g.drawLine(xpoints[0], ypoints[0] + 1, xpoints[2] - 1, ypoints[2]);
		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
		g.drawLine(
			xpoints[0] + 1,
			ypoints[0] + 1,
			xpoints[1] + 1,
			ypoints[1] - 1);
		//Bottom
		g.drawLine(xpoints[1] + 2, ypoints[1] - 1, xpoints[2], ypoints[2]);
		g.drawLine(xpoints[1] + 2, ypoints[1] - 2, xpoints[2] - 1, ypoints[2]);
	}

	/** This function is responsible for the drawing of the thumbs and
	 *  the area between them. */
	private void drawHorizontalThumbs(final Graphics g) {
		g.setColor(new Color(150, 150, 150));
		//    g.setColor((Color.green).darker());
		//g.fillRect(2*m_arrow_length+1, inset - 2, width - 4*m_arrow_length -1, height);
		// Instead of clearing the whole range just clean outide the thumbs
		g.fillRect(
			2 * this.m_arrow_length + 1,
			this.inset - 2,
			this.leftPosition - this.thumbWidth - 2 * this.m_arrow_length - 1,
			this.height);
		g.fillRect(
			this.rightPosition + this.thumbWidth,
			this.inset - 2,
			this.width - 2 * this.m_arrow_length - 1 - this.rightPosition
					- this.thumbWidth,
			this.height);
		g.drawLine(this.leftPosition, 1, this.rightPosition, 1);
		g.drawLine(
			this.leftPosition,
			this.height,
			this.rightPosition,
			this.height);
		g.setColor(Color.lightGray);

		if (this.leftPosition == this.rightPosition) {
			//Left Thumb
			g.fill3DRect(
				this.leftPosition - this.thumbWidth,
				1,
				this.thumbWidth,
				this.height / 2,
				true);
			this.mouseListener.setLeftThumb(new Rectangle(
				this.leftPosition - this.thumbWidth,
				1,
				this.thumbWidth,
				(this.height - 1) / 2));

			//Right thumb
			g.fill3DRect(
				this.rightPosition,
				1,
				this.thumbWidth,
				this.height / 2,
				true);
			this.mouseListener.setRightThumb(new Rectangle(
				this.rightPosition,
				1,
				this.thumbWidth,
				(this.height - 1) / 2));

			// Tie
			g.fill3DRect(
				this.leftPosition - this.thumbWidth,
				this.height / 2 + 1,
				2 * this.thumbWidth,
				this.height / 2,
				true);
			this.mouseListener.setMidBlock(new Rectangle(
				this.leftPosition - this.thumbWidth,
				this.height / 2,
				2 * this.thumbWidth,
				this.height / 2));
		}
		else {
			// Left Thumb
			g.fill3DRect(
				this.leftPosition - this.thumbWidth,
				1,
				this.thumbWidth,
				this.height - 1,
				true);
			//g.fill3DRect(leftPosition, 1, thumbWidth, height, true);
			this.mouseListener.setLeftThumb(new Rectangle(this.leftPosition
					- this.thumbWidth, 1, this.thumbWidth, this.height - 1));

			// Right Thumb
			g.fill3DRect(
				this.rightPosition,
				1,
				this.thumbWidth,
				this.height - 1,
				true);
			this.mouseListener.setRightThumb(new Rectangle(
				this.rightPosition,
				1,
				this.thumbWidth,
				this.height - 1));

			// Block in between
			// Modifications added by Laurent in order to display the bounding boxes
			// of the different sliders on the filtering panel.
			g.setColor(new Color(197, 197, 197));
			g.fillRect(this.leftPosition + 1, 2, this.rightPosition
					- this.leftPosition - 1, this.height - 2);
			// First, let's fill the inside bar with a dark yellow:
			/*      g.setColor(Color.yellow);
			g.fillRect(leftBoxPosition+1, 2, rightBoxPosition-leftBoxPosition-1, height-2);

			// Now, let's fill the rest of the inside bar with a light yellow:
			g.setColor(Color.yellow);
			g.fillRect(leftPosition+1, 2, leftBoxPosition-leftPosition-1, height-2);
			g.fillRect(rightBoxPosition+1, 2, rightPosition-rightBoxPosition-1, height-2);*/

			g.setColor(Color.gray);

			// Setting the Block for the Block Increments	
			this.mouseListener.setLeftBlock(new Rectangle(
				this.thumbRangeMin,
				this.inset,
				this.leftPosition - this.thumbRangeMin,
				this.height - this.inset));
			final int pos = this.rightPosition + this.thumbWidth + 1;
			this.mouseListener.setRightBlock(new Rectangle(
				pos,
				this.inset,
				this.thumbRangeMax - pos,
				this.height - this.inset));

			// Setting the Block between the Two Thumbs
			this.mouseListener.setMidBlock(new Rectangle(this.leftPosition
					+ this.thumbWidth, this.inset, this.rightPosition
					- this.leftPosition - this.thumbWidth, this.height
					- this.inset));
		}

	}

	private void drawVerticalArrows(final Graphics g) {
		final int height = this.height;
		final int xpoints[] = new int[3];
		final int ypoints[] = new int[3];

		//Drawing background
		g.setColor(new Color(150, 150, 150));
		g.fillRect(
			this.inset - 1,
			this.inset - 1,
			this.width - this.inset + 1,
			height - 1);

		// Bottom arrow decrement
		// set up points
		xpoints[0] = this.width / 2 - 1;
		xpoints[1] = this.inset;
		xpoints[2] = this.width - this.inset - 1;
		ypoints[0] = height;
		ypoints[1] = ypoints[2] = height - this.m_arrow_length;
		// draw arrow
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		// register with the mouse listener
		this.mouseListener.setLeftDArrow(new Polygon(xpoints, ypoints, 3));
		//GIVE ARROW 3D LOOK
		g.setColor(Color.white);
		g.drawLine(xpoints[1], ypoints[1], xpoints[2] - 1, ypoints[2]);
		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[2], ypoints[2], xpoints[0], ypoints[0]);
		g.drawLine(xpoints[1] + 1, ypoints[1] + 1, xpoints[0], ypoints[0]);

		// Bottom arrow increment  
		xpoints[0] = xpoints[1];
		xpoints[1] = xpoints[2];
		xpoints[2] = this.width / 2 - 1;
		ypoints[0] = ypoints[1] -= 1;
		ypoints[2] = ypoints[0] - this.m_arrow_length;
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setLeftIArrow(new Polygon(xpoints, ypoints, 3));
		g.setColor(Color.white);
		g.drawLine(xpoints[0], ypoints[0], xpoints[2], ypoints[2]);
		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
		g.drawLine(xpoints[1], ypoints[1], xpoints[2] + 1, ypoints[2] - 1);

		// Upper arrow increment 
		xpoints[0] = this.width / 2 - 1;
		xpoints[1] = this.inset;
		xpoints[2] = this.width - this.inset - 1;
		ypoints[0] = this.inset;
		ypoints[1] = ypoints[2] = this.inset + this.m_arrow_length;
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setRightIArrow(new Polygon(xpoints, ypoints, 3));
		g.setColor(Color.white);
		g.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[0] + 1, ypoints[0] + 1, xpoints[2], ypoints[2]);
		g.drawLine(xpoints[1] + 1, ypoints[1], xpoints[2], ypoints[2]);

		// Upper arrow decrement
		xpoints[0] = xpoints[1];
		xpoints[1] = xpoints[2];
		xpoints[2] = this.width / 2 - 1;
		ypoints[0] = ypoints[1] += 1;
		ypoints[2] = ypoints[2] + 1 + this.m_arrow_length;
		g.setColor(Color.lightGray);
		g.fillPolygon(xpoints, ypoints, 3);
		this.mouseListener.setRightDArrow(new Polygon(xpoints, ypoints, 3));
		g.setColor(Color.white);
		g.drawLine(xpoints[0], ypoints[0], xpoints[1] - 1, ypoints[1]);
		g.setColor(new Color(128, 128, 128));
		g.drawLine(xpoints[1], ypoints[1], xpoints[2], ypoints[2]);
		g.drawLine(xpoints[0] + 1, ypoints[0] + 1, xpoints[2], ypoints[2]);

	}

	private void drawVerticalThumbs(final Graphics g) {
		g.setColor(new Color(150, 150, 150));

		//g.fillRect(inset, 2*m_arrow_length+3, width-inset, height - 4*m_arrow_length -3);
		g.fillRect(this.inset, 2 * this.m_arrow_length + 3, this.width
				- this.inset, this.rightPosition - this.thumbWidth - this.inset
				- 2 * this.m_arrow_length - 1);

		g.fillRect(this.inset, this.leftPosition + this.thumbWidth, this.width
				- this.inset, this.height - 2 * this.m_arrow_length
				- this.leftPosition - this.thumbWidth);

		g.drawLine(
			this.inset,
			this.leftPosition,
			this.inset,
			this.rightPosition);

		g.drawLine(
			this.width,
			this.leftPosition,
			this.width,
			this.rightPosition);

		g.setColor(Color.lightGray);

		if (this.leftPosition == this.rightPosition) {
			//Bottom Thumb
			g.fill3DRect(
				this.inset,
				this.leftPosition,
				(this.width - this.inset) / 2,
				this.thumbWidth,
				true);
			this.mouseListener.setLeftThumb(new Rectangle(
				this.inset,
				this.leftPosition,
				(this.width - this.inset - 1) / 2,
				this.thumbWidth));
			// Upper Thumb
			g.fill3DRect(
				this.inset,
				this.rightPosition - this.thumbWidth,
				(this.width - this.inset) / 2,
				this.thumbWidth,
				true);
			this.mouseListener.setRightThumb(new Rectangle(
				this.inset,
				this.rightPosition - this.thumbWidth,
				(this.width - this.inset - 1) / 2,
				this.thumbWidth));

			// Tie
			g.fill3DRect(
				this.inset + (this.width - this.inset) / 2,
				this.rightPosition - this.thumbWidth,
				(this.width - this.inset) / 2,
				2 * this.thumbWidth,
				true);
			this.mouseListener.setMidBlock(new Rectangle(
				this.inset + (this.width - this.inset) / 2,
				this.rightPosition - this.thumbWidth,
				(this.width - this.inset) / 2,
				2 * this.thumbWidth));
		}
		else {
			//Bottom Thumb
			g.fill3DRect(
				this.inset,
				this.leftPosition,
				this.width - this.inset,
				this.thumbWidth,
				true);
			this.mouseListener.setLeftThumb(new Rectangle(
				this.inset,
				this.leftPosition,
				this.width - this.inset,
				this.thumbWidth));

			// Upper Thumb
			g.fill3DRect(
				this.inset,
				this.rightPosition - this.thumbWidth,
				this.width - this.inset,
				this.thumbWidth,
				true);
			this.mouseListener.setRightThumb(new Rectangle(
				this.inset,
				this.rightPosition - this.thumbWidth,
				this.width - this.inset,
				this.thumbWidth));

			// Block in between
			// Modifications added by Laurent in order to display the bounding boxes
			// of the different sliders on the filtering panel.
			g.setColor(new Color(197, 197, 197));
			g.fillRect(this.inset + 1, this.rightPosition, this.width
					- this.inset - 3, this.leftPosition - this.rightPosition
					- 1);
			// First, let's fill the inside bar with a dark yellow:
			/*    g.setColor(Color.yellow);
			g.fillRect(inset+1, rightBoxPosition, width-inset-3, leftBoxPosition-rightBoxPosition-1);

			// Now, let's draw the top and the bottom of the inside bar with
			// a light yellow:
			g.setColor(Color.yellow);
			g.fillRect(inset+1, rightPosition, width-inset-3, rightBoxPosition-rightPosition-1);
			g.fillRect(inset+1, leftBoxPosition, width-inset-3, leftPosition-leftBoxPosition-1);*/

			// Setting the blocks for block increments and the mid block for dragging
			// Setting the space between the bottom arrow and the bottom thumb.
			this.mouseListener.setLeftBlock(new Rectangle(
				this.inset,
				this.leftPosition + this.thumbWidth + 1,
				this.width - this.inset,
				this.leftPosition - this.thumbRangeMin));
			// Setting the space between the top arrows and the top thumb
			this.mouseListener.setRightBlock(new Rectangle(
				this.inset,
				this.thumbRangeMax - this.thumbWidth,
				this.width - this.inset,
				this.thumbRangeMax - this.rightPosition));

			// Setting the Block between the Two Thumbs
			this.mouseListener.setMidBlock(new Rectangle(
				this.inset + 1,
				this.rightPosition + 1,
				this.width - this.inset,
				this.leftPosition - this.rightPosition - 1));
		}
	}

	private void estimateArrowLength() {
		if (this.orientation == Adjustable.HORIZONTAL) {
			this.m_arrow_length = (int) Math.round(0.075 * this.width);
		}
		else {
			this.m_arrow_length = (int) Math.round(0.075 * this.height);
		}

		//MAKING SURE THAT THE ARROW SIZE IS NOT BIGGER THEN 20 pixels in length  
		this.m_arrow_length = Math.min(this.m_arrow_length, 20);
	}

	private void generalSetup() {
		this.thumbRangeMin = this.thumbRangeMax = this.thumbRange = 0;
		this.unitIncrement = 1;
		this.blockIncrement = 10;
		this.thumbWidth = TMRangeSlider.basicWidth;

		this.motionListener = new RangeSliderMotionAdapter(this);
		this.mouseListener =
			new RangeSliderMouseAdapter(this, this.motionListener);
		this.addMouseListener(this.mouseListener);
		this.addMouseMotionListener(this.motionListener);
	}

	/** Gets the block increment for this range slider.*/
	public int getBlockIncrement() {
		return this.blockIncrement;
	}

	/** The following function returns the value of the last thumb changed. You can check which thumb was moved by checking this value against the constant variables <bold>LEFT_THUMB</bold>, <bold>RIGHT_THUMB</bold>. Initially the value will be neither of t
	he two variables. */
	public int getChangedThumb() {
		return this.lastThumbChanged;
	}

	/** Same as getInsets in the Component class. */
	public Insets getInsets() {
		return new Insets(this.inset, this.inset, this.inset, this.inset);
	}

	/** Gets the value of left thumb for this range slider.*/
	public int getLeftValue() {
		return this.leftValue;
	}

	/** Gets the maximum value for this range slider.*/
	public int getMaximum() {
		return this.actualRangeMax;
	}

	/** Gets the minimum value for this range slider.*/
	public int getMinimum() {
		return this.actualRangeMin;
	}

	/** Same as getMinimumSize() in the Component class. */
	public Dimension getMinimumSize() {
		int w, h;

		w = this.width + this.inset * 2;
		h = this.height + this.inset * 2;
		if (w > this.getSize().width) {
			this.setSize(w, h);
		}
		return new Dimension(w, h);
	}

	/** Dummy function does not do anythuing.*/
	public int getOrientation() {
		return 0;
	}

	/** Same as getPreferedSize() in the Component class. */
	public Dimension getPreferedSize() {
		final Dimension min = this.getMinimumSize();
		final Dimension cur = this.getSize();
		return new Dimension(Math.max(min.width, cur.width), Math.max(
			min.height,
			cur.height));
	}

	/** Gets the value of the right thumb for this range slider.*/
	public int getRightValue() {
		return this.rightValue;
	}

	/** Gets the unit increment for this range slider.*/
	public int getUnitIncrement() {
		return this.unitIncrement;
	}

	/** Dummy function does not do anything.*/
	public int getValue() {
		return 0;
	}

	/*********************
	 * PRIVATE FUNCTIONS *
	 *********************/

	/** Dummy function does not do anythuing.*/
	public int getVisibleAmount() {
		return 0;
	}

	private void graphicsInit(final Dimension new_size) {
		if (new_size.width != this.width || new_size.height != this.height) {
			this.height = new_size.height - this.inset;
			this.width = new_size.width - this.inset;

			this.estimateArrowLength();

			/*int range_length = 0;
			if (this.orientation == VERTICAL) 
			range_length = height - 4*m_arrow_length;
			else
			range_length = width - 4*m_arrow_length;
			if (range_length > actualRange) {
			int difference = range_length - actualRange;
			thumbWidth = Math.max(thumbWidth, (int)difference/4);
			}*/

			if (this.orientation == Adjustable.VERTICAL) {
				this.thumbRangeMin =
					this.height - 2 * this.m_arrow_length - this.thumbWidth;
				this.thumbRangeMax =
					2 * this.m_arrow_length + this.thumbWidth + 3;
			}
			else {
				this.thumbRangeMin =
					2 * this.m_arrow_length + this.thumbWidth + 2;
				this.thumbRangeMax =
					this.width - 2 * this.m_arrow_length - this.thumbWidth - 2;
			}
			this.thumbRange = this.thumbRangeMax - this.thumbRangeMin;
		}
	}

	public void paint(final Graphics g) {
		super.paint(g);

		final Dimension panelSize = this.getSize();

		this.graphicsInit(panelSize);
		// Color the background
		if (this.orientation == Adjustable.HORIZONTAL) {
			this.drawHorizontalArrows(g);
		}
		else {
			this.drawVerticalArrows(g);
		}
		this.refresh(g);
	}

	/** Function checks and calculates the values of the range. */

	private void rangeSetup() {
		if (this.actualRangeMin >= this.actualRangeMax) {
			this.actualRangeMin = 0;
			this.actualRangeMax = 100;
		}
		this.actualRange = this.actualRangeMax - this.actualRangeMin;
	}

	/** Paints the thumbs **/
	private void refresh() {
		final Graphics g = this.getGraphics();
		this.refresh(g);
	}

	/** Paints the thumbs **/
	private void refresh(final Graphics g) {
		if (g != null) {
			if (this.leftMoved || this.rightMoved) {
				this.adjustValue();
			}
			if (this.leftValueChanged || this.rightValueChanged) {
				this.adjustPosition();
			}
			if (this.orientation == Adjustable.HORIZONTAL) {
				this.drawHorizontalThumbs(g);
			}
			else {
				this.drawVerticalThumbs(g);
			}
		}
	}

	/** Look at ScrollBar or Adjustable interface for definition.*/
	public synchronized void removeAdjustmentListener(final AdjustmentListener l) {
		this.aListener = null;
	}

	public void repaint() {
		this.leftValueChanged = this.rightValueChanged = true;
		super.repaint();
	}

	/** Sets the block increment for this range slider.
	 *  @param v value to be used as the block inrement
	 */
	public void setBlockIncrement(final int v) {
		this.blockIncrement = v;
	}

	/** Same as setBounds(...) in the Component class. */
	public void setBounds(
		final int x,
		final int y,
		final int width,
		final int height) {
		super.setBounds(x, y, width, height);
		this.rightValueChanged = this.leftValueChanged = true;
	}

	public void setLeftBoxValue(final int v) {
		if (v < this.actualRangeMin) {
			this.leftBoxValue = this.actualRangeMin;
		}
		else if (v >= this.rightBoxValue) {
			this.leftBoxValue = this.rightBoxValue - 1;
		}
		else {
			this.leftBoxValue = v;
		}
		//    leftValueChanged = true;
		this.adjustBoxValue();
		this.repaint();
	}

	/** Sets the left thumb value for this range slider.
	 *  @param v value to be used as the left thumb value
	 */
	public void setLeftValue(final int v) {
		if (v < this.actualRangeMin) {
			this.leftValue = this.actualRangeMin;
		}
		else if (v >= this.rightValue) {
			this.leftValue = this.rightValue - 1;
		}
		else {
			this.leftValue = v;
		}
		this.leftValueChanged = true;
		this.adjustValue();
		this.repaint();
	}

	/** Sets the maximum of the range to the value given.
	 *  @param v value to be used as the maximum range value
	 */
	public void setMaximum(final int v) {
		this.actualRangeMax = v;
		this.rangeSetup();
		this.repaint();
	}

	/** Sets the minimum of the range to the value given.
	 *  @param v value to be used as the minimum range value
	 */
	public void setMinimum(final int v) {
		this.actualRangeMin = v;
		this.rangeSetup();
		this.repaint();
	}

	public void setRightBoxValue(final int v) {
		if (v > this.actualRangeMax) {
			this.rightBoxValue = this.actualRangeMax;
		}
		else if (v <= this.leftBoxValue) {
			this.rightBoxValue = this.leftBoxValue + 1;
		}
		else {
			this.rightBoxValue = v;
		}
		//    rightValueChanged = true;
		this.adjustBoxValue();
		this.repaint();
	}

	/** Sets the right value for this range slider.
	 *  @param v value to be used as the right thumb value
	 */
	public void setRightValue(final int v) {
		if (v > this.actualRangeMax) {
			this.rightValue = this.actualRangeMax;
		}
		else if (v <= this.leftValue) {
			this.rightValue = this.leftValue + 1;
		}
		else {
			this.rightValue = v;
		}
		this.rightValueChanged = true;
		this.adjustValue();
		this.repaint();
	}

	/** Sets the unit increment for this range slider.
	 *  @param v  value to be used as the unit increment
	 */
	public void setUnitIncrement(final int v) {
		this.unitIncrement = v;
	}

	/** Dummy function does not do anythuing.*/
	public void setValue(final int v) {
	}

	/** Sets the left and right values for this range slider.
	 *  @param lv value to be used as the left thumb value
	 *  @param rv value to be used as the right thumb value
	 */
	public void setValues(
		final int lv,
		final int rv,
		final int minimum,
		final int maximum) {
		this.actualRangeMin = minimum;
		this.actualRangeMax = maximum;
		this.rangeSetup();
		this.setLeftValue(lv);
		this.setRightValue(rv);
		this.repaint();
	}

	/** Dummy function does not do anythuing.*/
	public void setVisibleAmount(final int v) {
	}

	/** Function decrements the value of the left thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void unitDecrementLeft() {
		this.lastThumbChanged = TMRangeSlider.LEFT_THUMB;
		if (this.leftValue + this.unitIncrement <= this.rightValue) {
			this.leftValue += this.unitIncrement;
			if (this.aListener != null) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.UNIT_DECREMENT,
					this.leftValue));
			}
		}
		else {
			this.leftValue = this.rightValue;
		}
		this.leftValueChanged = true;
		this.refresh();
	}

	/** Function decrements the value of the right thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void unitDecrementRight() {
		this.lastThumbChanged = TMRangeSlider.RIGHT_THUMB;
		if (this.rightValue - this.unitIncrement >= this.leftValue) {
			this.rightValue -= this.unitIncrement;
			if (this.aListener != null) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.UNIT_DECREMENT,
					this.rightValue));
			}
		}
		else {
			this.rightValue = this.leftValue;
		}
		this.rightValueChanged = true;
		this.refresh();
	}

	/** Function increments the value of the left thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void unitIncrementLeft() {
		this.lastThumbChanged = TMRangeSlider.LEFT_THUMB;
		if (this.leftValue - this.unitIncrement >= this.actualRangeMin) {
			this.leftValue -= this.unitIncrement;
			if (this.aListener != null) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.UNIT_INCREMENT,
					this.leftValue));
			}
		}
		else {
			this.leftValue = this.actualRangeMin;
		}
		this.leftValueChanged = true;
		this.refresh();
	}

	/** Function increments the value of the right thumb and then recalculates the position of the thumb. Did not know how to make it a private therefore YOU SHOULD NOT HAVE TO CALL THIS FUNCTION BY YOURSELF. A LISTENER CALLS IT*/
	private void unitIncrementRight() {
		this.lastThumbChanged = TMRangeSlider.RIGHT_THUMB;
		if (this.rightValue + this.unitIncrement <= this.actualRangeMax) {
			this.rightValue += this.unitIncrement;
			if (this.aListener != null) {
				this.aListener.adjustmentValueChanged(new AdjustmentEvent(
					this,
					AdjustmentEvent.ADJUSTMENT_FIRST,
					AdjustmentEvent.UNIT_INCREMENT,
					this.rightValue));
			}
		}
		else {
			this.rightValue = this.actualRangeMax;
		}
		this.rightValueChanged = true;
		this.refresh();
	}

	/** Update overloaded to reduce the amount of flicker between repaintings.*/
	public void update(final Graphics g) {
		this.paint(g);
	}

}
