package com.ew.devops.canteen.utils

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.ew.devops.canteen.R

class UiUtils {
    companion object StaticMethods {
        
        @DrawableRes fun getCategoryDrawable(id: Int): Int = when (id) {
            631 -> R.drawable.ic_aubergine
            632 -> R.drawable.ic_rice
            633 -> R.drawable.ic_chicken
            634 -> R.drawable.ic_covering
            635 -> R.drawable.ic_fries
            else -> R.drawable.ic_aubergine
        }


        @ColorRes fun getCategoryColor(id: Int): Int = when (id) {
            631 -> R.color.colorTraditional
            632 -> R.color.colorTraditional
            633 -> R.color.colorCulinary
            634 -> R.color.colorSoup
            635 -> R.color.colorDaily
            else -> R.color.colorTraditional
        }
    }
}
