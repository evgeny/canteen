package com.ew.devops.canteen

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.ew.devops.canteen.utils.subtitle
import com.ew.devops.canteen.utils.title
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MenuActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun inject() {
        // nothing here
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        tabs.tabMode = TabLayout.MODE_SCROLLABLE

        val adapter = DayMenuAdapter(supportFragmentManager)
        pager.adapter = adapter

        tabs.setupWithViewPager(pager)
    }

    override fun onResume() {
        super.onResume()

        setDrawerUserName()
    }

    override fun onBackPressed() {
        val drawer = drawer_layout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_camera -> {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setDrawerUserName() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null || user.displayName == null) {
            nav_view.title = "Anonymous"
        } else {
            nav_view.title = user.displayName!!
        }

        if (user != null && user.email != null) {
            nav_view.subtitle = user.email!!
        }
    }
}
