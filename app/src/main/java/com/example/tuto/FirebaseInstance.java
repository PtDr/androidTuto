package com.example.tuto;

import com.example.tuto.model.PlantModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseInstance {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private final String BUCKET_URL = "gs://tuto-4aa8e.appspot.com";//donner le lien pour acceder au bucket
    private List<PlantModel> plantList = new ArrayList<>();
    private List<PlantModel> plantListLiked = new ArrayList<>();
    private List<PlantModel> plantListNotLiked = new ArrayList<>();
    private static final FirebaseInstance instance = new FirebaseInstance();

    private FirebaseInstance(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("plants");
        this.storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL);
    }

    public static FirebaseInstance getInstance(){
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

    public List<PlantModel> getPlantListNotLiked() {
        return plantListNotLiked;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }
}
