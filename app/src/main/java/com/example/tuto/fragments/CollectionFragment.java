package com.example.tuto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuto.FirebaseInstance;
import com.example.tuto.MainActivity;
import com.example.tuto.R;
import com.example.tuto.adapters.PlantAdapter;
import com.example.tuto.adapters.PlantItemDecoration;
import com.example.tuto.repository.PlantRepository;

public class CollectionFragment extends Fragment {

    private final MainActivity context;

    public CollectionFragment(MainActivity context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater!=null){
            View view = inflater.inflate(R.layout.fragment_collection, container, false);
            PlantRepository plantRepository = new PlantRepository();

            RecyclerView collectionRecyclerView = view.findViewById(R.id.collection_recycler_list);
            collectionRecyclerView.setAdapter(new PlantAdapter(context,
                                                FirebaseInstance.getInstance().getPlantListLiked(),
                                                R.layout.item_vertical_plant, plantRepository));
            collectionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            collectionRecyclerView.addItemDecoration(new PlantItemDecoration());

            plantRepository.updateData();

            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
