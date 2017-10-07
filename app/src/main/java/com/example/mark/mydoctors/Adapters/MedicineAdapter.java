package com.example.mark.mydoctors.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.mydoctors.Model.Medicine;
import com.example.mark.mydoctors.R;

import java.util.ArrayList;

/**
 * Created by Mark on 22.05.2016..
 */
public class MedicineAdapter extends ArrayAdapter<Medicine> {
    Context context;
    int layoutResourceId;
    private ArrayList<Medicine> data=null;

    public MedicineAdapter(Context context, int layoutResourceId, ArrayList<Medicine> data) {
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
        Medicine grad;
        if (getCount() == 0) {
            grad = getItem(-1);
        }


        grad = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewData; // view lookup cache stored in tag
        if (convertView == null) {
            viewData = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.medicine_content_adapter, parent, false);
            viewData.txtId = (TextView) convertView.findViewById(R.id.textViewAdapterMedicineId);
            viewData.txtDName = (TextView) convertView.findViewById(R.id.textViewAdapterMedicineName);

            convertView.setTag(viewData);
        } else {
            viewData = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewData.txtId.setText("Šifra lijeka/terapije: " + Integer.toString(grad.getId()));
        viewData.txtDName.setText("Naziv lijeka/terapije: " + grad.getName());

        // Return the completed view to render on screen
        return convertView;
    }

    private static class ViewHolder
    {
        TextView txtId;
        TextView txtDName;
    }
}
