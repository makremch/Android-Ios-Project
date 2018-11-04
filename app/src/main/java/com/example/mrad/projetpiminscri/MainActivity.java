package com.example.mrad.projetpiminscri;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eightbitlab.bottomnavigationbar.BottomBarItem;
import com.eightbitlab.bottomnavigationbar.BottomNavigationBar;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity implements Articles.OnFragmentInteractionListener,Recomendation.OnFragmentInteractionListener,
        Messagerie.OnFragmentInteractionListener,Profil_personnel.OnFragmentInteractionListener{


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);

        BottomBarItem item1 = new BottomBarItem(R.drawable.com_facebook_button_icon);
        BottomBarItem item2 = new BottomBarItem(R.drawable.ic_launcher_background);
        BottomBarItem item3 = new BottomBarItem(R.drawable.ic_skip_white);
        BottomBarItem item4 = new BottomBarItem(R.drawable.ic_done_white);


        bottomNavigationBar.addTab(item1);
        bottomNavigationBar.addTab(item2);
        bottomNavigationBar.addTab(item3);
        bottomNavigationBar.addTab(item4);

        bottomNavigationBar.selectTab(1,true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Recomendation recomendation = new Recomendation();
        fragmentTransaction.replace(R.id.FragmentContainer, recomendation);
        fragmentTransaction.commit();

        bottomNavigationBar.setOnSelectListener(new BottomNavigationBar.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                if(position==0)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Articles articles = new Articles();
                    fragmentTransaction.replace(R.id.FragmentContainer, articles);
                    fragmentTransaction.commit();
                }
                if(position==1)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Recomendation recomendation = new Recomendation();
                    fragmentTransaction.replace(R.id.FragmentContainer, recomendation);
                    fragmentTransaction.commit();
                }
                if(position==2)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Messagerie messagerie = new Messagerie();
                    fragmentTransaction.replace(R.id.FragmentContainer, messagerie);
                    fragmentTransaction.commit();
                }
                if(position==3)
                {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Profil_personnel profil_personnel = new Profil_personnel();
                    fragmentTransaction.replace(R.id.FragmentContainer, profil_personnel);
                    fragmentTransaction.commit();
                }

            }
        });

        }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

