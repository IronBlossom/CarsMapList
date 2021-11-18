package ishtiaq.codingchallenge.carsmaplist.view.list

import ishtiaq.codingchallenge.carsmaplist.datasource.AppDatabase
import ishtiaq.codingchallenge.carsmaplist.datasource.CarListApi
import ishtiaq.codingchallenge.carsmaplist.model.Car


class CarListRepository(private val database: AppDatabase) {

    suspend fun getCarsFromServer() = CarListApi.carInterface.getCars(0.0)

    fun getCarsFromDB() = database.carDao.getCars()

    suspend fun insertCarsToDB(cars: List<Car>) = database.carDao.insertAll(cars)
}