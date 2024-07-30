package com.rmtz.calendar.widget

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import com.rmtz.calendar.model.ImageState
import com.rmtz.calendar.stateDevinitions.ImageStateDefinition
import com.rmtz.calendar.worker.KalenderWorker

class KalenderWidget: GlanceAppWidget() {
    override val sizeMode: SizeMode = SizeMode.Exact
    override val stateDefinition = ImageStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    private fun getBoxAlignment(imageState: ImageState): Alignment = when (imageState) {
        ImageState.Loading -> Alignment.Center
        is ImageState.Success -> Alignment.BottomEnd
    }

    @Composable
    fun Content() {
        val size = LocalSize.current
        val context = LocalContext.current
        val glanceId = LocalGlanceId.current
        val imageState = currentState<ImageState>()
        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp),
                contentAlignment = getBoxAlignment(imageState)
            ) {
                when (imageState) {
                    ImageState.Loading -> LoadingState()
                    is ImageState.Success -> SuccessState(path = imageState.url)
                }

                LaunchedEffect(Unit){
                    KalenderWorker.enqueue(context, size, glanceId)
                }
            }
        }
    }

    @Composable
    private fun SuccessState(path: String) {
        Image(
            provider = getImageProvider(path),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable(actionRunCallback<WidgetActions>())
        )
        Text(
            text = "Tap to refresh",
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline
            ),
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(GlanceTheme.colors.surface)
        )
    }

    @Composable
    private fun LoadingState() {
        CircularProgressIndicator()
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
        KalenderWorker.cancel(context, glanceId)
    }

    private fun getImageProvider(path: String): ImageProvider {
        if (path.startsWith("content://")) {
            return ImageProvider(path.toUri())
        }
        val bitmap = BitmapFactory.decodeFile(path)
        return ImageProvider(bitmap)
    }
}

