package android.example.dogs.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.dogs.R
import android.example.dogs.Util.getProgressDrawble
import android.example.dogs.Util.loadImage
import android.example.dogs.databinding.FragmentDetailBinding
import android.example.dogs.model.DogPalette
import android.example.dogs.viewModel.DetailViewModel
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogUuid = 0
    private lateinit var  viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel  = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }
        viewModel.fetch(dogUuid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer { dog->
            dog?.let {
                dataBinding.dog = dog
                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val initColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = DogPalette(initColor)
                            dataBinding.palette = myPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}