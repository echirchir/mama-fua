package app.mamafua.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.components.ItemView
import app.mamafua.ui.theme.grey50
import app.mamafua.ui.theme.grey70
import app.mamafua.ui.theme.white

sealed interface IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource
    data class Drawable(val drawableRes: Int) : IconSource
}

@Composable
fun HeaderWithDescription(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    firstOptionMenu: IconSource? = null,
    secondOptionMenu: IconSource? = null,
    color: Color = white,
    textColor: Color = MaterialTheme.colorScheme.primary,
    inlined: Boolean = false,
    onFirstOptionMenu: (() -> Unit)? = null,
    onSecondOptionMenu: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .background(color.copy(alpha = 0.96f))
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(if (inlined) 0.dp else 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (onBack != null) {
                    ItemView(icon = Icons.AutoMirrored.Default.ArrowBack, onClick = onBack)
                } else {
                    Spacer(modifier = Modifier.width(36.dp))
                }

                if (inlined && title != null) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    onSecondOptionMenu?.let { handler ->
                        secondOptionMenu?.let { second ->
                            ItemView(iconSource = second, onClick = handler)
                        }
                    }
                    onFirstOptionMenu?.let { handler ->
                        firstOptionMenu?.let { first ->
                            ItemView(iconSource = first, onClick = handler)
                        }
                    }
                }
            }

            if (!inlined && title != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )

                    subtitle?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = grey70
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.96f),
                            color.copy(alpha = 0.96f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
private fun ItemView(
    modifier: Modifier = Modifier,
    iconSource: IconSource,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        when (iconSource) {
            is IconSource.Vector -> {
                Icon(
                    imageVector = iconSource.imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            is IconSource.Drawable -> {
                Image(
                    painter = painterResource(id = iconSource.drawableRes),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}
