package app.mamafua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.mamafua.ui.R
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest

@Composable
fun GifLoadingAnimation(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color(0xFFFDFBFD)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.raw.scan_loading)
                .decoderFactory(GifDecoder.Factory())
                .build(),
            contentDescription = "Loading animation",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
