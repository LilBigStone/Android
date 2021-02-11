package com.karpov.lab3.profile

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.karpov.lab3.R
import com.karpov.lab3.databinding.ProfileFragmentBinding
import com.karpov.lab3.others.Constants.EMPTY
import com.karpov.lab3.others.Constants.PICOAMPERES
import java.math.BigInteger
import java.security.MessageDigest


class ProfileFragment : Fragment() {

    private val storage = Firebase.storage.reference
    private val currentUser = Firebase.auth.currentUser
    private var pathToFile: Uri? = null
    private var avatar: ImageView? = null

    private fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun updateImageView(uri: Uri, avatar: ImageView) {
        Glide.with(requireContext())
            .load(uri)
            .into(object : ViewTarget<ImageView, Drawable>(avatar) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    avatar.setImageDrawable(resource)
                }
            })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (requestCode == PICOAMPERES && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            pathToFile = data.data
            pathToFile?.let {
                avatar?.let { it1 -> updateImageView(it, it1) }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select"),
            PICOAMPERES
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: ProfileFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.profile_fragment, container, false
        )
        binding.lifecycleOwner = this
        var viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.email.text = currentUser!!.email
        binding.displayName.setText(currentUser.displayName)
        currentUser.photoUrl?.let { updateImageView(it, binding.avatar) }
        avatar = binding.avatar

        fun updateProfile(url: String) {
            viewModel.updateCurrentUser(url, binding.displayName.text.toString())
            updateImageView(Uri.parse(url), binding.avatar)
        }
        fun updatePhoto() {
            storage.child("avatars/${currentUser.email}").downloadUrl.addOnSuccessListener {
                viewModel.updateCurrentUser(it.toString(), binding.displayName.text.toString())
                updateImageView(it, binding.avatar)
            }
        }
        fun uploadImage() {
            if (pathToFile != null) {
                binding.CircularProgressIndicator.visibility = View.VISIBLE
                val reference: StorageReference = storage.child("avatars/" + currentUser.email)
                reference.putFile(pathToFile!!)
                    .addOnSuccessListener {
                        updatePhoto()
                        binding.CircularProgressIndicator.visibility = View.GONE
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    }

            }
        }
        binding.gravatar.setOnClickListener()
        {
            val url = "https://www.gravatar.com/avatar/${md5(currentUser.email.toString())}?s=350&d=identicon&r=PG"
            updateProfile(url)
        }
        binding.avatar.setOnClickListener()
        {
            selectImage()
        }
        binding.apply.setOnClickListener()
        {
            uploadImage()
            viewModel.updateCurrentUser(EMPTY, binding.displayName.text.toString())
        }
        return binding.root
    }

}