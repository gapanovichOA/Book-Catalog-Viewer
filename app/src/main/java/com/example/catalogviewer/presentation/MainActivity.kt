package com.example.catalogviewer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.catalogviewer.R
import com.example.catalogviewer.presentation.navigation.BookDetail
import com.example.catalogviewer.presentation.navigation.CatalogList
import com.example.catalogviewer.presentation.navigation.NavigationManager
import com.example.catalogviewer.presentation.navigation.entryProvider
import com.example.catalogviewer.presentation.theme.CatalogViewerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.listOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatalogViewerTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(state.error) {
                    state.error?.let { message ->
                        if (message.isNotEmpty()) {
                            snackbarHostState.showSnackbar(message)
                            viewModel.onIntent(UiIntent.ClearError)
                        }
                    }
                }

                val backStack = navigationManager.backStack

                val provider = remember { entryProvider(viewModel) }

                val canNavigateBack = backStack.size > 1
                val currentKey = backStack.lastOrNull()

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = when(currentKey) {
                                    is BookDetail -> "Book Details"
                                    is CatalogList -> "Book Catalog"
                                    else -> "Catalog Viewer"
                                })
                            },
                            navigationIcon = {
                                if (canNavigateBack) {
                                    IconButton(onClick = { navigationManager.navigateBack() }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        NavDisplay(
                            backStack = backStack,
                            onBack = {
                                navigationManager.navigateBack()
                            },
                            entryProvider = provider,
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                            ),
                        )
                    }
                }
            }
        }
    }
}