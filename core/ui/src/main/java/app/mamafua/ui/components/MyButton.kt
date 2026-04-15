package app.mamafua.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.theme.primary100
import app.mamafua.ui.theme.primaryGreen
import app.mamafua.ui.theme.white

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Medium,
    loading: Boolean = false,
    color: Color = primaryGreen,
    textColor: Color = white,
    radius: Dp = 28.dp,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(56.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
            disabledContainerColor = color.copy(alpha = 0.2f),
            disabledContentColor = textColor.copy(alpha = 0.38f)
        )
    ) {
        if (loading) CircularProgress(modifier = Modifier.size(24.dp), color = white)
        else {
            Text(
                text = stringResource(id = text),
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = textColor,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun MySpecialButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Medium,
    loading: Boolean = false,
    color: Color = primaryGreen,
    radius: Dp = 28.dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(white)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                color = Color(0xFFDCE2E8),
                thickness = 1.dp,
            )

            Spacer(modifier = Modifier.height(13.dp))

            Button(
                onClick = onClick,
                enabled = enabled,
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = white,
                    disabledContainerColor = color.copy(alpha = 0.2f),
                    disabledContentColor = white.copy(alpha = 0.38f)
                )
            ) {
                if (loading) {
                    CircularProgress(
                        modifier = Modifier.size(24.dp),
                        color = white
                    )
                } else {
                    Text(
                        text = stringResource(id = text),
                        fontWeight = fontWeight,
                        fontSize = 18.sp,
                        color = white,
                        maxLines = 1,
                    )
                }
            }

            Spacer(modifier = Modifier.height(13.dp))
        }
    }
}

@Composable
fun MyButtonWithArrow(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Medium,
    loading: Boolean = false,
    color: Color = primaryGreen,
    textColor: Color = white,
    arrowColor: Color = primaryGreen,
    radius: Dp = 28.dp,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(56.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
            disabledContainerColor = color.copy(alpha = 0.2f),
            disabledContentColor = textColor.copy(alpha = 0.38f)
        )
    ) {
        if (loading) {
            CircularProgress(modifier = Modifier.size(24.dp), color = primaryGreen)
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = text),
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    color = textColor,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = arrowColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
