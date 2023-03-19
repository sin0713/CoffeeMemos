package com.withapp.coffeememo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.withapp.coffeememo.Constants
import com.withapp.coffeememo.databinding.ActivityMainBinding
import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.entity.Taste
import com.withapp.coffeememo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val database = (application as CoffeeMemosApplication).database
        val recipeDao = database.recipeDao()
        val beanDao = database.beanDao()
        val tasteDao = database.tasteDao()



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        // bottomNavigationとNavControllerの関連付け
        binding.bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.homeRecipeFragment -> {
                    showBottomNav()
                }
                R.id.homeBeansFragment -> {
                    showBottomNav()
                }
                R.id.timerFragment -> {
                    showBottomNav()
                }
                R.id.searchFragment -> {
                    showBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }

        // timer画面のボトムナビゲーション 監視
        viewModel.newRecipeFragmentExists.observe(this) { exists ->
            if (exists) hideBottomNav()
        }

        // データベース初期化
        if (Constants.databaseResetFlag) {
            GlobalScope.launch {
                recipeDao.clearTable()
                beanDao.clearTable()
                tasteDao.clearTable()

//                beanDao.insert(Constants.sampleBean)
//                beanDao.insert(Constants.sampleBean2)
//                beanDao.insert(Constants.sampleBean3)
//                beanDao.insert(Constants.sampleBean4)
//                beanDao.insert(Constants.sampleBean5)
//
//                val beans = beanDao.getAll()
//                for (bean in beans) {
//                    val recipeList: MutableList<Recipe> = mutableListOf()
//                    for (i in 0 until 60) {
//                        Constants.sampleRecipe1.beanId = bean.id
//                        Constants.sampleRecipe2.beanId = bean.id
//                        Constants.sampleRecipe1.country = bean.country
//                        Constants.sampleRecipe2.country = bean.country
//                        recipeList.add(Constants.sampleRecipe1)
//                        recipeList.add(Constants.sampleRecipe2)
//                    }
//                    recipeDao.insertAll(recipeList)
//                }
//
//                val recipeIds: List<Long> = recipeDao.getIds()
//                for ((index, id) in recipeIds.withIndex()) {
//                    val value = (index % 5) + 1
//                    tasteDao.insert(
//                        Taste(
//                            0,
//                            id,
//                            value,
//                            value,
//                            value,
//                            value,
//                            value
//                        )
//                    )
//                }
//
//                Log.d("initializeDataProcess", "==========================================")
//                Log.d("initializeDataProcess", "finish")
//                Log.d("initializeDataProcess", "==========================================")
//            }
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavBar.visibility = View.GONE
    }
}