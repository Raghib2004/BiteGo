package com.example.bitego.screens.favoritescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitego.R
import com.example.bitego.data.FoodItem
import com.example.bitego.viewmodel.FoodViewModel

@Composable
fun FavoriteScreen(foodViewModel: FoodViewModel) {

    val favorites = foodViewModel.favorites

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "My Favorites",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your favourite meals, all in one place.",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Empty Favorites Screen

        if (favorites.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "No Favorites",
                    tint = Color.LightGray,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "No favorites yet",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Save your favorite meals from Home.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

        } else {

            Text(
                text = "${favorites.size} saved items",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Display Dynamic Favorite Items

            favorites.forEach { food ->

                FavoriteFoodCard(
                    food = food,
                    foodViewModel = foodViewModel
                )
            }
        }
    }
}


// Favorite Food Card

@Composable
fun FavoriteFoodCard(
    food: FoodItem,
    foodViewModel: FoodViewModel
) {

    val quantity = foodViewModel.cartItems[food] ?: 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Food Image

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        Color(0xFFFFE8D9),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = food.emoji,
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Food Information

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = food.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = food.description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "₹${food.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B35)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Add to Cart Button

                Button(
                    onClick = {
                        foodViewModel.addToCart(food)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B35)
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    )
                ) {

                    Text(
                        text = if (quantity > 0)
                            "Added ($quantity) +"
                        else
                            "Add to Cart",
                        fontSize = 12.sp
                    )
                }
            }

            // Remove From Favorites

            IconButton(
                onClick = {
                    foodViewModel.toggleFavorite(food)
                }
            ) {

                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Remove from Favorites",
                    tint = Color(0xFFFF6B35),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}