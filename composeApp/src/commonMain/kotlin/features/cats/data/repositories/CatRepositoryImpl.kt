package features.cats.data.repositories

import core.data.repositories.NetworkRepository
import features.cats.data.dtos.CatDto
import features.cats.data.dtos.CategoryDto
import core.data.httpClient
import features.cats.data.mappers.toCat
import features.cats.data.mappers.toCategory
import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory
import core.domain.NetworkErrors
import core.domain.Resource
import features.cats.domain.repositories.CatRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CatRepositoryImpl(
    private val ktorClient: HttpClient = httpClient
) : NetworkRepository(), CatRepository {
    override suspend fun getCategories(): Resource<List<CatCategory>, NetworkErrors> {
        return getMany(
            call = {
                ktorClient
                    .get("categories")
                    .body<List<CategoryDto>>()
            },
            toDomain = { it.toCategory() }
        )
    }

    override suspend fun getCats(categoryId: String?): Resource<List<Cat>, NetworkErrors> {
        return getMany(
            call = {
                ktorClient
                    .get("images/search") {
                        url {
                            parameters.append("size", "thumb")
                            parameters.append("mime_types", "jpg")
                            parameters.append("format", "json")
                            parameters.append("has_breeds", false.toString())
                            parameters.append("order", "DESC")
                            parameters.append("category_ids", categoryId.orEmpty())
                            parameters.append("page", "1")
                            parameters.append("limit", "25")
                        }
                    }
                    .body<List<CatDto>>()
            },
            toDomain = { it.toCat() }
        )
    }
}