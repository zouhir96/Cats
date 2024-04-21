package features.cats.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDto(
    val id: String,
    val url: String,
    val breeds: List<Bread>
){
    @Serializable
    data class Bread(
        val name: String,
        @SerialName("life_span") val lifeSpan: String,
        val origin: String
    )
}
