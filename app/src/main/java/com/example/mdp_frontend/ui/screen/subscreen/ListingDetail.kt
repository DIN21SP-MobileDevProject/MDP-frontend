package com.example.mdp_frontend.ui.screen.subscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.ui.components.SubscreenHeader


@Composable
fun ListingDetailScreen(
    listingId: String?,
    onNavUp: () -> Unit,
) {
    val viewModel: ListingDetailViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(listingId) {
        viewModel.loadListing(listingId)
    }

    when (val response = viewModel.listingResponse) {
        is Response.Failure -> Text(text = response.e.toString() ?: "Unknown error")
        is Response.Success<Listing> -> {
            val listing = response.data
            SubscreenHeader(title = "Listing Details", onNavUp = onNavUp) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = listing.pictureUrl),
                        contentDescription = "Listing picture",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = listing.title,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = listing.description,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = rememberAsyncImagePainter(model = listing.publisher?.imageUrl),
                                contentDescription = "Publisher picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = listing.publisher?.name ?: "",
                                style = TextStyle(fontSize = 12.sp)
                            )
                            Row {
                                repeat(listing.rating) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = Color.Yellow
                                    )
                                }
                            }
                        }
                        Text(
                            text = "$${listing.price}",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { /* react to listing */
                                Toast.makeText(context, "Reacted to listing", Toast.LENGTH_SHORT).show()
                            }) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "React")
                            Text(text = "React")
                        }
                        Button(
                            onClick = { /* contact publisher */
                            }) {
                            Icon(imageVector = Icons.Default.Email, contentDescription =
                            "Contact")
                            Text(text =
                            "Contact")
                        }
                    }
                }
            }
        }
        is Response.Loading -> CircularProgressIndicator()
    }
}


/*
@Composable
@Preview
fun ListingDetailScreenPreview() {

    ListingDetailScreen(
        Listing(
            id = "1",
    pictureUrl = "https://picsum.photos/200",
    title = "Sample Listing",
    description = "This is a sample listing for preview purposes." +
            "senectus et netus et malesuada fames ac turpis egestas. " +
            "Vestibulum tortor quam, feugiat vitae, ultricies eget, " +
            "tempor sit amet, ante. Donec eu libero sit amet quam egestas semper." +
            "Aenean ultricies mi vitae est",
    publisher = User(
        imageUrl = "https://picsum.photos/40",
        name = "John Doe",
    ),
    rating = 4,
    price = 99.99
    )
    )
}

 */