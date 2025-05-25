package com.example.planetapp.models

import com.example.planetapp.R

data class Planet(
    val id: Int,
    val name: String,
    val type: String,
    val galaxy: String,
    val distanceFromSun: String,
    val diameter: String,
    val characteristics: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

val planetList = listOf(
    Planet(
        id = 1,
        name = "Mercúrio",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "57,9 milhões de km",
        diameter = "4.879 km",
        characteristics = "Menor planeta, sem atmosfera, temperaturas extremas.",
        imageRes = R.drawable.mercurio
    ),
    Planet(
        id = 2,
        name = "Vênus",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "108,2 milhões de km",
        diameter = "12.104 km",
        characteristics = "Planeta mais quente, atmosfera densa com nuvens de ácido sulfúrico.",
        imageRes = R.drawable.venus
    ),
    Planet(
        id = 3,
        name = "Terra",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "149,6 milhões de km",
        diameter = "12.742 km",
        characteristics = "Suporta vida, possui água e atmosfera.",
        imageRes = R.drawable.terra
    ),
    Planet(
        id = 4,
        name = "Marte",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "227,9 milhões de km",
        diameter = "6.779 km",
        characteristics = "Planeta vermelho, possui o maior vulcão do sistema solar.",
        imageRes = R.drawable.marte
    ),
    Planet(
        id = 5,
        name = "Júpiter",
        type = "Gigante gasoso",
        galaxy = "Via Láctea",
        distanceFromSun = "778,5 milhões de km",
        diameter = "139.820 km",
        characteristics = "Maior planeta, possui a Grande Mancha Vermelha, muitas luas.",
        imageRes = R.drawable.jupiter
    ),
    Planet(
        id = 6,
        name = "Saturno",
        type = "Gigante gasoso",
        galaxy = "Via Láctea",
        distanceFromSun = "1,43 bilhões de km",
        diameter = "116.460 km",
        characteristics = "Famoso por seu extenso sistema de anéis, baixa densidade.",
        imageRes = R.drawable.saturno
    ),
    Planet(
        id = 7,
        name = "Urano",
        type = "Gigante gelado",
        galaxy = "Via Láctea",
        distanceFromSun = "2,87 bilhões de km",
        diameter = "50.724 km",
        characteristics = "Gira de lado, possui anéis tênues, cor azulada devido ao metano.",
        imageRes = R.drawable.urano
    ),
    Planet(
        id = 8,
        name = "Netuno",
        type = "Gigante gelado",
        galaxy = "Via Láctea",
        distanceFromSun = "4,5 bilhões de km",
        diameter = "49.244 km",
        characteristics = "Ventos mais fortes do sistema solar, cor azul intensa.",
        imageRes = R.drawable.netuno
    )
)
