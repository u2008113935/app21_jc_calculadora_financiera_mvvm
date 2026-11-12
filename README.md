# Plan de Pagos: Calculadora Financiera (MVVM)

Aplicación Android que simula el **plan de pagos de un préstamo** con el método francés (cuota fija). El usuario ingresa monto, tasa efectiva anual (TEA) y plazo, y la app calcula la cuota mensual y el cronograma completo.

Proyecto de ejemplo de clase para practicar **arquitectura MVVM con Jetpack Compose** y el proceso de **publicación en Google Play** (firma digital, App Bundle y Play Console).

> Resultado referencial. La aplicación es un simulador y no constituye una oferta de crédito.

## Funcionalidades

- Ingreso de monto (S/), TEA (%) y plazo (1 a 120 meses)
- Conversión de TEA a TEM (tasa efectiva mensual)
- Cálculo de la cuota fija mensual
- Cronograma de pagos: cuota, interés, amortización y saldo por mes
- Validación de datos en el ViewModel
- Splash screen animado e ícono personalizado
- Tests unitarios del modelo financiero

## Fórmulas

| Concepto | Fórmula |
|---|---|
| Tasa mensual | `TEM = (1 + TEA)^(1/12) - 1` |
| Cuota fija | `Cuota = P * i / (1 - (1 + i)^-n)` |
| Interés del mes | `Interés = Saldo * TEM` |
| Amortización | `Amortización = Cuota - Interés` |

Ejemplo: S/ 10,000 con TEA de 35 % a 12 meses da una cuota de **S/ 976.79**.

## Arquitectura MVVM

```
View (Compose)  --eventos-->  ViewModel  --cálculo-->  Model
      ^                           |
      +------- UiState -----------+
```

| Capa | Responsabilidad |
|---|---|
| **Model** | Datos y fórmulas financieras puras (sin Android) |
| **ViewModel** | Estado de la pantalla, validación y comunicación con el Model |
| **View** | Dibuja el estado y envía eventos al ViewModel |

## Estructura del proyecto

```
app/src/main/java/com/example/app21_jc_calculadora_financiera_mvvm/
├── MainActivity.kt                    Splash del sistema, splash animado y pantalla principal
├── model/
│   ├── Cuota.kt                       Una fila del cronograma
│   └── CalculadoraFinanciera.kt       TEA a TEM, cuota fija y cronograma
├── viewmodel/
│   └── PlanPagosViewModel.kt          UiState, eventos y validación
└── view/
    ├── screens/
    │   ├── SplashPlanPagosScreen.kt   Splash animado
    │   └── PlanPagosScreen.kt         Formulario y tabla del cronograma
    └── theme/                         Color, Theme y Type

app/src/test/.../CalculadoraFinancieraTest.kt   Tests unitarios
```

## Tecnologías

- Kotlin
- Jetpack Compose y Material 3
- ViewModel + StateFlow
- Lifecycle Compose (`collectAsStateWithLifecycle`)
- Core SplashScreen
- JUnit 4
- minSdk 24, targetSdk 36

## Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/u2008113935/app21_jc_calculadora_financiera_mvvm.git
   ```
2. Abrir el proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Ejecutar en un emulador o en un celular con depuración USB activada.

## Tests

En Android Studio, clic en el triángulo verde junto a `CalculadoraFinancieraTest`, o desde la terminal:

```bash
./gradlew test
```

## Publicación en Google Play

| Paso | Detalle |
|---|---|
| 1 | `applicationId` propio: `com.strategymge.planpagos` |
| 2 | `targetSdk 36` y `versionCode` incrementado en cada versión |
| 3 | Firma con llave de subida (`.jks`) |
| 4 | Generación del App Bundle (`.aab`) |
| 5 | Ficha, declaraciones de contenido y pruebas en Play Console |

La llave de firma (`.jks`) **no se incluye** en este repositorio.

## Autor

Desarrollado como material del curso de Desarrollo de Aplicaciones Móviles.
