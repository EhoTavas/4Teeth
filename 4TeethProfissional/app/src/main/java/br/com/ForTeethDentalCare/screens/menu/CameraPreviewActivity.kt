package br.com.ForTeethDentalCare.screens.menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.databinding.ActivityCameraPreviewBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraPreviewBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    //selecionar qual camera usar
    private lateinit var cameraSelector: CameraSelector
    private lateinit var storage: FirebaseStorage

    private var imageCapture: ImageCapture? = null

    //executor de thread separado
    private lateinit var imgCaptureExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        //chamar o método startCamera()
        startCamera()

        binding.btnTakePhoto.setOnClickListener {
            takePhoto()
        }
        binding.btnAcceptPhoto.setOnClickListener {
            savePhoto()
        }
        binding.btnCancelPhoto.setOnClickListener {
            binding.ivCapturedPhoto.visibility = View.INVISIBLE
            binding.btnAcceptPhoto.visibility = View.INVISIBLE
            binding.btnCancelPhoto.visibility = View.INVISIBLE
            binding.cameraPreview.visibility = View.VISIBLE
            binding.btnTakePhoto.visibility = View.VISIBLE
        }
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({

            imageCapture = ImageCapture.Builder().build()

            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraPreview", "Falha ao abrir a câmera")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            imgCaptureExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val buffer = image.planes[0].buffer
                    val imageBytes = ByteArray(buffer.remaining())
                    buffer.get(imageBytes)

                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    val rotationDegrees = image.imageInfo.rotationDegrees

                    val matrix = Matrix()
                    matrix.postRotate(rotationDegrees.toFloat())
                    val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                    runOnUiThread {
                        binding.ivCapturedPhoto.setImageBitmap(rotatedBitmap)
                        binding.ivCapturedPhoto.visibility = View.VISIBLE
                        binding.btnAcceptPhoto.visibility = View.VISIBLE
                        binding.btnCancelPhoto.visibility = View.VISIBLE
                        binding.cameraPreview.visibility = View.INVISIBLE
                        binding.btnTakePhoto.visibility = View.INVISIBLE
                    }

                    image.close()
                }
            }
        )
    }

    private fun savePhoto() {
        val storage = Firebase.storage.reference

        imageCapture?.let {

            val file = File(
                externalMediaDirs[0],
                "dentist_profile_picture_${System.currentTimeMillis()}.jpg"
            )
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

            it.takePicture(
                outputFileOptions,
                imgCaptureExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.i("CameraPreview", "A imagem foi salva: ${file.toUri()}")
                        val firebaseFile = file.toUri()
                        val dentistGallery = storage.child("DentistUserPictures/${firebaseFile.lastPathSegment}")
                        val uploadTask = dentistGallery.putFile(firebaseFile)

                        uploadTask.addOnFailureListener {
                            Log.e("DatabaseSaving", "Não foi possível salvar a imagem")
                        }.addOnSuccessListener {
                            Log.d("DatabaseSaving", "A imagem foi salva com sucesso!")

                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            binding.root.context,
                            "Erro ao salvar a foto",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("CameraPreview", "Exceção ao gravar arquivo da foto: $exception")
                    }
                }
            )
        }
    }

}