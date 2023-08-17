package com.example.tuto.popup;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.tuto.R;
import com.example.tuto.model.PlantModel;
import com.example.tuto.repository.PlantRepository;

public class PlantePopup extends Dialog {

    private PlantModel currentPlant;

    public PlantePopup(@NonNull Context context, PlantModel currentPlant) {
        super(context);
        this.currentPlant = currentPlant;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_plants_details);
        setupComponents();
        setupCloseButton();
        setupDeleteButton();
        setupStarButton();
    }

    private void updateStar(ImageView starButton){
        if (currentPlant.getLiked()){
            starButton.setImageResource(R.drawable.ic_like);
        }else{
            starButton.setImageResource(R.drawable.ic_unlike);
        }
    }

    private void setupStarButton() {
        ImageView starButton = findViewById(R.id.star_button);

        updateStar(starButton);

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlantRepository plantRepository = new PlantRepository();
                currentPlant.setLiked(!currentPlant.getLiked());
                plantRepository.updatePlant(currentPlant);
                updateStar(starButton);
            }
        });
    }

    private void setupDeleteButton() {
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //supprimer la plante de la base
                PlantRepository plantRepository = new PlantRepository();
                plantRepository.deletePlant(currentPlant);
                dismiss();
            }
        });
    }

    private void setupCloseButton() {
        findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fermer la fenetre popup
                dismiss();
            }
        });
    }

    private void setupComponents() {
        //actualiser l'image de la plante
        ImageView plantImage = findViewById(R.id.image_item);
        Glide.with(super.getContext()).load(Uri.parse(currentPlant.getImageUrl())).into(plantImage);

        TextView plantName = findViewById(R.id.popup_plant_name);
        plantName.setText(currentPlant.getName());

        TextView plantDescription = findViewById(R.id.popup_plant_description_subtitle);
        plantDescription.setText(currentPlant.getDescription());

        TextView plantGrow = findViewById(R.id.popup_plant_grow_subtitle);
        plantGrow.setText(currentPlant.getGrow());

        TextView plantWater = findViewById(R.id.popup_plant_water_subtitle);
        plantWater.setText(currentPlant.getWater());
    }
}
