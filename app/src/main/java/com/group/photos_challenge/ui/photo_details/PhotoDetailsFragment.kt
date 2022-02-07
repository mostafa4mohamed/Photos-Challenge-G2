package com.group.photos_challenge.ui.photo_details

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.group.photos_challenge.R
import com.group.photos_challenge.databinding.FragmentPhotoDetailsBinding
import com.group.photos_challenge.utils.Constants
import com.group.photos_challenge.utils.PhotoLoaderListener
import com.group.photos_challenge.utils.Utils

class PhotoDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_photo_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        ui()

        onBack()

    }

    private fun ui() {

        Utils.loadImage(binding.imgView,
            requireArguments().getString(Constants.PHOTO_URL)!!,
            object : PhotoLoaderListener {
                override fun onLoadFinished() {

                    binding.progressBar.visibility = View.GONE

                }
            })

    }


    private fun onBack() {

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event

                finish()
                Log.e(TAG, "handleOnBackPressed: 12")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun finish() {

       dismiss()

    }

    companion object {
        private val TAG = this::class.java.name
    }
}