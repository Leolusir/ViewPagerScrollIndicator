package com.leo.viewpagerscrollindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by littleming on 15/7/28.
 */
public class PagerItem extends Fragment{
    private View rootView;
    private TextView textView;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.pager_item, container, false);
            textView = (TextView)rootView.findViewById(R.id.text);
            textView.setText(text);
        }else{
            ViewGroup parent = (ViewGroup)rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    public void setText(String text){
        this.text = text;
    }

}
