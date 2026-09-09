package org.unizd.rma.brkic.presentation.skincareItem.create

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.unizd.rma.brkic.domain.models.SkincareItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel  = hiltViewModel(),
    skincareItem: SkincareItem? = null,
    productIdForEdit: Int? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val brand by viewModel.brand.collectAsStateWithLifecycle()
    val typeOfProduct by viewModel.typeOfProduct.collectAsStateWithLifecycle()
    val imageUri by viewModel.imageUri.collectAsStateWithLifecycle()

    LaunchedEffect(productIdForEdit) {
        if (productIdForEdit != null) {
            viewModel.loadProductForEdit(productIdForEdit)
        }
    }

    LaunchedEffect(skincareItem) {
        skincareItem?.let {
            viewModel.loadProductForEditDirect(it)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AddProductUiState.Success) {
            onSaveSuccess()
        }
    }

    Scaffold (
    topBar = {
        TopAppBar(
            title = {
                Text(
                    if (productIdForEdit != null) "Uredi kontakt" else "Dodaj kontakt"
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Nazad")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            when (uiState) {
                is AddProductUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AddProductUiState.Error -> {
                    Text(
                        text = (uiState as AddProductUiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                else -> {
                    TextField(
                        value = name,
                        onValueChange = { viewModel.setName(it) },
                        label = {Text("Ime*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

                    TextField(
                        value = brand,
                        onValueChange = { viewModel.setBrand(it) },
                        label = {Text("Brand*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

//                    TextField(
//                        value = typeOfProduct,
//                        onValueChange = { viewModel.setTypeOfProduct(null) },
//                        label = {Text("Type of product*")},
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 16.dp),
//                        singleLine = true
//                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button (
                        onClick = {viewModel.saveProduct()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Spremi")
                    }

                }
            }
        }
    }

}