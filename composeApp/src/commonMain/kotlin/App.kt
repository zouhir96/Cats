import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import features.cats.presentation.CatListRoute
import features.cats.presentation.CatListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.CatsTheme

@Composable
@Preview
fun App() {
    CatsTheme {
        val catsViewModel = getViewModel(Unit, viewModelFactory { CatListViewModel() })
        CatListRoute(catsViewModel)
    }
}