package com.example.app21_jc_calculadora_financiera_mvvm.model

import kotlin.math.pow

object CalculadoraFinanciera {

    fun tasaMensual(teaPorcentaje: Double): Double =
        (1 + teaPorcentaje / 100).pow(1.0 / 12) - 1

    fun cuotaFija(monto: Double, tem: Double, meses: Int): Double =
        if (tem == 0.0)  monto / meses
        else monto * tem / (1 - (1 + tem).pow(-meses))

    fun planDePagos(monto: Double, teaPorcentaje: Double, meses: Int): List<Cuota>{
            val tem = tasaMensual(teaPorcentaje)
            val cuota = cuotaFija(monto, tem, meses)
            var saldo = monto

        return(1..meses).map { n ->
            val interes = saldo * tem
            val amortizacion = cuota - interes
            saldo -= amortizacion
            Cuota(n, cuota, interes, amortizacion, saldo.coerceAtLeast(0.0))

        }
    }




}