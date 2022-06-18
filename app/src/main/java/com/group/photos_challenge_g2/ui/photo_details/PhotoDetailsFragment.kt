package com.group.photos_challenge_g2.ui.photo_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.group.photos_challenge_g2.R
import com.group.photos_challenge_g2.databinding.FragmentPhotoDetailsBinding
import com.group.photos_challenge_g2.utils.BaseFragment
import com.group.photos_challenge_g2.utils.Constants
import com.group.photos_challenge_g2.utils.PhotoLoaderListener
import com.group.photos_challenge_g2.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max


@AndroidEntryPoint
class PhotoDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f

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

        setUp()
        ui()

    }

    private fun ui() {

        scaleGestureDetector = ScaleGestureDetector(mContext, ScaleListener())

        binding.imgView.loadImage(
            requireArguments().getString(Constants.PHOTO_URL)!!,
            object : PhotoLoaderListener {
                override fun onLoadFinished() {

                    binding.progressBar.visibility = View.GONE

                }
            })

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUp() {

        binding.imgView.setOnTouchListener { _, p1 ->
            scaleGestureDetector!!.onTouchEvent(p1)
            true
        }
    }


    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = max(0.1f, mScaleFactor.coerceAtMost(10.0f))
            binding.imgView.scaleX = mScaleFactor
            binding.imgView.scaleY = mScaleFactor
            return true
        }
    }

}