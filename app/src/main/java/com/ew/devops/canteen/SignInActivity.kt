package com.ew.devops.canteen

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject


class SignInActivity : BaseActivity(), View.OnClickListener {

    @Inject lateinit var mAuth: FirebaseAuth

    // [START declare_auth_listener]
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    // [END declare_auth_listener]

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setStatusBarColor(R.color.light_green_300)

        // Buttons
        email_sign_in_button.setOnClickListener(this)
        email_create_account_button.setOnClickListener(this)
        sign_out_button.setOnClickListener(this)
        btn_display_name.setOnClickListener(this)

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
            // [START_EXCLUDE]
            updateUI(user)
            // [END_EXCLUDE]
        }
        // [END auth_state_listener]
    }

    // [START on_start_add_listener]
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    override fun onStop() {
        super.onStop()

        mAuth.removeAuthStateListener(mAuthListener)
    }
    // [END on_stop_remove_listener]

    /**
     * create a new user account
     */
    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:" + email)
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Toast.makeText(this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show()
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:" + email)
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithEmail:failed", task.exception)
                        Toast.makeText(this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show()
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful) {
                        status!!.setText(R.string.auth_failed)
                    }
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }

    /**
     * update user icon and name
     */
    private fun updateUserProfile() {
        val user = mAuth.currentUser ?: return

        showProgressDialog()
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(et_display_name.text.toString())
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()

        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile update is completed", Toast.LENGTH_SHORT).show()
                updateUI(mAuth.currentUser)
            } else {
                Log.e(TAG, "profile update failed")
            }

            hideProgressDialog()
        }
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = field_email!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            field_email!!.error = "Required."
            valid = false
        } else {
            field_email!!.error = null
        }

        val password = field_password!!.text.toString()
        if (TextUtils.isEmpty(password)) {
            field_password!!.error = "Required."
            valid = false
        } else {
            field_password!!.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) = //        hideProgressDialog()
            if (user != null) {
                status!!.text = user.email
                detail!!.text = user.displayName

                email_password_buttons.visibility = View.GONE
                email_password_fields.visibility = View.GONE
                profile_fields.visibility = View.VISIBLE
                sign_out_button.visibility = View.VISIBLE
            } else {
                status!!.setText(R.string.signed_out)
                detail!!.text = null

                email_password_buttons.visibility = View.VISIBLE
                email_password_fields.visibility = View.VISIBLE
                sign_out_button.visibility = View.GONE
                profile_fields.visibility = View.GONE
            }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.email_create_account_button -> createAccount(field_email!!.text.toString(), field_password!!.text.toString())
            R.id.email_sign_in_button -> signIn(field_email!!.text.toString(), field_password!!.text.toString())
            R.id.sign_out_button -> signOut()
            R.id.btn_display_name -> updateUserProfile()
        }
    }

    override fun inject() {
        CanteenApplication.appComponent.inject(this)
    }

    companion object {
        private val TAG = "EmailPassword"
    }
}