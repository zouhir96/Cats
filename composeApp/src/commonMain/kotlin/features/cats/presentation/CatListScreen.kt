package features.cats.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cats.composeapp.generated.resources.Res
import cats.composeapp.generated.resources.img_cat
import core.EmptyCallback
import core.ItemCallback
import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.Brown
import theme.BrownWhite80
import theme.CatsTheme
import theme.Grey50

@Composable
fun CatListRoute() {
    val categories = remember {
        mutableStateOf(List(8) { CatCategory("$it", "category$it") })
    }
    CatListScreen(
        categorie = categories.value,
        cats = List(30) {
            Cat(
                id = "$it",
                url = "",
                bread = Cat.Bread(
                    name = "Abyssinian",
                    location = "Egypt",
                    lifeSpan = "104 - 15"
                )
            )
        }
    ) {
        val newCategies = categories.value.map { c -> c.copy(selected = it == c) }
        categories.value = newCategies
    }
}

@Composable
fun CatListScreen(
    categorie: List<CatCategory>,
    cats: List<Cat>,
    onCategoryClicked: ItemCallback<CatCategory>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(categorie) {
                CategoryItem(category = it, onClick = { onCategoryClicked(it) })
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            items(cats) {
                CatItem(it)
            }
        }
    }
}

@Preview
@Composable
fun CatListScreenPreview() {
    CatsTheme {
        CatListScreen(
            categorie = List(8) { CatCategory("$it", "category$it") },
            cats = List(30) {
                Cat(
                    id = "$it",
                    url = "",
                    bread = Cat.Bread(
                        name = "Abyssinian",
                        location = "Egypt",
                        lifeSpan = "14 - 15"
                    )
                )
            }
        ) {

        }
    }
}

@Composable
fun CategoryItem(
    category: CatCategory,
    onClick: EmptyCallback
) {
    Text(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (category.selected) Brown else Color.White,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = Brown,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        text = category.name,
        style = MaterialTheme.typography.body1,
        color = if (category.selected) Color.White else Brown
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CatItem(cat: Cat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large 
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth(),) {
                Image(
                    modifier = Modifier.fillMaxWidth().aspectRatio(0.8f),
                    painter = painterResource(Res.drawable.img_cat),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                    Text(
                        cat.bread.location,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp, end = 10.dp)
                            .background(Brown, MaterialTheme.shapes.small)
                            .padding(4.dp),
                        color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.caption
                    )
            }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BrownWhite80)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = cat.bread.name,
                        style = MaterialTheme.typography.subtitle1,
                        color = Brown
                    )
                    Text(
                        text = "Life span: ${cat.bread.lifeSpan}",
                        style = MaterialTheme.typography.caption,
                        color = Grey50
                    )
                }
        }
    }
}