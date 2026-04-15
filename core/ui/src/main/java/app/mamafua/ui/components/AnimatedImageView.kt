package app.mamafua.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.R
import app.mamafua.ui.theme.black
import app.mamafua.ui.theme.lightGrey
import app.mamafua.ui.theme.primaryGreen
import app.mamafua.ui.theme.white
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest

@Composable
fun AnimatedImageView(
    modifier: Modifier = Modifier,
    height: Dp = 250.dp,
    contentScale: ContentScale = ContentScale.Crop,
    imageUrl: String,
    title: String,
    cornerRadius: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    val boxModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = horizontalPadding)
        .height(height)
        .then(
            if (onClick != null) {
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
            } else {
                Modifier
            }
        )

    Box(
        modifier = boxModifier
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .allowHardware(false)
                .build(),
            contentDescription = title,
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                .clip(RoundedCornerShape(cornerRadius))
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(primaryGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Gray)
                    }
                }

                is AsyncImagePainter.State.Success -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                        exit = fadeOut()
                    ) {
                        SubcomposeAsyncImageContent()
                    }
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        painter = rememberVectorPainter(Icons.Filled.Clear),
                        contentDescription = "$title (fallback)",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }

                else -> Unit
            }
        }
    }
}

enum class OverlayPosition {
    TOP,
    BOTTOM;
}

enum class BadgeSize {
    SMALL,
    MEDIUM;
}

sealed class OverlayType {
    // Emoji with chevron (used in CoffeeDescription)
    data class EmojiWithChevron(val emojiDrawableResId: Int, val onClick: () -> Unit) : OverlayType()

    // Emoji without chevron (used in GenericCoffeeCard)
    data class EmojiOnly(val emojiDrawableResId: Int) : OverlayType()

    // Text with chevron (another badge type)
    data class TextWithChevron(val text: String, val size: BadgeSize = BadgeSize.MEDIUM, val onClick: () -> Unit) : OverlayType()

    // Blurred percentage match badge
    data class BlurredMatch(val percentage: Int = 89, val size: BadgeSize = BadgeSize.MEDIUM, val onClick: () -> Unit = {}) : OverlayType()

    // Loading indicator overlay
    object Loading : OverlayType()

    // No overlay
    object None : OverlayType()
}

@Composable
fun AnimatedImageViewWithOverlay(
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    contentScale: ContentScale = ContentScale.Crop,
    imageUrl: String,
    title: String,
    cornerRadius: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    overlayType: OverlayType = OverlayType.None,
    overlayPosition: OverlayPosition = OverlayPosition.BOTTOM,
    applyBlurToBottom: Float = 0f,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        AnimatedImageView(
            modifier = Modifier,
            height = imageHeight,
            contentScale = contentScale,
            imageUrl = imageUrl,
            title = title,
            cornerRadius = cornerRadius,
            horizontalPadding = 0.dp,
            borderColor = borderColor,
            borderWidth = borderWidth,
            onClick = onClick
        )

        // Blur effect overlay on bottom (only if applyBlurToBottom > 0)
        if (applyBlurToBottom > 0f) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(imageHeight * applyBlurToBottom)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                black.copy(alpha = 0.2f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        ),
                        shape = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius)
                    )
                    .clip(RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius))
                    .blur(radius = 32.dp)
            )
        }

        val verticalAlignment = if (overlayPosition == OverlayPosition.TOP) {
            Alignment.TopCenter
        } else {
            Alignment.BottomCenter
        }

        Box(
            modifier = Modifier
                .align(verticalAlignment)
                .padding(
                    top = if (overlayPosition == OverlayPosition.TOP) 18.dp else 0.dp,
                    bottom = if (overlayPosition == OverlayPosition.BOTTOM) 12.dp else 0.dp
                )
        ) {
            when (overlayType) {
                is OverlayType.EmojiWithChevron -> {
                    OverlayBadge(
                        emojiDrawableResId = overlayType.emojiDrawableResId,
                        showChevron = true,
                        onClick = overlayType.onClick
                    )
                }
                is OverlayType.EmojiOnly -> {
                    OverlayBadge(
                        emojiDrawableResId = overlayType.emojiDrawableResId,
                        showChevron = false,
                        onClick = {}
                    )
                }
                is OverlayType.TextWithChevron -> {
                    TextBadge(
                        text = overlayType.text,
                        size = overlayType.size,
                        onClick = overlayType.onClick
                    )
                }
                is OverlayType.BlurredMatch -> {
                    BlurredPercentageMatch(
                        percentage = overlayType.percentage,
                        size = overlayType.size,
                        onClick = overlayType.onClick
                    )
                }
                OverlayType.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = lightGrey,
                        strokeWidth = 3.dp
                    )
                }
                OverlayType.None -> {
                    // No overlay
                }
            }
        }
    }
}

@Composable
private fun OverlayBadge(
    emojiDrawableResId: Int,
    showChevron: Boolean = true,
    onClick: () -> Unit = {}
) {
    val badgeModifier = if (showChevron) {
        Modifier
            .shadow(
                elevation = 25.dp,
                spotColor = Color(0x40404040),
                ambientColor = Color(0x40404040)
            )
            .width(86.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(45.dp))
            .border(1.dp, white, RoundedCornerShape(45.dp))
            .background(
                color = white.copy(alpha = 0.7f),
                shape = RoundedCornerShape(45.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp)
    } else {
        Modifier
            .shadow(
                elevation = 18.dp,
                spotColor = Color(0x40404040),
                ambientColor = Color(0x40404040)
            )
            .width(45.dp)
            .height(36.dp)
            .border(1.dp, white, RoundedCornerShape(28.dp))
            .background(
                color = white.copy(alpha = 0.7f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 13.dp, vertical = 9.dp)
    }

    Box(
        modifier = badgeModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = emojiDrawableResId),
                contentDescription = "Match indicator",
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit
            )

            if (showChevron) {
                Spacer(modifier = Modifier.width(6.dp))

                Image(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Chevron right",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun TextBadge(
    text: String,
    size: BadgeSize = BadgeSize.MEDIUM,
    onClick: () -> Unit = {}
) {
    val badgeModifier = when (size) {
        BadgeSize.SMALL -> {
            Modifier
                .shadow(
                    elevation = 22.dp,
                    spotColor = Color(0x40404040),
                    ambientColor = Color(0x40404040)
                )
                .height(34.dp)
                .clip(RoundedCornerShape(45.dp))
                .border(1.dp, white, RoundedCornerShape(45.dp))
                .background(
                    color = Color(0xE6FFFFFF),
                    shape = RoundedCornerShape(45.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        }
        BadgeSize.MEDIUM -> {
            Modifier
                .shadow(
                    elevation = 25.dp,
                    spotColor = Color(0x40404040),
                    ambientColor = Color(0x40404040)
                )
                .height(56.dp)
                .clip(RoundedCornerShape(40.dp))
                .border(1.dp, white, RoundedCornerShape(40.dp))
                .background(
                    color = Color(0xE6FFFFFF),
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable { onClick() }
                .padding(horizontal = 18.dp, vertical = 14.dp)
        }
    }

    Box(
        modifier = badgeModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "$text% Match",
                fontSize = if (size == BadgeSize.SMALL) 12.sp else 16.sp,
                color = black,
                fontWeight = FontWeight(600),
                maxLines = 1
            )

            if (size == BadgeSize.MEDIUM) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Chevron right",
                    modifier = Modifier.size(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun BlurredPercentageMatch(
    percentage: Int = 89,
    size: BadgeSize = BadgeSize.MEDIUM,
    onClick: () -> Unit = {}
) {
    val badgeModifier = when (size) {
        BadgeSize.SMALL -> {
            Modifier
                .shadow(
                    elevation = 22.dp,
                    spotColor = Color(0x40404040),
                    ambientColor = Color(0x40404040)
                )
                .height(34.dp)
                .clip(RoundedCornerShape(45.dp))
                .border(1.dp, white, RoundedCornerShape(45.dp))
                .background(
                    color = Color(0xE6FFFFFF),
                    shape = RoundedCornerShape(45.dp)
                )
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
        }
        BadgeSize.MEDIUM -> {
            Modifier
                .shadow(
                    elevation = 25.dp,
                    spotColor = Color(0x40404040),
                    ambientColor = Color(0x40404040)
                )
                .height(56.dp)
                .clip(RoundedCornerShape(40.dp))
                .border(1.dp, white, RoundedCornerShape(40.dp))
                .background(
                    color = Color(0xE6FFFFFF),
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable { onClick() }
                .padding(start = 18.dp, top = 14.dp, end = 18.dp, bottom = 14.dp)
        }
    }

    Box(
        modifier = badgeModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "$percentage",
                fontWeight = FontWeight(600),
                fontSize = if (size == BadgeSize.SMALL) 14.sp else 18.sp,
                color = black,
                modifier = Modifier.blur(8.dp)
            )

            Text(
                text = "% Match",
                fontWeight = FontWeight(600),
                fontSize = if (size == BadgeSize.SMALL) 14.sp else 16.sp,
                color = black
            )

            if (size == BadgeSize.MEDIUM) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Arrow",
                    modifier = Modifier.size(18.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
