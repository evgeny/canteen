package com.ew.devops.canteen.utils

import android.support.design.widget.NavigationView
import android.widget.TextView
import com.ew.devops.canteen.R

var NavigationView.title: CharSequence
    get() = this.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_title).text
    set(value) {
        this.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_title).text = value
    }

var NavigationView.subtitle: CharSequence
    get() = this.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_subtitle).text
    set(value) {
        this.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_subtitle).text = value
    }
