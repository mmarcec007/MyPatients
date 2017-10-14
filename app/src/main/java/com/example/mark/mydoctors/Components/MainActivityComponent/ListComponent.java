package com.example.mark.mydoctors.Components.MainActivityComponent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mark.mydoctors.DisasiesActivity;
import com.example.mark.mydoctors.Model.Patient;

/**
 * Created by Mark on 14.10.2017..
 */

public class ListComponent extends View {
    Context context;
    private Activity activity;

    public ListComponent(Context context, Activity act)
    {
        super(context);
        this.activity = act;
    }

    public void setup()
    {
        Toast.makeText(context, "CECA", Toast.LENGTH_LONG).show();
    }

    public void handle(final ListView obj)
    {
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Patient p = (Patient) obj.getItemAtPosition(arg2);
                Intent i = new Intent(activity.getBaseContext(), DisasiesActivity.class);
                i.putExtra("patients_id", p.getId());
                getContext().startActivity(i);
            }
        });
    }
}
