package com.example.vishal.edhusk_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.net.URL;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;

public class Splash_Screen extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "Splash_Google signin";
    private static final int RC_SignIn = 9001;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private String email_user;
    private String name_user;

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        findViewById(R.id.google_sign_in).setOnClickListener(this);

        //Geting google Signin options

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();


        mGoogleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, this).
                addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();


        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){startActivity(new Intent(Splash_Screen.this,Main_Home.class));}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SignIn) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);


            } else {


            }

        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());



        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d(TAG, "signinwithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), Main_Home.class));
                    updateUI(user);
                } else {
                    Log.w(TAG, "sign_credential:fail", task.getException());
                    Toast.makeText(Splash_Screen.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });

    }

    private void signIn() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SignIn);

    }
    public void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(null);
            }
        });
    }



    private void updateUI(FirebaseUser user) {
        if (user != null) {

            editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();


            email_user = getString(R.string.google_status_fmt, user.getEmail());
            editor.putString("email", email_user);

            name_user = getString(R.string.google_status_fmt, user.getDisplayName());
            editor.putString("name", name_user);


            String url = getString(R.string.google_status_fmt, user.getPhotoUrl());
            editor.putString("url", url);


//            Uri photoUri = user.getPhotoUrl();
//
//            if(photoUri != null){
//                editor.putString("uri",photoUri.toString());
//            }else
//            {
//                Toast.makeText(getApplicationContext(),"error in reciveing image url",Toast.LENGTH_SHORT).show();
//            }

            editor.apply();


        }
    }




    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.google_sign_in) {
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}

