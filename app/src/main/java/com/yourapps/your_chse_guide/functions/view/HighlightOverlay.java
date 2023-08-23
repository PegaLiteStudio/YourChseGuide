package com.yourapps.your_chse_guide.functions.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class HighlightOverlay extends View {
    private List<RectF> highlightedAreas;

    public HighlightOverlay(Context context) {
        super(context);
        init();
    }

    public HighlightOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Initialize any necessary variables or settings here
    }

    public void setHighlightedAreas(List<RectF> highlightedAreas) {
        this.highlightedAreas = highlightedAreas;
        invalidate(); // Trigger a redraw of the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (highlightedAreas != null) {
            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setAlpha(100); // Set transparency

            for (RectF rect : highlightedAreas) {
                canvas.drawRect(rect, paint);
            }
        }
    }
}
