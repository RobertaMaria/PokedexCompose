package com.example.pokedex.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.dao.RemoteKeysDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.details.data.database.dao.PokemonEvolutionDao
import com.example.pokedex.details.data.database.dao.PokemonSpecieDao
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity

@Database(entities = [PokemonEntity::class, RemoteKeysEntity::class, PokemonSpecieEntity::class, PokemonEvolutionEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun pokemonSpecieDao(): PokemonSpecieDao
    abstract fun pokemonEvolutionDao(): PokemonEvolutionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}