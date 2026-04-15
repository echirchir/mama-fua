package app.mamafua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.R
import app.mamafua.ui.theme.primaryGreen
import app.mamafua.ui.theme.white

@Composable
fun ExploreSubscriptionsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                spotColor = Color(0xFF9ABAB0),
                ambientColor = Color(0xFF9ABAB0)
            )
            .border(
                width = 1.dp,
                color = primaryGreen,
                shape = RoundedCornerShape(size = 72.dp)
            )
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(size = 72.dp))
            .background(color = primaryGreen)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = white),
                onClick = onClick
            )
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.explore_icon),
                contentDescription = null,
                tint = white,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.explore_subscriptions_label),
                color = white,
                fontSize = 14.sp,
                fontWeight = FontWeight(600)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = white,
            modifier = Modifier.size(20.dp)
        )
    }
}
