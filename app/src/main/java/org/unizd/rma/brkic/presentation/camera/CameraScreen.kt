package org.unizd.rma.brkic.presentation.camera

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.unizd.rma.brkic.presentation.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    context: Context,
    productId: Int,
    productDetailViewModel: ProductViewModel = hiltViewModel(),
    onPhotoTaken: (photoPath: String) -> Unit,
    onBackClick: () -> Unit
){

}