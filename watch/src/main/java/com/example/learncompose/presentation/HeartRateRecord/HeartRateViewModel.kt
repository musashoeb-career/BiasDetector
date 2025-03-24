package com.example.learncompose.presentation.HeartRateRecord

import android.app.Application
import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.AndroidViewModel
import com.example.learncompose.presentation.Oxymeter.OxygenListenerService
import com.example.learncompose.presentation.SamsungHealthConnect
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException

class VoiceRecorder(private val context: Application) {

    private var mediaRecorder: MediaRecorder? = null
    private var fileName: String = ""
//
//    fun startRecording() {
//        mediaRecorder = new MediaReco
//        mediaRecorder = MediaRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            setOutputFile(fileName)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//            try {
//                prepare()
//            } catch (e: IOException) {
//               Log.d("Record Message", "Recorder Failed")
//            }
//
//            start()
//        }
//    }

    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}


class HeartRateViewModel(application: Application) : AndroidViewModel(application) {

    val voiceRecorder: VoiceRecorder = VoiceRecorder(application)
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("users/names/heartrate")

        fun ReadDatabase() {

            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called when data is retrieved from the database
                    val value = dataSnapshot.getValue(String::class.java)
                    Log.d("Firebase", "Value is: $value")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // This method is called when the read operation is cancelled
                    Log.w("Firebase", "Failed to read value.", databaseError.toException())
                }
            })
        }

        val healthConnect = SamsungHealthConnect(application)
        val heartRateListener = HeartRateListenerService()


        suspend fun startTest() {


            //voiceRecorder.startRecording()
            healthConnect.connectHealthService()

            delay(1000)
            if(healthConnect.isConnected) {

                healthConnect.heartRateTracker.setEventListener(heartRateListener.heartRateListener)

            }


        }

        fun stopTest() {
            //voiceRecorder.stopRecording()
            val heartrateFinal = heartRateListener.heartRateData.value
            myRef.setValue("Heart Rate Final:$heartrateFinal")
            healthConnect.heartRateTracker.unsetEventListener()
            healthConnect.disconnectHealthService()
        }

    }

