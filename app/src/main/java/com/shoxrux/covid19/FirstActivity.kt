package com.shoxrux.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.shoxrux.covid19.ui.mainFragments.HomeActivity
import com.shoxrux.covid19.utils.resource.Status
import com.shoxrux.covid19.viewmodel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class FirstActivity : AppCompatActivity(),CoroutineScope {
    val viewModelOffline: OfflineViewModel by viewModels()
    private val TAG = "FirstActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)


        GlobalScope.launch(Dispatchers.Main) {
            viewModelOffline.getBookmark().observe(this@FirstActivity) {

                when (it.status) {
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                    Status.SUCCESS -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        val data = it.data
                        if (!data.isNullOrEmpty()){
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                            finish()
                        }else{
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    }
                }

            }
        }



    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}