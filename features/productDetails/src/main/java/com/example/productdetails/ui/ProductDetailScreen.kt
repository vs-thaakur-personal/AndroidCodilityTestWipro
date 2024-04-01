package com.example.productdetails.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.resources.R
import com.example.resources.composables.ToolBar
import com.example.resources.composables.TopLoader

@Composable
fun ProductDetailRoute(
    productId: Int,
    onBackPressed: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val productDetailState by viewModel.productDetailStateFlow.collectAsState()

    LaunchedEffect(key1 = "dashboardScreen") {
        viewModel.getProductDetails(productId)
    }
    ProductDetailScreen(
        productId = productId,
        productDetailUiState = productDetailState,
        onBackPressed = onBackPressed,
    )

}

@Composable
fun ProductDetailScreen(
    productId: Int,
    productDetailUiState: ProductDetailUiState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = productDetailUiState.product?.title ?: "",
                showNavigationIcon = true,
                onNavigationIconClick = onBackPressed
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
//        CircularProgressIndicator(modifier = Modifier.padding(it))
        if (productDetailUiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (productDetailUiState.product != null) {
            MainUiScreen(
                uiData = productDetailUiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(it),
            )
        } else {
            Text(
                text = productDetailUiState.errorMessage
                    ?: stringResource(id = R.string.something_went_wrong)
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainUiScreen(
    uiData: ProductDetailUiState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            val state = rememberPagerState { uiData.product?.images?.size ?: 0 }
            val pageCount = uiData.product?.images?.size
            HorizontalPager(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = uiData.product?.images?.get(page),
                    contentDescription = "",
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(pageCount ?: 0) { iteration ->
                    val color =
                        if (state.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .size(20.dp)
                            .background(color = color),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = uiData.product?.title ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )

            Text(
                text = uiData.product?.description ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rating: ${uiData.product?.rating ?: 0}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1.0F),
                )

                Spacer(modifier = Modifier.weight(1.0F))
                Text(
                    text = "₹${uiData.product?.price ?: 0}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .weight(1.0F),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}