package com.shoxrux.covid19.ui.mainFragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.shoxrux.covid19.R
import com.shoxrux.covid19.databinding.ActivityHomeBinding
import com.shoxrux.covid19.db.SavedEntity
import com.shoxrux.covid19.utils.resource.Status
import com.shoxrux.covid19.viewmodel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),CoroutineScope {

    val viewModel: OfflineViewModel by viewModels()
    private val TAG = "HomeActivity"
    lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_200))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)
        navController = findNavController(R.id.hostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.statysticsFragment,
                R.id.symptomsFragment,
                R.id.preventationFragment,
                R.id.articleFragment,
                R.id.newsFragment,
                R.id.helpFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbarMain,
            R.string.open, R.string.close
        )

        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        binding.toolbarMain.setTitle(null)
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false);


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