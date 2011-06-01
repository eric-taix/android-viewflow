/**
 * 
 */
package org.taptwo.android.widget;

import org.taptwo.android.widget.viewflow.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

/**
 * A FlowIndicator which draws circles (one for each view). Circles are invisible except when the users swipes the screen.
 * In this case circles are visible and the current position circle has its number drawed, with a bigger radius than other circles
 * @author Eric Taix
 */
public class NumberFlowIndicator extends View implements FlowIndicator {
	private int radius = 40;
	private final Paint mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintNumber = new Paint(Paint.ANTI_ALIAS_FLAG);
	private ViewFlow viewFlow;
	private int currentScroll = 0;
	private int flowWidth = 0;
	private int currentPosition = 1;

	/**
	 * Default constructor
	 * 
	 * @param context
	 */
	public NumberFlowIndicator(Context context) {
		super(context);
		initColors(0xFFFFFFFF, 0xFFFFFFFF, 0xFFFF0000);
	}

	/**
	 * The contructor used with an inflater
	 * 
	 * @param context
	 * @param attrs
	 */
	public NumberFlowIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Retrieve styles attributs
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleFlowIndicator);
		// Retrieve the colors to be used for this view and apply them.
		int fillColor = a.getColor(R.styleable.CircleFlowIndicator_fillColor,
				0xFFFFFFFF);
		int strokeColor = a.getColor(
				R.styleable.CircleFlowIndicator_strokeColor, 0xFFFFFFFF);
		int numberColor = a.getColor(
				R.styleable.NumberFlowIndicator_numberColor, 0xFFFF0000);
		// Retrieve the radius
		radius = a.getInt(R.styleable.CircleFlowIndicator_radius, 4);
		initColors(fillColor, strokeColor, numberColor);
	}

	private void initColors(int fillColor, int strokeColor, int numberColor) {
		mPaintStroke.setStyle(Style.STROKE);
		mPaintStroke.setColor(strokeColor);
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(fillColor);
		mPaintNumber.setStyle(Style.FILL);
		mPaintNumber.setColor(numberColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int count = 3;
		if (viewFlow != null) {
			count = viewFlow.getViewsCount();
		}
		// Draw stroked circles
		for (int iLoop = 0; iLoop < count; iLoop++) {
			canvas.drawCircle(getPaddingLeft() + radius
					+ (iLoop * (2 * radius + radius)),
					getPaddingTop() + radius, radius / 2, mPaintStroke);
		}
		int cx = 0;
		if (flowWidth != 0) {
			// Draw the filled circle according to the current scroll
			cx = (currentScroll * (2 * radius + radius)) / flowWidth;
		}
		// The flow width has been upadated yet. Draw the default position
	    canvas.drawCircle(getPaddingLeft() + radius + cx,
					getPaddingTop() + radius, radius, mPaintFill);
	    Paint p = new Paint();
	    p.setColor(0xFFFFFFFF);
	    canvas.drawText(""+currentPosition, getPaddingLeft() + radius + cx, getPaddingTop() + radius, mPaintNumber);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.taptwo.android.widget.ViewFlow.ViewSwitchListener#onSwitched(android
	 * .view.View, int)
	 */
	@Override
	public void onSwitched(View view, int position) {
		invalidate();
		currentPosition = position;
	}

	/* (non-Javadoc)
	 * @see org.taptwo.android.widget.FlowIndicator#setViewFlow(org.taptwo.android.widget.ViewFlow)
	 */
	@Override
	public void setViewFlow(ViewFlow view) {
		viewFlow = view;
		flowWidth = viewFlow.getWidth();
		invalidate();
	}

	
	
	/* (non-Javadoc)
	 * @see org.taptwo.android.widget.FlowIndicator#onScrolled(int, int, int, int)
	 */
	@Override
	public void onScrolled(int h, int v, int oldh, int oldv) {
		currentScroll = h;
		flowWidth = viewFlow.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// We were told how big to be
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		// Calculate the width according the views count
		else {
			int count = 3;
			if (viewFlow != null) {
				count = viewFlow.getViewsCount();
			}
			result = getPaddingLeft() + getPaddingRight()
					+ (count * 2 * radius) + (count - 1) * radius + 1;
			// Respect AT_MOST value if that was what is called for by
			// measureSpec
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// We were told how big to be
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		// Measure the height
		else {
			result = 2 * radius + getPaddingTop() + getPaddingBottom() + 1;
			// Respect AT_MOST value if that was what is called for by
			// measureSpec
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * Sets the fill color
	 * 
	 * @param color
	 *            ARGB value for the text
	 */
	public void setFillColor(int color) {
		mPaintFill.setColor(color);
		invalidate();
	}

	/**
	 * Sets the stroke color
	 * 
	 * @param color
	 *            ARGB value for the text
	 */
	public void setStrokeColor(int color) {
		mPaintStroke.setColor(color);
		invalidate();
	}
}