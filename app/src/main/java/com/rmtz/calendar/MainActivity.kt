package com.rmtz.calendar

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmtz.calendar.ui.component.roundedTopCornerShape
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.FlatUiColors
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var widgetProvider: AppWidgetProviderInfo? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the AppWidgetManager instance
        val appWidgetManager = AppWidgetManager.getInstance(this)
        // Safely get the list of widget providers
        val widgetProviders = appWidgetManager.getInstalledProvidersForPackage(packageName, null)
        // Check if the list is not empty before accessing the first element
        widgetProvider = widgetProviders.firstOrNull()

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FlatUiColors.CanadianPallet.WildCaribbeanGreen
                ) {
                    ContentUI()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateUI() {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetState = rememberModalBottomSheetState(true)

    val title = "Bottom Sheet Title"
    val content: @Composable () -> Unit = {
        Text("Bottom sheet content goes here")
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = roundedTopCornerShape,
        //sheetPeekHeight = 95.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                content()
            }
        },
        content = {
            ContentUI()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentUI() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetState = rememberModalBottomSheetState(true)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(0.dp),
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Snackbar # ${++clickCount}"
                        )
                    }
                }
            ) { Text("Show snackbar") }
        },
        content = { innerPadding ->
            KalenderUI(innerPadding)
        }
    )
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)

@Composable
fun TemplatePreview() {
    AppTheme {
        TemplateUI()
    }
}

