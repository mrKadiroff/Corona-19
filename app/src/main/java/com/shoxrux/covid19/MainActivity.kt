package com.shoxrux.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.shoxrux.covid19.databinding.ActivityMainBinding
import com.shoxrux.covid19.models.offline.OnboardingItem
import com.shoxrux.covid19.ui.adapters.OnboardingItemsAdapter
import com.shoxrux.covid19.ui.mainFragments.HomeActivity
import com.shoxrux.covid19.utils.DataHandler
import com.shoxrux.covid19.utils.resource.Status
import com.shoxrux.covid19.viewmodel.OfflineViewModel
import com.shoxrux.covid19.viewmodel.OnlineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

   

    val viewModel: OnlineViewModel by viewModels()
    val viewModelOffline: OfflineViewModel by viewModels()
    private val TAG = "MainActivity"

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)







//        viewModel.topHeadlines.observe(this, { dataHandler ->
//            when (dataHandler) {
//                is DataHandler.SUCCESS -> {
//                    Log.d(TAG, "onCreate: ${dataHandler.data?.articles}")
//                }
//                is DataHandler.ERROR -> {
//                    Log.d(TAG, "onCreate: ${dataHandler.message}")
//                }
//                is DataHandler.LOADING -> {
//
//
//                }
//            }
//
//        })
//        viewModel.getTopHeadlines()

        
    }

    private fun setOnboardingItems() {
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingItem = R.drawable.birinshis,
                    title = "Wear a mask",
                    description = "A cloth mask is intended to trap respiratory droplets that are released when the wearer talks, coughs or sneezes",
                    button = "Get Started"
                ),
                OnboardingItem(
                    onboardingItem = R.drawable.ikkinshi,
                    title = "Hand Wash & Sanitize",
                    description = "Soap and water work to remove all types of germs from hands, while sanitizer acts by killing certain germs on the skin",
                    button = "Next"
                ),
                OnboardingItem(
                    onboardingItem = R.drawable.ushinshi,
                    title = "Pyshical Distancing",
                    description = "Physical distancing is a prevention method to slow the person-to-person transmission of COVID-19. Currently, there is no vaccine available to prevent the novel coronavirus, so the best way to prevent illness from COVID-19 is to avoid being exposed to it.Aug 11, 2021",
                    button = "Next"
                )
            )
        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPAger2)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
                findViewById<MaterialButton>(R.id.buttonGetStarted).setText("Next")
            }else {
                navigateToHomeActivity()
            }
        }


    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(applicationContext,HomeActivity::class.java))
        finish()
    }

    private fun setCurrentIndicator(position:Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

}