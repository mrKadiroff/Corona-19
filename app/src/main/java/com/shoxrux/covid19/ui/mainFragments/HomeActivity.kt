package com.shoxrux.covid19.ui.mainFragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.shoxrux.covid19.R
import com.shoxrux.covid19.databinding.ActivityHomeBinding
import com.shoxrux.covid19.db.SavedEntity
import com.shoxrux.covid19.utils.resource.Status
import com.shoxrux.covid19.viewmodel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),CoroutineScope {

    val viewModel: OfflineViewModel by viewModels()
    private val TAG = "HomeActivity"
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        insertToDB()




    }

    private fun insertToDB() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getBookmark().observe(this@HomeActivity) {

                when (it.status) {
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                    Status.SUCCESS -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        val data = it.data
                        if (data.isNullOrEmpty()){
                            viewModel.insertArticle(savedEntity = SavedEntity(0,"saved"))
                        }else{
                            Toast.makeText(this@HomeActivity, "Malumot bor", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
        }

    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}