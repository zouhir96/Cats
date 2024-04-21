package features.cats.data.mappers

import features.cats.data.dtos.CatDto
import features.cats.data.dtos.CategoryDto
import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory

fun CatDto.toCat() = Cat(
    id = id,
    url = url,
    bread = breeds.firstOrNull()?.let { bread ->
        Cat.Bread(
            name = bread.name,
            lifeSpan = bread.lifeSpan,
            location = bread.origin
        )
    }
)

fun CategoryDto.toCategory() = CatCategory(
    id = id.toString(),
    name = name,
)

