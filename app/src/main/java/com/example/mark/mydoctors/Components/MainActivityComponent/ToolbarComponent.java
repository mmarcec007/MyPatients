package com.example.mark.mydoctors.Components.MainActivityComponent;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.example.mark.mydoctors.R;

/**
 * Created by Mark on 14.10.2017..
 */

public class ToolbarComponent extends Activity {
    Context context;
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    public ToolbarComponent(Context context){
        this.context = context;
    }

    public void manage() {
    }
}
