package br.edu.ifsp.dmo.conversordetemperatura.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo.conversordetemperatura.R
import br.edu.ifsp.dmo.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.conversordetemperatura.model.CelsiusStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.FahrenheitStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.KelvinStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.TemperatureConverter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var converterStrategy: TemperatureConverter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCelsius.setOnClickListener {
            handleConversion(CelsiusStrategy)
        }
        binding.btnFahrenheit.setOnClickListener {
            handleConversion(FahrenheitStrategy)
        }
        binding.btnKelvin.setOnClickListener {
            handleConversion(KelvinStrategy)
        }
    }


    private fun readTemperature(): Double {
        return try {
            binding.edittextTemperature.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            throw NumberFormatException("Input Error")
        }
    }

    private fun handleConversion(strategy: TemperatureConverter) {
        converterStrategy = strategy
        try {
            val inputValue = readTemperature()
            binding.textviewResultNumber.text = String.format(
                "%.2f %s",
                converterStrategy.converter(inputValue),
                converterStrategy.getScale()
            )
            binding.textviewResultMessage.text = when (converterStrategy) {
                is CelsiusStrategy -> getString(R.string.msgFtoC)
                is FahrenheitStrategy -> getString(R.string.msgCtoF)
                is KelvinStrategy -> when (strategy) {
                    else -> getString(R.string.msgKtoC)
                }
                else -> ""
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_popup_notify),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("APP_DMO_TEMPERATURA", e.stackTraceToString())
        }
    }

}