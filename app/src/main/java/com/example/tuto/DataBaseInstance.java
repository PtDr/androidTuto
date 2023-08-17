package com.example.tuto;

import com.example.tuto.model.PlantModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseInstance {

    private DatabaseReference databaseReference;
    private List<PlantModel> plantList = new ArrayList<>();
    private List<PlantModel> plantListLiked = new ArrayList<>();
    private static final DataBaseInstance instance = new DataBaseInstance();
    private DataBaseInstance(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("plants");
    }

    public static DataBaseInstance getInstance(){
        return instance;
    }
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public List<PlantModel> getPlantList() {
        return plantList;
    }

    public List<PlantModel> getPlantListLiked() {
        return plantListLiked;
    }
}
