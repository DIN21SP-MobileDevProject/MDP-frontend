package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.ui.components.SubscreenHeader
import com.example.mdp_frontend.ui.components.TaskServiceRow

/*
@Composable
fun MyServices(services: List<TaskServiceRowItem>, onNavUp: () -> Unit) {
    SubscreenHeader(title = "My Services", onNavUp = onNavUp) {

        val colorScheme = MaterialTheme.colorScheme
        LazyColumn {
            items(services) { service ->
                TaskServiceRow(service = service)
                Divider(color = colorScheme.onSurfaceVariant)

            }
        }
    }
}


 */

@Composable
fun MyServices(
    onNavUp: () -> Unit,
    viewModel: MyServicesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadServices()
    }

    SubscreenHeader(title = "My Services", onNavUp = onNavUp) {
        when (viewModel.servicesResponse) {
            is Response.Loading -> {
                // Display a loading indicator
                CircularProgressIndicator()
            }
            is Response.Success -> {
                val services = (viewModel.servicesResponse as Response.Success).data
                val colorScheme = MaterialTheme.colorScheme
                LazyColumn {
                    items(services) { service ->
                        TaskServiceRow(service = service)
                        Divider(color = colorScheme.onSurfaceVariant)
                    }
                }
            }
            is Response.Failure -> {
                // Display an error message
                Text(text = "Failed to load services")
            }
        }
    }
}

/*
@Preview
@Composable
fun MyServicesPreview() {
    val services = listOf(
        TaskServiceRowItem(
            name = "Example Service 1",
            status = Status.Active,
            price = 100
        ),
        TaskServiceRowItem(
            name = "Example Service 2",
            status = Status.InProgress,
            price = 200
        ),
        TaskServiceRowItem(
            name = "Example Service 3",
            status = Status.Finished,
            price = 300
        )
    )
    MyServices(services = services, onNavUp={})

}

 */