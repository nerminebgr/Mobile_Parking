package com.example.project.interfaces

import com.example.project.R

data class Parking(
    val nom:String,
    val commune:String,
    val adresse:String,
    val prix:String,
    val dispo:String,
    val distance:String,
    val places:Int,
    val img:Int

)

fun getData():List<Parking> {
    val parkings = ArrayList<Parking>()
    for(i in 0..4) {
        parkings.add(
            Parking(
                nom[i],
                commune[i],
                adresse[i],
                prix[i],
                dispo[i],
                distance[i],
                places[i],
                imageIds[i])
        )
    }
    return parkings
}


val nom = listOf(
    "Parking Résidentiel",
    "Parking Université",
    "Parking Mosquée",
    "Parking Hôpital",
    "Parking Centre Commercial"
)

val places = listOf(
    1,1,1,1,1
)

val commune = listOf(
    "Alger Centre",
    "Boumerdès",
    "Constantine",
    "Oran",
    "Annaba"
)

val adresse = listOf(
    "Rue Emir Abdelkader, Alger Centre",
    "Rue Tahar, Boumerdès",
    "Rue Palestine, Constantine",
    "Rue des Martyrs, Oran",
    "Rue des Martyrs, Annaba"
)

val prix = listOf(
    "200 DA/h",
    "150 DA/h",
    "180 DA/h",
    "220 DA/h",
    "250 DA/h"
)

val dispo = listOf(
    "Disponible",
    "Complet",
    "Disponible",
    "Disponible",
    "Disponible"
)

val distance = listOf(
    "À 0.3 km",
    "À 0.5 km",
    "À 0.2 km",
    "À 0.8 km",
    "À 0.4 km"
)

val imageIds = listOf(
    R.drawable.p1,
    R.drawable.p2,
    R.drawable.p1,
    R.drawable.p2,
    R.drawable.p1,
)
