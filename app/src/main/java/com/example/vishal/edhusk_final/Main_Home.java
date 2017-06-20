package com.example.vishal.edhusk_final;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pkmmte.view.CircularImageView;
import com.ramotion.foldingcell.FoldingCell;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Main_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user");


    ListView listView;
    SharedPreferences sharedPreferences;

    public ArrayList<student_data> student_list = new ArrayList<student_data>();
    public FoldingCellListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.mListView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new JSONAsyncTask().execute("https://api.myjson.com/bins/ihu0v");

        adapter = new FoldingCellListAdapter(getApplicationContext(),R.layout.cell, student_list);

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Default handler", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(position);
            }
        });

//Dialog box for profile of tutor

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_user_dialog);
     final   String userId = mDatabase.push().getKey();

        TextView profile_name = (TextView) dialog.findViewById(R.id.profile_dialog_name);
        TextView profile_email = (TextView) dialog.findViewById(R.id.profile_dialog_email);
        CircularImageView profile_image = (CircularImageView) dialog.findViewById(R.id.profile_dialog_image);
        Button profile_submit = (Button) dialog.findViewById(R.id.profile_submit);
        final EditText profile_phone = (EditText) dialog.findViewById(R.id.profile_dialog_phone_no);
        final EditText profile_address = (EditText) dialog.findViewById(R.id.profile_dialog_address);
        RadioGroup radioGenderGroup = (RadioGroup) dialog.findViewById(R.id.dialog_profile_rg);
        final Spinner city_spinner = (Spinner) dialog.findViewById(R.id.dialog_profile_spinner_city);

        final String city_list[] = {"jaipur"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city_list);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(cityAdapter);


        final String[] gender = new String[1];
radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if(checkedId == R.id.dialog_profile_rd_male) {
            gender[0] = "Male";

        }else

            if(checkedId == R.id.dialog_profile_rd_female);
        gender[0] = "Female";

        }
});

        sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);


        final String name = sharedPreferences.getString("name","Error in getting name");
        profile_name.setText(name);
        String email = sharedPreferences.getString("email","Error in getting name");
        profile_email.setText(email);
        String url = sharedPreferences.getString("url","Error in getting url");
        Glide.with(this).load(url).thumbnail(0.8f).into(profile_image);





        profile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] city = new String[1];
                String phone = profile_phone.getText().toString();
               String address = profile_address.getText().toString();
                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        city[0] = city_list[position];

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                if (phone != null & address != null & gender[0] != null) {
                    mDatabase.child(userId).child(name).child("phone").setValue(phone);
                    mDatabase.child(userId).child(name).child("address").setValue(address);
                    mDatabase.child(userId).child(name).child("gender").setValue(gender[0]);
                    mDatabase.child(userId).child(name).child("city").setValue("jaipur");



                    dialog.dismiss();
                }



            }
        });

        dialog.show();


        mDatabase.child(userId).setValue(name);
        mDatabase.child(userId).child(name).child("email").setValue(email);
        mDatabase.child(userId).child(name).child("image").setValue(url);




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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            startActivity(new Intent(this,Main_Home.class));
        } else if (id == R.id.account) {


        } else if (id == R.id.history) {


        } else if (id == R.id.change_bank_detail) {


        } else if (id == R.id.change_password) {


        } else if (id == R.id.contact) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;





    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Home.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONArray jarray = new JSONArray(data);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        student_data item = new student_data();

                        item.setName(object.getString("Name"));



                        student_list.add(item);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }


    }


}
