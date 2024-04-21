package features.cats.presentation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.EmptyCallback
import core.ItemCallback
import features.cats.domain.modesl.Cat
import features.cats.domain.modesl.CatCategory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import theme.Brown
import theme.BrownWhite80
import theme.Grey50

@Composable
fun CatListRoute(viewModel: CatListViewModel) {
    val state = viewModel.uiState.collectAsState().value
    CatListScreen(state) {
        viewModel.onCategoryClicked(it)
    }
}

@Composable
fun CatListScreen(
    state: CatListUiState,
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
            items(
                state.categories,
                key = { it.id }
            ) {
                CategoryItem(
                    category = it,
                    selected = state.selectedCategory == it,
                    onClick = { onCategoryClicked(it) }
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            items(state.cats) {
                CatItem(it)
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CatCategory,
    selected: Boolean,
    onClick: EmptyCallback
) {
    Text(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (selected) Brown else Color.White,
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
        color = if (selected) Color.White else Brown
    )
}

@Composable
fun CatItem(cat: Cat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                KamelImage(
                    modifier = Modifier.fillMaxWidth().aspectRatio(0.8f),
                    resource = asyncPainterResource(cat.url),
                    contentDescription = "${cat.bread?.name}",
                    contentScale = ContentScale.Crop
                )
                Text(
                    cat.bread?.location.orEmpty(),
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
                    text = cat.bread?.name.orEmpty(),
                    style = MaterialTheme.typography.subtitle1,
                    color = Brown
                )
                Text(
                    text = "Life span: ${cat.bread?.lifeSpan.orEmpty()}",
                    style = MaterialTheme.typography.caption,
                    color = Grey50
                )
            }
        }
    }
}