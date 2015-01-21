package com.traviswooten.playtime.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traviswooten.playtime.R;


/**
 * Created by traviswooten on 1/9/15.
 */
public class IconButton extends LinearLayout {

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconButton, 0, 0);
        Drawable icon = a.getDrawable(R.styleable.IconButton_icon);
        String buttonText = a.getString(R.styleable.IconButton_button_text);
        int textColor = a.getColor(R.styleable.IconButton_button_text_color, R.color.black);
        int textSize = a.getDimensionPixelSize(R.styleable.IconButton_button_text_size, R.dimen.default_margin);
        a.recycle();

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(params);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_icon_button, this, true);

        ImageView iconView = (ImageView) getChildAt(0);
        iconView.setImageDrawable(icon);

        TextView buttonTextView = (TextView) getChildAt(2);
        buttonTextView.setText(buttonText);
        buttonTextView.setTextColor(textColor);
        buttonTextView.setTextSize(textSize);
    }
}
