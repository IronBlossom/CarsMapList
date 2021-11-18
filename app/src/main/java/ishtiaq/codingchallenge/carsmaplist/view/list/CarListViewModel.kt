package ishtiaq.codingchallenge.carsmaplist.view.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ishtiaq.codingchallenge.carsmaplist.datasource.getDatabase
import kotlinx.coroutines.launch
import java.io.IOException

enum class ApiStatus { LOADING, DONE, ERROR }
class CarListViewModel(application: Application) : AndroidViewModel(application) {

    val repo = CarListRepository(getDatabase(application))


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status


    val cars = repo.getCarsFromDB()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val tempCars = repo.getCarsFromServer()
                repo.insertCarsToDB(tempCars)
                _status.value = ApiStatus.DONE
            } catch (networkError: IOException) {
                networkError.printStackTrace()
                _status.value = ApiStatus.ERROR
            }
        }
    }

}