package features.cats.presentation

import androidx.compose.runtime.Stable
import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory


@Stable
data class CatListUiState(
    val categories: List<CatCategory> = emptyList(),
    val selectedCategory: CatCategory? = null,
    val cats: List<Cat> = emptyList()
)

