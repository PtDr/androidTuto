package com.example.tuto.repository;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tuto.CallBack;
import com.example.tuto.FirebaseInstance;
import com.example.tuto.adapters.PlantAdapter;
import com.example.tuto.model.PlantModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlantRepository {

    FirebaseInstance fireBase = FirebaseInstance.getInstance();
    DatabaseReference databaseReference = fireBase.getDatabaseReference();
    StorageReference storage = fireBase.getStorageReference();

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
                fireBase.getPlantList().clear();
                fireBase.getPlantListLiked().clear();
                fireBase.getPlantListNotLiked().clear();
                for (DataSnapshot child:snapshot.getChildren()){
                    PlantModel plant = child.getValue(PlantModel.class);

                    if (plant != null){
                        fireBase.getPlantList().add(plant);
                        if (plant.getLiked())   fireBase.getPlantListLiked().add(plant);
                        else fireBase.getPlantListNotLiked().add(plant);
                    }
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

    public void insertPlant(PlantModel plantModel){
        databaseReference.child(plantModel.getId()).setValue(plantModel);
    }

    public void deletePlant(PlantModel plantModel){
        databaseReference.child(plantModel.getId()).removeValue();
    }

//    fonction pour envoyer des fichiers sur le storage firebase
    public void uploadImage(Uri uri, CallBack callBack){
        if (uri!=null){
            String fileName = UUID.randomUUID().toString() + ".jpg";
            StorageReference ref = storage.child(fileName);
            UploadTask uploadTask = ref.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            callBack.onUploadPlant(uri);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
