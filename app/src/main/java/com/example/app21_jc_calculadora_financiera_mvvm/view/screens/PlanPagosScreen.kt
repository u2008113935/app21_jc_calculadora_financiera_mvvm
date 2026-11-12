package com.example.app21_jc_calculadora_financiera_mvvm.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app21_jc_calculadora_financiera_mvvm.model.Cuota
import com.example.app21_jc_calculadora_financiera_mvvm.viewmodel.PlanPagosViewModel
import java.util.Locale

private fun soles(valor: Double) = "S/ " + String.format(Locale.US, "%,.2f", valor)

@Composable
fun PlanPagosScreen (
    modifier: Modifier = Modifier,
    viewModel: PlanPagosViewModel = viewModel()
)

{
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Plan de pagos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        // Cada campo muestra el estado y envia el cambio al ViewModel
        CampoNumero("Monto (S/)", estado.monto,
            viewModel::onMontoChange, KeyboardType.Decimal)
        CampoNumero("TEA (%)", estado.tea,
            viewModel::onTeaChange, KeyboardType.Decimal)
        CampoNumero("Plazo (meses)", estado.plazo,
            viewModel::onPlazoChange, KeyboardType.Number)

        Button(onClick = viewModel::calcular, modifier = Modifier.fillMaxWidth()) {
            Text("Calcular")
        }

        // Error de validacion (lo decide el ViewModel)
        estado.error?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Resultado: solo aparece cuando ya hay cuota calculada
        estado.cuotaMensual?.let { cuota ->
            Spacer(Modifier.height(16.dp))
            Text("Cuota mensual", style = MaterialTheme.typography.labelLarge)
            Text(
                soles(cuota),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "TEM %.4f %%  |  Intereses %s".format(
                    Locale.US, estado.tem * 100, soles(estado.totalIntereses)
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                "Resultado referencial. No constituye una oferta de crédito.",
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(8.dp))

            Encabezado()
            // La tabla ocupa el espacio restante
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(estado.cronograma, key = { it.numero }) { c ->
                    FilaCuota(c)
                }
            }
        }
    }
}


@Composable
private fun CampoNumero(
    etiqueta: String,
    valor: String,
    onCambio: (String) -> Unit,
    tipo: KeyboardType
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = tipo),
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )
}


@Composable
private fun Encabezado() {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        val titulos = listOf("N°", "Cuota", "Interés", "Amortiz.", "Saldo")
        titulos.forEachIndexed { i, titulo ->
            Text(
                titulo,
                Modifier.weight(if (i == 0) 0.5f else 1f),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
    HorizontalDivider()
}


@Composable
private fun FilaCuota(c: Cuota) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            "${c.numero}", Modifier.weight(0.5f), textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall
        )
        listOf(c.cuota, c.interes, c.amortizacion, c.saldo).forEach { valor ->
            Text(
                String.format(Locale.US, "%,.2f", valor),
                Modifier.weight(1f),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}