package com.ew.devops.canteen.utils

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.ew.devops.canteen.R

/**
 * Created by Evgeny on 27-Jan-17.
 */
class UiUtils {
    companion object StaticMethods {
        
        @DrawableRes fun getCategoryDrawable(id: Int): Int {
            when (id) {
                631 -> return R.drawable.ic_aubergine
                632 -> return R.drawable.ic_rice
                633 -> return R.drawable.ic_chicken
                634 -> return R.drawable.ic_covering
                635 -> return R.drawable.ic_fries
                else -> return R.drawable.ic_aubergine
            }
        }


        @ColorRes fun getCategoryColor(id: Int): Int {
            when (id) {
                631 -> return R.color.colorTraditional
                632 -> return R.color.colorTraditional
                633 -> return R.color.colorCulinary
                634 -> return R.color.colorSoup
                635 -> return R.color.colorDaily
                else -> return R.color.colorTraditional
            }
        }
    }
}
