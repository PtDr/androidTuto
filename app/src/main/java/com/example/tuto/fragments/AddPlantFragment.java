package com.example.tuto.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tuto.CallBack;
import com.example.tuto.MainActivity;
import com.example.tuto.R;
import com.example.tuto.model.PlantModel;
import com.example.tuto.repository.PlantRepository;

import java.util.UUID;

public class AddPlantFragment extends Fragment {

    private final MainActivity context;
    private ActivityResultLauncher<Intent> launcher;
    private ImageView uploadedImage;
    private Uri fileUri;

    public AddPlantFragment(MainActivity context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater!=null){
            View view = inflater.inflate(R.layout.fragment_add_plant, container, false);
            //recuperer uploadedImage pour lui associer son composant
            uploadedImage = view.findViewById(R.id.preview_image);

            //utiliser pour ouvrier l'importation de fichier local
            launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        fileUri = result.getData().getData();

                        if (uploadedImage!=null) uploadedImage.setImageURI(fileUri);
                    }
                }
            });

            //recuperer le bouton pour charger image
            Button pickupImageButton = view.findViewById(R.id.upload_button);
            //lorque qu'on clique ca ouvre  les images du phone
            pickupImageButton.setOnClickListener(pickupImage());

            Button confirmButton = view.findViewById(R.id.confirm_button);
            confirmButton.setOnClickListener(sendForm(view));

            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private View.OnClickListener sendForm(View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //heberger l'image sur bucket firebase
                PlantRepository plantRepository = new PlantRepository();
                plantRepository.uploadImage(fileUri, new CallBack() {
                    @Override
                    public void onUploadPlant(Uri uri) {
                        String plantName = ((EditText) view.findViewById(R.id.name_input)).getText().toString();
                        String plantDescription = ((EditText) view.findViewById(R.id.description_input)).getText().toString();
                        String grow = ((Spinner) view.findViewById(R.id.grow_spinner)).getSelectedItem().toString();
                        String water = ((Spinner) view.findViewById(R.id.water_spinner)).getSelectedItem().toString();
                        String downloadImageUrl = uri.toString();

                        //creer un objet plant model
                        PlantModel plantModel = new PlantModel(UUID.randomUUID().toString(),
                                plantName,
                                plantDescription,
                                downloadImageUrl,
                                false,
                                grow,
                                water);
                        plantRepository.insertPlant(plantModel);
                    }
                });
            }
        };
    }

    private View.OnClickListener pickupImage() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        };
    }
}
