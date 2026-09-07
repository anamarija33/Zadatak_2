package org.unizd.rma.brkic.presentation.skincareItem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.presentation.ProductUiState
import org.unizd.rma.brkic.presentation.ProductViewModel
import org.unizd.rma.brkic.presentation.components.EmptyScreen
import org.unizd.rma.brkic.presentation.components.ErrorScreen
import org.unizd.rma.brkic.presentation.components.ProductList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen (
    viewModel: ProductViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onContactClick: (SkincareItem) -> Unit = {},
    onNavigateToDetail: (SkincareItem) -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title  = {
                    Text("Moji proizvodi")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Dodaj kontakt")
            }
        }
    ) {
            paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Content
            when (uiState) {
                is ProductUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ProductUiState.Success -> {
                    ProductList(
                        products = (uiState as ProductUiState.Success).skincareItem,
                        onProductClick = { skincareItem ->
                            viewModel.selectProduct(skincareItem)
                            onNavigateToDetail(skincareItem)
                        },
                        onDeleteClick = { skincareItem ->
                            viewModel.deleteProduct(skincareItem.id)
                        }
                    )
                }

                is ProductUiState.Error -> {
                    ErrorScreen(
                        message = (uiState as ProductUiState.Error).message,
                        onRetry = viewModel::clearError
                    )
                }

                is ProductUiState.Empty -> {
                    EmptyScreen (onAddClick = onAddClick)
                }
            }

        }
    }
}