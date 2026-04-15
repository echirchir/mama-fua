package app.mamafua.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mamafua.ui.R
import app.mamafua.ui.theme.primaryGreen

@Composable
fun InAppReviewFallBackModal(
    onSubmitClick: (String) -> Unit
) {
    var feedbackText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.dislike_app_review_illus),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp)
                    .width(32.dp)
                    .height(4.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.thats_a_bummer),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Start
                ),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.were_working_hard_to_improve),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Start,
                    color = Color(0xFF626C76)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.tell_us_more_about_your_experience),
                        color = Color(0xFFB0B8C1)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(126.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E6EB),
                    unfocusedBorderColor = Color(0xFFE0E6EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            MyButton(
                text = R.string.submit,
                color = primaryGreen,
                enabled = feedbackText.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                radius = 28.dp,
                onClick = { onSubmitClick(feedbackText) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
