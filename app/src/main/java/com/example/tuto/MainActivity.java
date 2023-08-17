package com.example.tuto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tuto.fragments.CollectionFragment;
import com.example.tuto.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        injecter le fragment home dans l'activité main
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//         transaction.replace(R.id.fragmen_container, new HomeFragment(this));
        transaction.replace(R.id.fragmen_container, new CollectionFragment(this));
         transaction.addToBackStack(null);
         transaction.commit();
    }
}