package io.github.c23pr487.lapakin.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.ActivityAuthenticationBinding
import io.github.c23pr487.lapakin.ui.MainActivity

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val binding: ActivityAuthenticationBinding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Authentication", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Firebase.auth.currentUser?.let { user ->
            updateUI(user)
        }

        drawUxWriting()

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun drawUxWriting() {
        val spannableString = SpannableString(getString(R.string.tagline))
        spannableString.setSpan(
            ForegroundColorSpan(
                ResourcesCompat.getColor(resources, R.color.primary_dark_blue, theme)
            ),
            18,
            23,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(
                ResourcesCompat.getColor(resources, R.color.primary_light_red, theme)
            ),
            23,
            25,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            StyleSpan(Typeface.ITALIC),
            18,
            25,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val typeface = ResourcesCompat.getFont(this, R.font.sriracha_regular)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            typeface?.let {
                spannableString.setSpan(
                    TypefaceSpan(it),
                    18,
                    25,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        spannableString.setSpan(
            RelativeSizeSpan(1.2f),
            18,
            25,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textViewTagline.text = spannableString

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    if (task.result.additionalUserInfo?.isNewUser == true) {
                        updateUI(user, true)
                        return@addOnCompleteListener
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

            }
    }

    private fun updateUI(currentUser: FirebaseUser?, newUser: Boolean = false) {
        if (currentUser != null && !newUser) {
            startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
            finish()
            return
        }

        if (currentUser != null) {
            startActivity(Intent(this, PreferencesActivity::class.java))
            finish()
            return
        }

        Snackbar.make(
            this,
            binding.root,
            getString(R.string.login_failed_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}