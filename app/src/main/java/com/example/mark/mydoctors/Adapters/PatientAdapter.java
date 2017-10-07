package com.example.mark.mydoctors.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mark.mydoctors.Model.Patient;
import com.example.mark.mydoctors.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mark on 22.05.2016..
 */
public class PatientAdapter extends ArrayAdapter<Patient> {
    Context context;
    int layoutResourceId;
    private List<Patient> patientsList = null;
    private ArrayList<Patient> data;

    public PatientAdapter(Context context, int layoutResourceId, ArrayList<Patient> data) {
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        Patient grad;
        if (getCount() == 0) {
            grad = getItem(-1);
        }


        grad = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewData; // view lookup cache stored in tag
        if (convertView == null) {
            viewData = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.patient_content_adapter, parent, false);
            viewData.txtName = (TextView) convertView.findViewById(R.id.textViewAdapterPatientNameAndSurname);
            viewData.txtBzi = (TextView) convertView.findViewById(R.id.textViewAdapterPatientBZI);
            viewData.patImg = (ImageView) convertView.findViewById(R.id.textViewAdapterPatientImg);
            viewData.menuButton = (Button) convertView.findViewById(R.id.buttonMenu);
            convertView.setTag(viewData);
        } else {
            viewData = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewData.txtName.setText(grad.getName() + " " + grad.getSurname());
        viewData.txtBzi.setText("BZI: " + grad.getId());
        viewData.patImg.setImageResource(R.drawable.ic_patient_imag_2);
        // Return the completed view to render on screen

        viewData.menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.showContextMenuForChild(v);
            }
        });



        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            patientsList.addAll(data);
        } else {
            for (Patient wp : data) {
                if (wp.getSurname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    patientsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder
    {
        TextView txtName;
        TextView txtBzi;
        ImageView patImg;
        Button menuButton;
    }
}
