package com.ew.devops.canteen

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    /**
     * inject needed components or leave the method body empty
     */
    abstract fun inject()
}
