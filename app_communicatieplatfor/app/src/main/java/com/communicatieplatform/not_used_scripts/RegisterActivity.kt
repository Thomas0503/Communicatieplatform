package com.communicatieplatform.not_used_scripts


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.communicatieplatform.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

//errors in code, daarom staat alles in commentaar
/*/*/*
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.RegisterButton.setOnClickListener {
            register()

        }

        binding.alreadyHaveAccountTextView.setOnClickListener {
            Log.d("MainActivity", "Try to shcow login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        /*
        binding.selectphotoButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)


        } */*/

    }
    /*
    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            binding.circleimage.setImageBitmap(bitmap)

            binding.selectphotoButton.alpha = 0f


        }
    }
    */

    private fun register(){
        val email = binding.editTextTextEmail.text.toString()
        val password = binding.editTextTextPassword.text.toString()
        val username = binding.editTextUsername.text.toString()

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Password is: $password")

        if (email.isEmpty()){
            Toast.makeText(this, "Please enter email!!", Toast.LENGTH_SHORT).show()

            return
        }

        if (password.isEmpty()){
            Toast.makeText(this, "Please enter password!!", Toast.LENGTH_SHORT).show()

            return
        }

        if (username.isEmpty()){
            Toast.makeText(this, "Please enter username!!", Toast.LENGTH_SHORT).show()

            return
        }

        if (password.length < 6){
            Toast.makeText(this, "This password is to short, enter new one!!", Toast.LENGTH_LONG).show()
            return
        }




        //Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")

                //uploadImageToFirebaseStorage()
                saveUserToFirebaseDatabase()
                val intent = Intent(this, Homepage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }

    /*
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) {
            Toast.makeText(this, "No Photo selected", Toast.LENGTH_SHORT).show()
        }

        val filename = UUID.randomUUID().toString()

        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }*/

    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""

        val db = Firebase.firestore

        val user = hashMapOf(
            "uid" to uid,
            "name" to binding.editTextUsername.text.toString(),
        )


        //val user = User(uid, binding.editTextTextPersonName.text.toString(), image)

        db.collection("usersTestLogin").document(uid)
            .set(user)

            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }




}

*/*/*/