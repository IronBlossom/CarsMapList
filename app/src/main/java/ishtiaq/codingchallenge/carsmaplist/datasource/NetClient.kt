package ishtiaq.codingchallenge.carsmaplist.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ishtiaq.codingchallenge.carsmaplist.model.Car
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://cdn.sixt.io"

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface CarInterface {
    @GET("codingtask/cars")
    suspend fun getCars(@Query("fuelLevel") fuelLevel: Double): List<Car>
}

object CarListApi { // Making the httpclient singleton
    val carInterface: CarInterface by lazy {
        retrofit.create(CarInterface::class.java)
    }
}

