import androidx.compose.runtime.Composable
import features.cats.presentation.CatListRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.CatsTheme

@Composable
@Preview
fun App() {
    CatsTheme {
        CatListRoute()
    }
}