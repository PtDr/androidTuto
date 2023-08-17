package com.example.tuto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PlantModel {

    private String id = "plant0";
    private String name = "Tulipe";
    private String description = "Petite description";
    private String imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.gammvert.fr%2Fconseils%2Fconseils-de-jardinage%2Ftulipe&psig=AOvVaw0YFP-2ayUwki3N99hr5tcl&ust=1692273694353000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCLiD9_aQ4YADFQAAAAAdAAAAABAE";
    private Boolean liked = false;
    private String grow = "Lente";
    private String water = "Faible";
}
