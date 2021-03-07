package com.orange.pokemon.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orange.pokemon.model.Pokemon

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val name: String,
    val reason: String,
    val evolvedfrom: String,
    val imageurl: String
)
{
    object ModelMapper {
        fun from(form: Pokemon) =
            PokemonEntity(0,form.name, form.reason, form.evolvedfrom,form.imageurl)
    }
}