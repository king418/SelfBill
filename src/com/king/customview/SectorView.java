package com.king.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: king
 * Date: 2015/4/11
 */
public class SectorView extends View {

    private Paint paint;
    private float[] data;
    private int[] color = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.BLACK};
    private float sum;
    private RectF rect;

    public SectorView(Context context) {
        this(context, null);
    }

    public SectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        rect = new RectF(10, 10, 300, 300);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 0;
        float sweepAngle = 0;
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                paint.setColor(color[i]);
                sweepAngle = (data[i] / sum) * 360f;
                canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
                startAngle = startAngle + sweepAngle;
            }
        }
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
    }
}
