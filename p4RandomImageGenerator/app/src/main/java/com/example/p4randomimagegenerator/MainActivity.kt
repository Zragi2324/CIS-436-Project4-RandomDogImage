package com.example.p4randomimagegenerator

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.p4randomimagegenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FirstFragment.ImageFetchListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.imageUrl.observe(this, Observer { imageUrl ->
            // Pass the imageUrl to Fragment2 for display
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, SecondFragment.newInstance(imageUrl))
                .commit()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun setImageUrlF2(imageUrl: String) {
        val fragment2 =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? SecondFragment
        fragment2?.updateImageUrl(imageUrl)

    }

    override fun onImageFetched(imageUrl: String) {
        setImageUrlF2(imageUrl)
    }
}
