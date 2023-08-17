package com.example.tuto.repository;

import androidx.annotation.NonNull;

import com.example.tuto.DataBaseInstance;
import com.example.tuto.adapters.PlantAdapter;
import com.example.tuto.model.PlantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlantRepository {

    DataBaseInstance dataBase = DataBaseInstance.getInstance();
    DatabaseReference databaseReference = dataBase.getDatabaseReference();

    private List<PlantAdapter> adapters = new ArrayList<>();

    public PlantRepository() {
    }

    public PlantRepository(List<PlantAdapter> adapters){
        this.adapters = adapters;
    }

    public void updateData(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataBase.getPlantList().clear();
                for (DataSnapshot child:snapshot.getChildren()){
                    PlantModel plant = child.getValue(PlantModel.class);

                    if (plant != null) dataBase.getPlantList().add(plant);
                }

                //refresh data
                for (PlantAdapter adapter:adapters){
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<PlantAdapter> getAdapters() {
        return adapters;
    }

    public void updatePlant(PlantModel plantModel){
        databaseReference.child(plantModel.getId()).setValue(plantModel);
    }

    public void deletePlant(PlantModel plantModel){
        databaseReference.child(plantModel.getId()).removeValue();
    }
}
