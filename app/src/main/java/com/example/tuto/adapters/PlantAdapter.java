package com.example.tuto.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuto.MainActivity;
import com.example.tuto.R;
import com.example.tuto.model.PlantModel;
import com.example.tuto.popup.PlantePopup;
import com.example.tuto.repository.PlantRepository;

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private Integer layoutId;
    private List<PlantModel> plantList;
    private MainActivity context;
    private PlantRepository plantRepository;

    public PlantAdapter(MainActivity context, List<PlantModel> plantList, Integer layoutId, PlantRepository plantRepository){
        this.context = context;
        this.plantList = plantList;
        this.layoutId = layoutId;
        this.plantRepository = plantRepository;
        this.plantRepository.getAdapters().add(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.layoutId, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantModel currentPlant = this.plantList.get(position);
//        utiliser glide  pour recuperer  l'image à partir  de son lien -> composant
        Glide.with(this.context).load(Uri.parse(currentPlant.getImageUrl())).into(holder.getPlantImage());
//        mettre à jour le nom de la plante
        if (holder.getPlantName()!=null) holder.getPlantName().setText(currentPlant.getName());
//        mettre à jour la description de la plante
        if (holder.getPlantDescription()!=null) holder.getPlantDescription().setText(currentPlant.getDescription());
//        mettre à jour le like
        if (currentPlant.getLiked()){
            holder.getStarIcon().setImageResource(R.drawable.ic_like);
        }else{
            holder.getStarIcon().setImageResource(R.drawable.ic_unlike);
        }
//        ajout interaction sur l'etoile
        holder.getStarIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPlant.setLiked(!currentPlant.getLiked());
                plantRepository.updatePlant(currentPlant);
            }
        });

        //interation lors du click sur plante pour voir detail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afficher la popup
                new PlantePopup(context, currentPlant).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    @Getter
    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView plantImage;
        private TextView plantName;
        private TextView plantDescription;
        private ImageView starIcon;

//        boite pour ranger  tout les composant à controller
        public ViewHolder(View view){
            super(view);
            this.plantImage = view.findViewById(R.id.image_item);
            this.plantName = view.findViewById(R.id.name_item);
            this.plantDescription = view.findViewById(R.id.description_item);
            this.starIcon = view.findViewById(R.id.star_icon);
        }
    }
}
