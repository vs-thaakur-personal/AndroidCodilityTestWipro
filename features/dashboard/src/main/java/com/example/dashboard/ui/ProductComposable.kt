package com.example.dashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.data.models.Product

@Composable
fun ProductSmall(
    product: Product,
    onProductClicked: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onProductClicked(product.id) },
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable { onProductClicked.invoke(product.id) }
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            AsyncImage(
                model = product.images[0],
                contentDescription = stringResource(id = com.example.resources.R.string.product_image_content),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = product.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            Text(
                text = product.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            Text(
                text = "Rating ${product.rating}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            Text(
                text = "₹${product.price}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClicked: (Int) -> Unit,
    isGrid: Boolean = false
) {
    if (isGrid) {
        ListInGrid(products = products, onProductClicked = onProductClicked)
    } else {
        ListInColumn(products = products, onProductClicked = onProductClicked)
    }
}

@Composable
fun ProductPagedList(
    products: LazyPagingItems<Product>,
    onProductClicked: (Int) -> Unit,
    onPageLoading: () -> Unit
) {
    LazyColumn {
        items(products.itemCount) { item ->
            item.let {
                ProductSmall(
                    product = products[it]!!,
                    onProductClicked = onProductClicked,
                )
            }
        }
        products.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                }

                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                }

                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                }
            }
        }
    }
}

@Composable
private fun ListInGrid(
    products: List<Product>,
    onProductClicked: (Int) -> Unit,
) {
    val listState = rememberLazyGridState()
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(products.size, key = { products[it].id }) {
            ProductSmall(
                product = products[it],
                onProductClicked = onProductClicked,
            )
        }
    }

}

@Composable
private fun ListInColumn(
    products: List<Product>,
    onProductClicked: (Int) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(products.size, key = { products[it].id }) {
            ProductSmall(
                product = products[it],
                onProductClicked = onProductClicked,
            )
        }
    }

}