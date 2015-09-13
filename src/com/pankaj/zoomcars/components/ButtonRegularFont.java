package com.pankaj.zoomcars.components;

import com.pankaj.zoomcars.utils.Constants;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonRegularFont extends Button{
	
	public ButtonRegularFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ButtonRegularFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonRegularFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT_CIRCULAR);
            setTypeface(tf);
        }
    }
}
