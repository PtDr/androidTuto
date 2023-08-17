package com.example.tuto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuto.FirebaseInstance;
import com.example.tuto.MainActivity;
import com.example.tuto.R;
import com.example.tuto.adapters.PlantAdapter;
import com.example.tuto.adapters.PlantItemDecoration;
import com.example.tuto.repository.PlantRepository;

public class HomeFragment extends Fragment {

    private final MainActivity context;

    public HomeFragment(MainActivity context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (inflater!=null){
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            PlantRepository repository = new PlantRepository();

            //recuperer  le recycler view
            RecyclerView horizontalRecyclerView = view.findViewById(R.id.horizontal_recycler_view);
            horizontalRecyclerView.setAdapter(new PlantAdapter(context, FirebaseInstance.getInstance().getPlantListNotLiked(), R.layout.item_horizontal_plant, repository));

            //recuperer  le recycler view
            RecyclerView verticalRecyclerView = view.findViewById(R.id.vertical_recycler_view);
            verticalRecyclerView.setAdapter(new PlantAdapter(context, FirebaseInstance.getInstance().getPlantList(), R.layout.item_vertical_plant, repository));
            verticalRecyclerView.addItemDecoration(new PlantItemDecoration());

            //actualise la Liste plante
            repository.updateData();

            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
