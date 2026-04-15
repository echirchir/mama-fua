package app.mamafua.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import app.mamafua.ui.theme.white

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    title: String? = null,
    optionMenu: IconSource? = null,
    color: Color = white,
    textColor: Color = MaterialTheme.colorScheme.primary,
    inlined: Boolean = false,
    showDivider: Boolean = true,
    onOptionMenu: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
) {
    val iconSlotWidth = 36.dp

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .background(color)
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
                    ItemView(icon = Icons.AutoMirrored.Filled.ArrowBack, onClick = onBack)
                } else {
                    Spacer(modifier = Modifier.width(iconSlotWidth))
                }

                if (inlined && title != null) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
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

                val rightControls = mutableListOf<@Composable () -> Unit>().apply {
                    onClose?.let { add { ItemView(icon = Icons.Default.Close, onClick = it) } }
                    if (onOptionMenu != null && optionMenu != null) {
                        add { ItemViewWithIconSource(iconSource = optionMenu, onClick = onOptionMenu) }
                    }
                }

                if (rightControls.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        rightControls.forEach { it() }
                    }
                } else {
                    Spacer(modifier = Modifier.width(iconSlotWidth))
                }
            }

            if (!inlined && title != null) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
        }

        if (showDivider) {
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
}

@Composable
fun ItemView(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ItemViewWithIconSource(
    iconSource: IconSource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
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
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            is IconSource.Drawable -> {
                Image(
                    painter = painterResource(id = iconSource.drawableRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
