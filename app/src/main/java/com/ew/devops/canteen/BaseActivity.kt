package com.ew.devops.canteen

import android.app.ProgressDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage(getString(R.string.loading))
            progressDialog!!.isIndeterminate = true
        }
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
}
