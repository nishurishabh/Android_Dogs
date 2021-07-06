package android.example.dogs.viewModel

import android.example.dogs.model.DogBreed
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel() {

    val dog = MutableLiveData<DogBreed>()

    fun fetch() {
        val myDog = DogBreed("1",
            "German Shepard",
            "15 Years",
            "BreedGroup",
            "BredFor",
            "Aggressive",
            ""
        )

        dog.value = myDog
    }

}