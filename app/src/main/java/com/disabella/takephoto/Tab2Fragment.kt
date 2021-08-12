package com.disabella.takephoto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.io.File
import java.util.*


class Tab2Fragment : Fragment() {

    private val authority = "com.disabella.takephoto.fileprovider"
    private var currentPhotoPath: String? = null
    private lateinit var imageView: ImageView

    private val contract = ActivityResultContracts.StartActivityForResult()

    private val resultLauncher = registerForActivityResult(contract) {
        if (it.resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(currentPhotoPath).into(imageView)
        }
    }


    /*lateinit var description: EditText*/
    lateinit var savePhoto: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_tab2, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageView = view.findViewById(R.id.imageView)
        val description = view.findViewById<EditText>(R.id.description)
        savePhoto = view.findViewById(R.id.savePhoto)

        val sharedPref = activity?.getSharedPreferences("main", Context.MODE_PRIVATE)

        Glide.with(this)
            .load(URL)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(imageView)

        view.findViewById<Button>(R.id.takePhoto)?.setOnClickListener {
            takePicture()
        }

        savePhoto.setOnClickListener {
            when {
                description.text.isEmpty() -> {
                    Toast.makeText(activity, "You have to write description", Toast.LENGTH_SHORT)
                        .show()
                }
                currentPhotoPath == null -> {
                    Toast.makeText(activity, "Firstly you have to take photo", Toast.LENGTH_SHORT)
                        .show()
                }
                description.text.isNotEmpty() && currentPhotoPath != null -> {
                    DataStorage.saveItem(MyItem(currentPhotoPath ?: "", description.text.toString()))
                    Toast.makeText(activity, "Great work", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun takePicture() {
        val randomName = UUID.randomUUID().toString()
        val file = createImageFile(randomName).apply {
            currentPhotoPath = absolutePath
        }
        val photoURI = FileProvider.getUriForFile(App.appContext, authority, file)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        resultLauncher.launch(intent)
        Log.i("my_log", "mess: $file")
    }

    private fun createImageFile(name: String) =
        File.createTempFile(
            name, ".jpg",
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

}