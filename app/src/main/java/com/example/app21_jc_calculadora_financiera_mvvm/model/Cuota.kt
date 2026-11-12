package com.example.app21_jc_calculadora_financiera_mvvm.model

data class Cuota (
    val numero: Int,
    val cuota: Double,
    val interes: Double,
    val amortizacion: Double,
    val saldo: Double
)
