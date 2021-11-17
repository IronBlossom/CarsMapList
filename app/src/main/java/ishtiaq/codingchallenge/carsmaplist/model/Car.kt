package ishtiaq.codingchallenge.carsmaplist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Car(
    @PrimaryKey
    @Json(name = "id") val carId: String,
    @Json(name = "modelIdentifier") val modelId: String,
    @Json(name = "modelName") val modelName: String,
    @Json(name = "name") val carName: String,
    @Json(name = "make") val manufacturer: String,
    @Json(name = "group") val group: String,
    @Json(name = "color") val color: String,
    @Json(name = "series") val series: String,
    @Json(name = "fuelType") val fuelType: String,
    @Json(name = "fuelLevel") val fuelLevel: Double,
    @Json(name = "transmission") val transmission: String,
    @Json(name = "licensePlate") val licensePlate: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "innerCleanliness") val cleanliness: String,
    @Json(name = "carImageUrl") val carImage: String,
)
