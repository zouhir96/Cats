package features.cats.domain.modesl

data class Cat(
    val id: String,
    val url: String,
    val bread: Bread?
) {
    data class Bread(
        val name: String,
        val lifeSpan: String,
        val location: String
    )
}

