package app.mamafua.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.extensions.getCountryFlag

@Composable
fun CountryTag(
    name: String,
    countryCode: String
) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFF6F6F6),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(text = context.getCountryFlag(countryCode), fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name, fontSize = 14.sp)
        }
    }
}
