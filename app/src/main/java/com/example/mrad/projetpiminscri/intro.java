package com.example.mrad.projetpiminscri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.github.paolorotolo.appintro.AppIntro;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mrad on 16/01/2017.
 */

public class intro extends AppIntro {
    String s1;
    Profile profile;
    String fbId = null;
    String fbBirthday = null;
    String fbLocation = null;
    String fbEmail = null;
    String fbFirstName = null;
    String fblastName=null;
    String gender = null;
    boolean test = false;
    long nombre_utilisateur;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    JSONArray rawName;
    private static final String SUCCESS = "success";
    private static final int PICK_PERMS_REQUEST = 0;

    private CallbackManager callbackManager;

    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    private LoginButton fbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        addSlide(SampleSlide.newInstance(R.layout.intro1));

        LayoutInflater inflater = getLayoutInflater();
        FacebookSdk.sdkInitialize(getApplicationContext());
        getWindow().addContentView(inflater.inflate(R.layout.facebook_login, null),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        showSkipButton(false);
        setProgressButtonEnabled(false);
        setZoomAnimation();
        setFadeAnimation();
        setIndicatorColor(getResources().getColor(R.color.Transparent),getResources().getColor(R.color.Transparent));


        if (AccessToken.getCurrentAccessToken()!=null) {

            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);

        }
/////////////////////////////
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) findViewById(R.id.login);


        VideoView VideoDebut = (VideoView)findViewById(R.id.VideoDebut);

        String path = "android.resource://"+getPackageName()+"/raw/slowmosmoke";
        VideoDebut.setVideoURI(Uri.parse(path));
        VideoDebut.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        VideoDebut.start();

        fbLoginButton.setReadPermissions("user_birthday", "user_location", "email","public_profile");
        // Callback registration

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
               // Toast.makeText(intro.this,R.string.success,Toast.LENGTH_LONG).show();
                handleFacebookAccessToken(loginResult.getAccessToken());

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("object", object.toString());

                                try {
                                    //    profile.getFirstName(), profile.getLastName()

                                    fbId = object.getString("id");

                                    if(object.isNull("email"))
                                    {fbEmail ="";}
                                    else fbEmail = object.getString("email");

                                    int x=object.getString("name").indexOf(" ");
                                    int l=object.getString("name").length();
                                    fbFirstName = object.getString("name").substring(0,x);
                                    fblastName = object.getString("name").substring(x+1,l);

                                    if(object.isNull("gender"))
                                    { gender ="male";}
                                    else
                                    { gender=object.getString("gender"); }

                                    if(object.isNull("birthday"))
                                    {fbBirthday ="";}
                                    else
                                    {fbBirthday = object.getString("birthday");}



                                    if(object.isNull("location"))
                                    {fbLocation ="";}
                                    else
                                    {JSONObject jsonObject = object.getJSONObject("location");
                                        fbLocation = jsonObject.getString("name");}

                                    int year = Calendar.getInstance().get(Calendar.YEAR);
                                    Log.d("object",""+object);
                                    //String year_birth= fbBirthday.substring(6);
                                    //Log.d("yeaaar",year_birth);
                                    Date d = new Date();
                                    CharSequence date_creation  = DateFormat.format("dd/MM/yyyy", d.getTime());

                                    System.out.println("id : " + fbId);
                                    System.out.println("nom : " + fbFirstName);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,location,birthday,gender");
                request.setParameters(parameters);

                request.executeAsync();
                Intent myIntent = new Intent(intro.this, MainActivity.class);
                startActivity(myIntent);

            }

            @Override
            public void onCancel() {
                //Toast.makeText(intro.this,R.string.cancel,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {

                Toast.makeText(
                        intro.this,
                       "Erreur , réessayer",
                        Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Toast.makeText(this, "skip", Toast.LENGTH_SHORT).show();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        System.out.println("handleFacebookAccessToken" + token);


    }

    //////////////////////
    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PERMS_REQUEST) {
            if (resultCode == RESULT_OK) {
                String[] readPermsArr = data
                        .getStringArrayExtra(PermissionSelectActivity.EXTRA_SELECTED_READ_PARAMS);
                String writePrivacy = data
                        .getStringExtra(PermissionSelectActivity.EXTRA_SELECTED_WRITE_PRIVACY);
                String[] publishPermsArr = data
                        .getStringArrayExtra(
                                PermissionSelectActivity.EXTRA_SELECTED_PUBLISH_PARAMS);

                fbLoginButton.clearPermissions();

                if (readPermsArr != null) {
                    if (readPermsArr.length > 0) {
                        fbLoginButton.setReadPermissions(readPermsArr);
                    }
                }

                if ((readPermsArr == null ||
                        readPermsArr.length == 0) &&
                        publishPermsArr != null) {
                    if (publishPermsArr.length > 0) {
                        fbLoginButton.setPublishPermissions(publishPermsArr);
                    }
                }
                // Set write privacy for the user
                if ((writePrivacy != null)) {
                    DefaultAudience audience;
                    if (DefaultAudience.EVERYONE.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.EVERYONE;
                    } else if (DefaultAudience.FRIENDS.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.FRIENDS;
                    } else {
                        audience = DefaultAudience.ONLY_ME;
                    }
                    fbLoginButton.setDefaultAudience(audience);
                }
            }
        } else {


            callbackManager.onActivityResult(requestCode, resultCode, data);


        } }


}
