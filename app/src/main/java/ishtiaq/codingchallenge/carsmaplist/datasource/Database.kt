package ishtiaq.codingchallenge.carsmaplist.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import ishtiaq.codingchallenge.carsmaplist.model.Car


@Dao
interface CarDao {
    @Query("select * from Car")
    fun getCars(): LiveData<List<Car>>
}

@Database(entities = [Car::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract val carDao: CarDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "cars.db"
            ).build()
        }
    }

    return INSTANCE
}




