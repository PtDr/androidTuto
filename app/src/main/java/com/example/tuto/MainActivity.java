package com.example.tuto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tuto.fragments.AddPlantFragment;
import com.example.tuto.fragments.CollectionFragment;
import com.example.tuto.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //importer le bottomNavigationView
        BottomNavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home_page) loadFragment(new HomeFragment(getInstance()), R.string.home_page_title);
                else if (item.getItemId()==R.id.collection_page) loadFragment(new CollectionFragment(getInstance()), R.string.collection_page_title);
                else loadFragment(new AddPlantFragment(getInstance()), R.string.add_plant_page_title);

                return true;
            }
        });

        loadFragment(new HomeFragment(getInstance()), R.string.home_page_title);
    }

    private MainActivity getInstance(){
        return this;
    }

    private void loadFragment(Fragment homeFragment, Integer pageTitle) {

        TextView title = findViewById(R.id.page_title);
        title.setText(getResources().getString(pageTitle));

        //        injecter le fragment home dans l'activité main
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmen_container, homeFragment);
//        transaction.replace(R.id.fragmen_container, new CollectionFragment(this));
//        transaction.replace(R.id.fragmen_container, new AddPlantFragment(this));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}