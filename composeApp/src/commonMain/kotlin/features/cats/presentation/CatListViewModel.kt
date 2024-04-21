package features.cats.presentation


import core.domain.Resource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import features.cats.data.repositories.CatRepositoryImpl
import features.cats.domain.modesl.CatCategory
import features.cats.domain.repositories.CatRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatListViewModel(
    private val catRepository: CatRepository = CatRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(CatListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData("")
    }

    private fun loadData(
        categoryId: String?
    ) {
        viewModelScope.launch {
            if (_uiState.value.categories.isEmpty()) {
                val categoriesDiff = async { catRepository.getCategories() }
                val catsDiff = async { catRepository.getCats(null) }

                val categories = categoriesDiff.await()
                val cats = catsDiff.await()

                when {
                    categories is Resource.Success && cats is Resource.Success -> _uiState.update {
                        it.copy(categories = categories.data, cats = cats.data)
                    }

                    else -> {}
                }
            } else {
                val cats = catRepository.getCats(categoryId)

                when {
                    cats is Resource.Success -> _uiState.update {
                        it.copy(cats = cats.data)
                    }

                    else -> {}
                }
            }
        }
    }

    fun onCategoryClicked(category: CatCategory) {
        if(category == _uiState.value.selectedCategory) return
        _uiState.update { it.copy(selectedCategory = category, cats = emptyList()) }
        loadData(categoryId = category.id)
    }
}
