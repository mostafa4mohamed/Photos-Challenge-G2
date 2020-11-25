package com.group.koinandcoroutiens.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.ActivityMainBinding
import com.group.koinandcoroutiens.pojo.response.PhotosResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    //    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        mainAdapter = MainAdapter()

        binding.rvMain.adapter = mainAdapter

        launch {
            mainViewModel.loadPhotosDataV1(binding)
//            mainViewModel.loadCategoryDataV2()
//            mainViewModel.loadCategoryDataV3()
        }
        mainViewModel.mutableLiveData.observe(this, { t ->

            mainAdapter.submitData(t as ArrayList<PhotosResponseItem>)
            binding.progressBar.visibility = GONE

        })


    }
}