package com.example.mark.mydoctors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.mydoctors.Adapters.PatientAdapter;
import com.example.mark.mydoctors.CustomHelpers.Communicator;
import com.example.mark.mydoctors.Model.Patient;
import com.example.mark.mydoctors.dao.CoreDao;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    EditText inputSearch;
    PatientAdapter arrayAdapter;

    private ListView obj;

    CoreDao coreDao;

    ArrayList array_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //inputSearch = (EditText) findViewById(R.id.myEditTextSearch);
        coreDao = new CoreDao(this);

        array_list = coreDao.instantiatePatientDao().getAllPatients();
        arrayAdapter = new PatientAdapter(this,R.layout.patient_content_adapter, array_list);

        inputSearch = (EditText) findViewById(R.id.inputSearch);

        obj = (ListView)findViewById(R.id.listViewPatients);

        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Patient p = (Patient) obj.getItemAtPosition(arg2);
                Intent i = new Intent(getApplicationContext(), DisasiesActivity.class);
                i.putExtra("patients_id", p.getId());
                startActivity(i);
            }
        });

        obj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.patient_details_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final TextView name = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsName);
                final TextView surname = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsSurname);
                final TextView email = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsEmail);
                final TextView street = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsStreet);
                final TextView place = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsPlace);
                final TextView phone = (TextView) promptsView.findViewById(R.id.editTextDialogUserDetailsPhone);

                Patient p = (Patient) obj.getItemAtPosition(pos);
                name.setText(p.getName());
                surname.setText(p.getSurname());
                email.setText(p.getEmail());
                street.setText(p.getStreet());
                place.setText(p.getPlace());
                phone.setText(p.getPhone());
                // set dialog message
                alertDialogBuilder.setItems(new CharSequence[]
                                {"Nazovi", "Pošalji E-Mail", "Pokaži na karti", "Obriši", "Ažuriraj"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        Patient p = (Patient) obj.getItemAtPosition(pos);
                                        callIntent.setData(Uri.parse("tel:" + p.getPhone()));
                                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //startActivity(callIntent);
                                        break;
                                    case 1:
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        p = (Patient) obj.getItemAtPosition(pos);
                                        i.setType("message/rfc822");
                                        i.putExtra(Intent.EXTRA_EMAIL, p.getEmail());
                                        i.putExtra(Intent.EXTRA_SUBJECT, "Obavijest od doktora.");
                                        i.putExtra(Intent.EXTRA_TEXT, "Pozdrav poštovani , ");
                                        try {
                                            startActivity(Intent.createChooser(i, "Send mail..."));
                                        } catch (android.content.ActivityNotFoundException ex) {
                                            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                        }
                                        break;
                                    case 2:
                                        p = (Patient) obj.getItemAtPosition(pos);
                                        String address = p.getPlace() + ", " + p.getStreet();
                                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                        LatLng latLng = GetLatitudeAndLongitude(address);
                                        Communicator.setComponent(latLng);
                                        Communicator.setButtonPressed(false);
                                        startActivity(intent);
                                        break;
                                    case 3:

                                        p = (Patient) obj.getItemAtPosition(pos);


                                            coreDao.instantiateMedicinenDao().deleteMedicine(p.getId());
                                            coreDao.instantiateDiseaseDao().deletedPatientsDiseases(p.getId());
                                            coreDao.instantiatePatientDao().deletePatient(p.getId());
                                            arrayAdapter.remove(p);
                                            arrayAdapter.notifyDataSetChanged();



                                        break;
                                    case 4:
                                        LayoutInflater li = LayoutInflater.from(context);
                                        View promptsView = li.inflate(R.layout.add_patient_dialog, null);

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                context);

                                        // set prompts.xml to alertdialog builder
                                        alertDialogBuilder.setView(promptsView);


                                        final EditText name = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputName);
                                        final EditText surname = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputSurname);
                                        final EditText email = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputEmail);
                                        final EditText street = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputStreet);
                                        final EditText place = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputPlace);
                                        final EditText phone = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputPhone);

                                        p = (Patient) obj.getItemAtPosition(pos);

                                        name.setText(p.getName());
                                        surname.setText(p.getSurname());
                                        email.setText(p.getEmail());
                                        street.setText(p.getStreet());
                                        place.setText(p.getPlace());
                                        phone.setText(p.getPhone());

                                        // set dialog message
                                        alertDialogBuilder
                                                .setCancelable(true)
                                                .setPositiveButton("AŽURIRAJ",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // get user input and set it to result
                                                                // edit text
                                                                Patient p = (Patient) obj.getItemAtPosition(pos);

                                                                p.setName(name.getText().toString());
                                                                p.setSurname(surname.getText().toString());
                                                                p.setPhone(phone.getText().toString());
                                                                p.setEmail(email.getText().toString());
                                                                p.setStreet(street.getText().toString());
                                                                p.setPlace(place.getText().toString());

                                                                coreDao.instantiatePatientDao().updatePatient(p);
                                                                arrayAdapter.notifyDataSetChanged();
                                                            }
                                                        })
                                                .setNegativeButton("Cancel",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        });

                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();
                                        break;
                                }
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                return true;
            }
        });

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        arrayAdapter.notifyDataSetChanged();
        obj.setAdapter(arrayAdapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                int textlength = cs.length();
                ArrayList<Patient> tempArrayList = new ArrayList<>();
                for(int i = 0; i < array_list.size(); i++){
                    Patient p = (Patient)array_list.get(i);
                    if (textlength <= p.getSurname().length()) {
                        if (p.getSurname().toLowerCase().contains(cs.toString().toLowerCase())) {
                            tempArrayList.add(p);
                        }
                    }
                }
                arrayAdapter = new PatientAdapter(context, R.layout.patient_content_adapter, tempArrayList);
                obj.setAdapter(arrayAdapter);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_add) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.add_patient_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText name = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputName);
            final EditText surname = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputSurname);
            final EditText email = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputEmail);
            final EditText street = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputStreet);
            final EditText place = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputPlace);
            final EditText phone = (EditText) promptsView.findViewById(R.id.editTextDialogUserInputPhone);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("DODAJ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    Patient p = new Patient();
                                    p.setName(name.getText().toString());
                                    p.setSurname(surname.getText().toString());
                                    p.setPhone(phone.getText().toString());
                                    p.setEmail(email.getText().toString());
                                    p.setStreet(street.getText().toString());
                                    p.setPlace(place.getText().toString());
                                    coreDao.instantiatePatientDao().insertPatient(p);

                                    array_list.clear();
                                    array_list = coreDao.instantiatePatientDao().getAllPatients();
                                    arrayAdapter = new PatientAdapter(context,R.layout.patient_content_adapter, array_list);
                                    obj.setAdapter(arrayAdapter);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        if (id == R.id.action_map) {
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            Communicator.setButtonPressed(true);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.hr/maps/search/hospitals+in+Croatia/@44.6231624,14.0414779,7z/data=!3m1!4b1"));
            startActivity(browserIntent);
        }

        else if (id == R.id.nav_send) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hzzo.hr/zdravstveni-sustav-rh/trazilica-za-lijekove-s-vazecih-lista/"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public LatLng GetLatitudeAndLongitude(String address) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        LatLng l = new LatLng(0, 0);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                l = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

}
