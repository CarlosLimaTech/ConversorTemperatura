package br.edu.ifsp.dmo.conversordetemperatura.model

object KelvinStrategy: TemperatureConverter {
    override fun converter(temperature: Double): Double {
        return (temperature - 32) * 5/9 + 273.15
    }

    override fun getScale(): String = "K"
}
