package com.purnya5151.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.purnya5151.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIactivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnit){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSMetricUnitsView()
            }

        }

        binding?.btnCalculateUnits?.setOnClickListener{
           calculateUnits()
        }

    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE


        binding?.etMetricUnitHeight?.text?.clear()
        binding?.etMetricUnitWeight?.text?.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleUSMetricUnitsView(){
        currentVisibleView = US_UNITS_VIEW

        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitWeight?.text?.clear()
        binding?.etUsMetricUnitHeightFeet?.text?.clear()
        binding?.etUsMetricUnitHeightInch?.text?.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIres(bmi:Float){

        val bmiLabel : String
        val bmiDescription : String

        if (bmi.compareTo(15f)<= 0){

            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of yourself! Eat more!"

        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits():Boolean{
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false

        }

        return isValid
    }

    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if (validateMetricUnits()){
            val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() /100

            val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

            val bmi = weightValue / (heightValue*heightValue)

            displayBMIres(bmi)

        }else{
            Toast.makeText(this,"Enter Valid values",Toast.LENGTH_SHORT).show()
        }
        }else{
            if (validateUSMetricUnits()){
                val usHeightValueFeet : String = binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usHeightValueInch : String = binding?.etUsMetricUnitHeightInch?.text.toString()
                val usWeightValue : Float = binding?.etUsMetricUnitWeight?.text.toString().toFloat()

                val heightValue = usHeightValueInch.toFloat() + usHeightValueFeet.toFloat()*12

                val bmi = 703*(usWeightValue/(heightValue*heightValue))
                displayBMIres(bmi)

            }else{
                Toast.makeText(this,"Enter Valid values",Toast.LENGTH_SHORT).show()
            }
        }

        }

        private fun validateUSMetricUnits():Boolean {
            var isValid = true
            when {
                binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                    isValid = false
                }
                binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                    isValid = false

                }
                binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                    isValid = false

                }


            }
            return isValid
        }

}