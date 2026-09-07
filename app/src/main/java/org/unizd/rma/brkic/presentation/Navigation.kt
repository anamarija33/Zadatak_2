package org.unizd.rma.brkic.presentation

import org.unizd.rma.brkic.presentation.camera.CameraScreen



import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.unizd.rma.brkic.SkincareApp
import org.unizd.rma.brkic.presentation.skincareItem.ProductsScreen
import org.unizd.rma.brkic.presentation.skincareItem.create.AddProductScreen
import org.unizd.rma.brkic.presentation.skincareItem.detail.ProductDetailScreen


sealed class Screen(val route: String) {
    object Products : Screen("products_screen")
    object ProductDetail : Screen("product_detail_screen/{productId}") {
        fun createRoute(productId: Int) = "product_detail_screen/$productId"
    }
    object AddProduct: Screen("add_product_screen")
    object EditProduct: Screen("edit_product/{productId}") {
        fun createRoute(productId: Int) = "edit_product/$productId"
    }
    object Camera: Screen("camera_screen/{productId}") {
    }

    fun createRoute(productId: Int) = "camera_screen/$productId"
}

@Composable
fun Navigation () {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Products.route
    ) {
        composable(Screen.Products.route) {
            ProductsScreen(
                onAddClick = {
                    navController.navigate(Screen.AddProduct.route)
                },
                onNavigateToDetail = { skincareItem ->
                    navController.navigate(Screen.ProductDetail.createRoute(skincareItem.id))
                }
            )
        }

        composable(Screen.AddProduct.route) {
            AddProductScreen (
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable (Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {type = NavType.IntType}
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            ProductDetailScreen(
                productId = productId!!,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { skincareItem ->
                    navController.navigate(Screen.EditProduct.createRoute(skincareItem.id))
                },
                onPhotoClick = {
                    navController.navigate(Screen.Camera.createRoute(productId))
                }
            )
        }

        composable (Screen.AddProduct.route) {
            AddProductScreen (
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            route = Screen.EditProduct.route,
            arguments = listOf(
                navArgument("productId") {type = NavType.IntType}
            )
        ) {backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            AddProductScreen(
                skincareItem = null,
                productIdForEdit = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Camera.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            CameraScreen(
                context = context,
                productId = productId,
                onPhotoTaken = { photoPath ->
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    }
}