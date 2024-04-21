package features.cats.domain.repositories

import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory
import core.domain.NetworkErrors
import core.domain.Resource

interface CatRepository {
    suspend fun getCategories(): Resource<List<CatCategory>, NetworkErrors>
    
    suspend fun getCats(categoryId: String?): Resource<List<Cat>, NetworkErrors>
}