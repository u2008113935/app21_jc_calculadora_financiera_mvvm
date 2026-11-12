package com.example.app21_jc_calculadora_financiera_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app21_jc_calculadora_financiera_mvvm.model.CalculadoraFinanciera
import com.example.app21_jc_calculadora_financiera_mvvm.model.Cuota
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PlanPagosViewModel : ViewModel() {

    data class PlanPagosUiState(
        val monto: String = "10000",
        val tea: String = "35",
        val plazo: String = "12",
        val cuotaMensual: Double? = null,      // null = aun no se calcula
        val tem: Double = 0.0,
        val totalIntereses: Double = 0.0,
        val cronograma: List<Cuota> = emptyList(),
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(PlanPagosUiState())

    val uiState: StateFlow<PlanPagosUiState> = _uiState.asStateFlow()

    // Eventos que envia la View al escribir en los campos
    fun onMontoChange(valor: String) =
        _uiState.update { it.copy(monto = valor, error = null) }

    fun onTeaChange(valor: String) =
        _uiState.update { it.copy(tea = valor, error = null) }

    fun onPlazoChange(valor: String) =
        _uiState.update { it.copy(plazo = valor, error = null) }

    fun calcular() {
        val estado = _uiState.value
        val monto = estado.monto.toDoubleOrNull()
        val tea = estado.tea.toDoubleOrNull()
        val plazo = estado.plazo.toIntOrNull()

        // Validacion: responsabilidad del ViewModel, no de la View
        if (monto == null || monto <= 0)
            return mostrarError("Ingresa un monto mayor a 0")
        if (tea == null || tea < 0)
            return mostrarError("Ingresa una TEA de 0 % o más")
        if (plazo == null || plazo !in 1..120)
            return mostrarError("El plazo va de 1 a 120 meses")

        val cronograma = CalculadoraFinanciera.planDePagos(monto, tea, plazo)

        _uiState.update {
            it.copy(
                cuotaMensual = cronograma.first().cuota,
                tem = CalculadoraFinanciera.tasaMensual(tea),
                totalIntereses = cronograma.sumOf { c -> c.interes },
                cronograma = cronograma,
                error = null
            )
        }
    }

    private fun mostrarError(mensaje: String) {
        _uiState.update {
            it.copy(error = mensaje, cuotaMensual = null, cronograma = emptyList())
        }
    }

}