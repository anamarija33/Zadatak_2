package org.unizd.rma.brkic.presentation.skincareItem.create

import android.util.Size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.unizd.rma.brkic.domain.models.ProductType
import org.unizd.rma.brkic.domain.models.SkincareItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone





@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel  = hiltViewModel(),
    skincareItem: SkincareItem? = null,
    productIdForEdit: Int? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    onPhotoClick: ()-> Unit = {},
    photoPath: String? = null,
    onPhotoConsumed: ()-> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val brand by viewModel.brand.collectAsStateWithLifecycle()
    val typeOfProduct by viewModel.typeOfProduct.collectAsStateWithLifecycle()
    val imageUri by viewModel.imageUri.collectAsStateWithLifecycle()
    val openingDate by viewModel.openingDate.collectAsStateWithLifecycle()


    LaunchedEffect(productIdForEdit) {
        if (productIdForEdit != null) {
            viewModel.loadProductForEdit(productIdForEdit)
        }
    }

//    LaunchedEffect(skincareItem) {
//        skincareItem?.let {
//            viewModel.loadProductForEditDirect(it)
//        }
//    }

    LaunchedEffect(uiState) {
        if (uiState is AddProductUiState.Success) {
            onSaveSuccess()
        }
    }

    LaunchedEffect(photoPath) {
        photoPath.let{
            viewModel.setImageUri(it)
            onPhotoConsumed()
        }
    }

    Scaffold (
    topBar = {
        TopAppBar(
            title = {
                Text(
                    if (productIdForEdit != null) "Edit Product" else "Add product"
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
                        label = {Text("Name*")},
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




                    var showDatePicker by remember { mutableStateOf(false) }
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = openingDate
                    )

                    val dateFormatter = remember {
                        SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }
                    }

                    TextField(
                        value = openingDate?.let { dateFormatter.format(Date(it)) } ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Opening date") },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Pick a date")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        datePickerState.selectedDateMillis?.let {
                                            viewModel.setopeningDate(it)
                                        }
                                        showDatePicker = false
                                    }
                                ) { Text("U redu") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text("Odustani")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

// https://developer.android.com/develop/ui/compose/components/menu
                    //youtu.be/_lee9vN1FiE
                    var expanded by remember { mutableStateOf(false) }
                  ExposedDropdownMenuBox ( expanded, {expanded= it}) {

                        TextField(
                            readOnly = true,
                            label = {Text ("Type of product")},
                            onValueChange = {},
                            value = typeOfProduct?.displayValue?:"",
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More options")
                            }
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                ProductType.entries.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type.displayValue) },
                                        onClick = { viewModel.setTypeOfProduct(type)
                                        expanded=false}
                                    )
                            }
                        }

                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    if (imageUri.isNotEmpty()) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }


                    Button(
                        onClick = onPhotoClick,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Icon(Icons.Filled.PhotoCamera,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Take a Picture!")
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button (
                        onClick = {viewModel.saveProduct()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Save")
                    }

                }
            }
        }
    }

}
@Composable
fun MinimalDropdownMenu(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}
