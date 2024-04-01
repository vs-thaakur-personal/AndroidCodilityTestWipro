package com.example.dashboard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.resources.composables.ErrorText
import com.example.resources.composables.ToolBar
import com.example.resources.composables.TopLoader
import com.example.theme.AssessmentTheme

@Composable
fun DashboardRoute(
    navigateToProductDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var dataLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = "dashboardScreen") {
        if(dataLoaded.not()) {
            viewModel.getAllCategories()
            viewModel.getAllProducts()
            dataLoaded = true
        }
    }
    val dashboardState by viewModel.loginStateFlow.collectAsState()

    BackHandler(enabled = true) {
        // Define what should happen on back press her
        navigateBack()
    }

    DashboardScreen(dashBoardUiState = dashboardState, onCategoryClicked = {}, onProductClicked = navigateToProductDetail) {
        viewModel.searchProducts(it)
    }

}

@Composable
fun DashboardScreen(
    dashBoardUiState: DashBoardUiState,
    onCategoryClicked: (String) -> Unit,
    onProductClicked: (Int)->Unit,
    onSearch: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = "Products",
                onNavigationIconClick = { /*TODO*/ },
                showNavigationIcon = false
            )
        }
    ) { padding ->
        MainUiScreen(Modifier.padding(padding), dashBoardUiState, {}, onProductClicked,{}) { searchQuery ->
            onSearch(searchQuery)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MainUiScreen(
    modifier: Modifier = Modifier,
    dashboardState: DashBoardUiState,
    onCategoryClicked: (String) -> Unit,
    onProductClicked: (Int) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchPerformed: (String) -> Unit,
) {
    var active by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        if (dashboardState.isLoading) {
            TopLoader(Modifier.fillMaxWidth())
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }
        if (dashboardState.error != null) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorText(
                    text = dashboardState.error.message
                        ?: stringResource(id = com.example.resources.R.string.something_went_wrong)
                )
            }
        }
        androidx.compose.material3.SearchBar(
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(10.dp),
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                onSearchTextChanged(it)
            },
            onSearch = {
                active = false
                onSearchPerformed(it)
            },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (searchQuery.isNotEmpty()) {
                                searchQuery = ""
                                onSearchTextChanged("")
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                    )
                }
            },
            placeholder = { Text(text = stringResource(id = com.example.resources.R.string.search)) },
        ) {}


        if (dashboardState.searchList.isNullOrEmpty().not()) {
            ProductList(
                products = dashboardState.searchList!!,
                onProductClicked = onProductClicked,
                isGrid = false
            )
        }

        val productList = dashboardState.allProductFlow?.collectAsLazyPagingItems()
        if (dashboardState.allCategories.isNullOrEmpty().not()) {
            CategoryList(categories = dashboardState.allCategories!!) {

            }
        }
        if (productList != null) {
            ProductPagedList(
                products = productList,
                onProductClicked = onProductClicked,
                onPageLoading = {}
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    AssessmentTheme(false) {
        DashboardScreen(DashBoardUiState(), {},{}) {}
    }
}
