package app.mamafua.feature.auth.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.mamafua.ui.components.HeaderView
import app.mamafua.ui.components.MyButton
import app.mamafua.ui.components.ScreenLayout

@Composable
fun SignUpScreen() {
    ScreenLayout(
        header = {
            HeaderView(
                title = "Welcome to MamaFua"
            )
        },
        footer = {
            MyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = app.mamafua.ui.R.string.get_started,
                onClick = {
                    // TODO: Handle Get Started click
                }
            )
        }
    ) { paddingValues ->

    }
}