package com.purnya5151.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.purnya5151.a7minworkout.databinding.ActivityExerciseBinding
import com.purnya5151.a7minworkout.databinding.DialogCoustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private val restTimeDuration: Long = 1

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private val exerciseTimeDuration: Long = 1

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var exerciseAdaptar: ExerciseStatusAdaptor? = null

    var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }


        binding?.toolbarExercise?.setNavigationOnClickListener {
            coustomDialogForBackbutton()
        }

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this,this )


        setUpRestView()
        setUpExerciseStatusRecyclerView()

    }

    private fun coustomDialogForBackbutton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCoustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdaptar = ExerciseStatusAdaptor(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdaptar
    }

    private fun setUpRestView(){

        try {
            val soundURI = Uri.parse("android.resource://com.purnya5151.a7minworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }



        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (restTimer!= null){
            restTimer!!.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()

        setRestProgressBar()
    }

    override fun onBackPressed() {
        coustomDialogForBackbutton()
//        super.onBackPressed()
    }
    private fun setUpExerciseView(){

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())


        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
            binding?.ProgBar?.progress = restProgress


        restTimer = object : CountDownTimer(restTimeDuration*1000,1000){
                override fun onTick(p0: Long) {
                    restProgress++
                    binding?.ProgBar?.progress = 10 - restProgress
                    binding?.tvTimer?.text = (10-restProgress).toString()

                }

                override fun onFinish() {
                    currentExercisePosition++
                    exerciseList!![currentExercisePosition].setIsSelected(true)
                    exerciseAdaptar!!.notifyDataSetChanged()
                    setUpExerciseView()
                }

            }.start()
    }

    private fun setExerciseProgressBar(){
            binding?.ProgBarExercise?.progress = exerciseProgress

            exerciseTimer = object : CountDownTimer(exerciseTimeDuration*1000,1000){
                override fun onTick(p0: Long) {
                    exerciseProgress++
                    binding?.ProgBarExercise?.progress = 30 - exerciseProgress
                    binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()

                }

                override fun onFinish() {

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdaptar!!.notifyDataSetChanged()

                   if(currentExercisePosition< exerciseList?.size!! - 1){
                       setUpRestView()
                   }else{
                       finish()
                       val intent = Intent(this@ExerciseActivity,Finsh::class.java)
                       startActivity(intent)

                   }
                }

            }.start()

    }



    override fun onDestroy() {
        super.onDestroy()

        if (restTimer!= null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts!= null){
            tts?.stop()
            tts?.shutdown()
        }

        if (player!= null){
            player?.stop()
        }

        binding = null

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "This Language specified is not supported!!")
            }else{
                Log.e("TTS", "Initialisation Failed!")
            }
        }
    }

    private fun speakOut(text: String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}