package com.mosh.javadevelopersinlagos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mosh.javadevelopersinlagos.adapter.UserAdapter;
import com.mosh.javadevelopersinlagos.model.UserList;
import com.mosh.javadevelopersinlagos.service.RestApiBuilder;
import com.mosh.javadevelopersinlagos.service.RestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static String location = "lagos";

    private RecyclerView recyclerView;
    private Context mContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate:  started.");

        //suggestion fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] recipients = {"mosh.onaarajnr@gmail.com", "oyetolamoshoodabiola@gmail.com"};
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions (Lagos Java Developer)");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                }
            }
        });

        //Layout manager
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_user_list);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        //checking for network connectivity
        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Network connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            fetchUsersData();
                        }
                    });

            snackbar.show();
        } else {
            fetchUsersData();
        }

    }

    private void prepareData(UserList userList) {
        Log.d(TAG, "prepareData:  prepareData");
        UserAdapter adapter = new UserAdapter(this, userList.getItems());
        recyclerView.setAdapter(adapter);

    }

    private void fetchUsersData() {
        String searchParams = "language:java location:"+location;
        RestApiService apiService = new RestApiBuilder().getService();
        Call<UserList> userListCall = apiService.getUserList(searchParams);
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    UserList userList = response.body();
                    prepareData(userList);
                } else {

                    Toast.makeText(MainActivity.this,
                            "Request not Successful",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.lagos:
                location = "lagos";
                fetchUsersData();
                return true;

            case R.id.osun:
                location = "osun";
                fetchUsersData();
                return true;

            case R.id.oyo:
                location = "oyo";
                fetchUsersData();
                return true;

            case R.id.ondo:
                location = "ondo";
                fetchUsersData();
                return true;

            case R.id.abuja:
                location = "abuja";
                fetchUsersData();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
