package com.thkoeln.kmm_project.android

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import com.thkoeln.kmm_project.android.databinding.ActivityMain2Binding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val userName = intent.getStringExtra("userName")

        val navUserName = navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_user_name)
        navUserName.text = userName


        // TODO use user name to get liked tweets

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tweet -> {
                    val bundle = bundleOf("userName" to userName)
                    this.findNavController(navView.id).navigate(R.id.fragment_home, bundle)

                }
                R.id.nav_logout ->
                    startActivity(Intent(this, LoginActivity::class.java))
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }

    }


}