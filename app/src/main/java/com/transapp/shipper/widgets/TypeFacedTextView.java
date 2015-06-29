package com.transapp.shipper.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.transapp.shipper.R;
import com.transapp.shipper.commons.FontCache;

/**
 * Created by Miljan on 6/29/2015.
 */
public class TypeFacedTextView extends SpanFixedTextView {

    public TypeFacedTextView(Context context) {
        this(context, null);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
        String fontName = typedArray.getString(R.styleable.TypeFacedTextView_typeface);
        typedArray.recycle();

        if (fontName != null) {
            Typeface typeface = FontCache.getCachedTypeface(fontName, context);
            setTypeface(typeface);
        }
    }
}
