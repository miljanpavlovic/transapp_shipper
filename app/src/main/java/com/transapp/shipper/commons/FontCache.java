package com.transapp.shipper.commons;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Miljan on 6/29/2015.
 */
public class FontCache {
    public static final int SMALL_FONT_SIZE_IN_SP = 14;
    public static final int LARGE_FONT_SIZE_IN_SP = 16;

    private static final String LOG_TAG = FontCache.class.getSimpleName();
    private static final Hashtable<String, Typeface> typefaceHashtable = new Hashtable<String, Typeface>();

    public static Typeface getCachedTypeface(String name, Context context) {
        Typeface cachedTypeface = typefaceHashtable.get(name);
        if (cachedTypeface == null) {
            try {
                cachedTypeface = Typeface.createFromAsset(context.getAssets(), name);
                typefaceHashtable.put(name, cachedTypeface);
            } catch (Exception e) {
                return Typeface.DEFAULT;
            }
        }
        return cachedTypeface;
    }
}
