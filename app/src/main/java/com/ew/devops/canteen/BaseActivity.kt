package com.ew.devops.canteen

import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inject dagger components
        inject()

        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage(getString(R.string.loading))
            progressDialog!!.isIndeterminate = true
        }
    }

    fun setStatusBarColor(@ColorRes colorRes: Int) {
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, colorRes)
    }

    fun showProgressDialog() {
        progressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    fun getUid(): String {
        FirebaseAuth.getInstance().currentUser?.let {
            return it.uid
        }

        return ""
    }

    fun getUserName(): String? {
        FirebaseAuth.getInstance().currentUser?.let {
            return it.displayName
        }

        return null
    }

    /**
     * inject needed components or leave the method body empty
     */
    abstract fun inject()
}
