package app.mamafua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun ImageDialogView(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(364.dp)
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .height(364.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // Prevent dismissal when clicking on the dialog itself
                }
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .allowHardware(false)
                    .build(),
                contentDescription = "Enlarged image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onDismiss()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
