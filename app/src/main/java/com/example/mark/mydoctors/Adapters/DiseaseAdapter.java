package com.example.mark.mydoctors.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.mydoctors.Model.Disease;
import com.example.mark.mydoctors.R;

import java.util.ArrayList;

/**
 * Created by Mark on 22.05.2016..
 */
public class DiseaseAdapter extends ArrayAdapter<Disease> {
    Context context;
    int layoutResourceId;
    private ArrayList<Disease> data=null;

    public DiseaseAdapter(Context context, int layoutResourceId, ArrayList<Disease> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public int getCount() {

        if(data.size()<=0)
            return 0;
        return data.size();
    }


    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Disease grad;
        if (getCount() == 0) {
            grad = getItem(-1);
        }


        grad = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewData; // view lookup cache stored in tag
        if (convertView == null) {
            viewData = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.disease_content_adapter, parent, false);
            viewData.txtDName = (TextView) convertView.findViewById(R.id.textViewAdapterDiseaseName);
            viewData.txtDIsOver = (TextView) convertView.findViewById(R.id.textViewAdapterDiseaseIsOver);
            convertView.setTag(viewData);
        } else {
            viewData = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewData.txtDName.setText("Razlog dolaska: " + grad.getName());
        viewData.txtDIsOver.setText("Vrijeme dolaska: " + grad.getIs_over());
        // Return the completed view to render on screen
        return convertView;
    }

    private static class ViewHolder
    {
        TextView txtDName;
        TextView txtDIsOver;
    }
}
