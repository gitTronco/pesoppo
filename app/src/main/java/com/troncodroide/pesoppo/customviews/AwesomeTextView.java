package com.troncodroide.pesoppo.customviews;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.content.res.TypedArray;
        import android.graphics.Typeface;
        import android.os.Build;
        import android.util.AttributeSet;
        import android.widget.Button;
        import android.widget.TextView;

/**
 * Created by Usuario-007 on 10/04/2015.
 */
public class AwesomeTextView extends TextView{

    public AwesomeTextView(Context context) {
        super(context);
        if (!this.isInEditMode()){
            setTypeface(Typeface.createFromAsset(context.getAssets(),"fontawesome.ttf"));
        }
    }
    public AwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()){
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf"));
        }
    }

    public AwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!this.isInEditMode()){
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf"));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!this.isInEditMode()){
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf"));
        }
    }
}
