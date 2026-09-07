package org.unizd.rma.brkic.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.unizd.rma.brkic.domain.models.SkincareItem

@Composable
fun ProductList (
    products: List<SkincareItem>,
    onProductClick: (SkincareItem) -> Unit,
    onDeleteClick: (SkincareItem) -> Unit
) {
    LazyColumn (
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                product,
                onProductClick = { onProductClick(product) },
                onDeleteClick = { onDeleteClick(product) }
            )
        }
    }
}