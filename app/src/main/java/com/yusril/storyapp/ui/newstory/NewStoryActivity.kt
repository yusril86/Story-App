package com.yusril.storyapp.ui.newstory

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.yusril.storyapp.data.Result
import com.yusril.storyapp.data.local.LoginPrefs
import com.yusril.storyapp.databinding.ActivityNewStoryBinding
import com.yusril.storyapp.ui.story.StoryActivity
import com.yusril.storyapp.ui.story.StoryViewModel
import com.yusril.storyapp.ui.story.ViewModelFactoryStory
import com.yusril.storyapp.utils.Common
import com.yusril.storyapp.utils.Common.createCustomTempFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class NewStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStoryBinding
//    private val viewModel : NewStoryViewModel by viewModels()
    private val viewModel : StoryViewModel by viewModels(){
    ViewModelFactoryStory(this)
}



    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    companion object {

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10


        private const val REQUEST_EXTERNAL_STORAGE = 20

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }*/

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE
            )
        }

        binding.btnAddFoto.setOnClickListener{showPicDialog()}
        binding.btnSubmit.setOnClickListener { addStory() }
    }

    private fun showPicDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Pilih foto dari")
        val pictureDialogItems =
            arrayOf("Galeri", "Kamera")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> gallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }


    private fun gallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = ACTION_GET_CONTENT
        val chooser = Intent.createChooser(intent,"Pilih Gambar")
        launchGallery.launch(chooser)
    }



    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == RESULT_OK){
            val selectedImg : Uri = result.data?.data as Uri
            val myFile = Common.uriToFile(selectedImg,this@NewStoryActivity)
            getFile = myFile
            binding.previewImageView.visibility = View.VISIBLE
            binding.btnAddFoto.visibility = View.GONE
            binding.tvHintAddfoto.visibility = View.GONE
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun takePhotoFromCamera() {



        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@NewStoryActivity,
                "com.yusril.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }

    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.previewImageView.setImageBitmap(result)
            binding.previewImageView.visibility = View.VISIBLE
            binding.btnAddFoto.visibility = View.GONE
            binding.tvHintAddfoto.visibility = View.GONE
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    /*private fun addStory(){
        val token = LoginPrefs(this).getToken()
        val desc = binding.edtDesc.text.toString()


        val map: HashMap<String, RequestBody> = hashMapOf()
        map["description"] = Common.createPartFromString(desc)


        if (getFile != null && desc.isNotEmpty()){
            val file = Common.reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            viewModel.fetchResponseMessageAddStory("Bearer $token",imageMultipart,map)
            viewModel.getResponseMessageAddStory().observe(this){
                when(it.status){
                    Resource.Status.SUCCESS ->{
                        binding.pbAddStory.visibility = View.GONE
                        Toast.makeText(this, it.data!!.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,StoryActivity::class.java))
                        finish()
                    }

                    Resource.Status.LOADING ->{
                        binding.pbAddStory.visibility = View.VISIBLE
                    }

                    Resource.Status.ERROR->{
                        binding.pbAddStory.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }else{
            Toast.makeText(this, "Lengkapi Field", Toast.LENGTH_SHORT).show()
        }

    }*/

    private fun addStory(){
        val token = LoginPrefs(this).getToken()
        val desc = binding.edtDesc.text.toString()


        val map: HashMap<String, RequestBody> = hashMapOf()
        map["description"] = Common.createPartFromString(desc)


        if (getFile != null && desc.isNotEmpty()){
            val file = Common.reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

//            viewModel.fetchResponseMessageAddStory("Bearer $token",imageMultipart,map)
            viewModel.addStory("Bearer $token",imageMultipart,map).observe(this){
                if(it != null){
                    when(it){
                        is Result.Success ->{
                            binding.pbAddStory.visibility = View.GONE
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,StoryActivity::class.java))
                            finish()
                        }

                        is Result.Loading ->{
                            binding.pbAddStory.visibility = View.VISIBLE
                        }

                        is Result.Error->{
                            binding.pbAddStory.visibility = View.GONE
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

        }else{
            Toast.makeText(this, "Lengkapi Field", Toast.LENGTH_SHORT).show()
        }

    }

}