package com.example.bitego.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun HomeScreen(foodViewModel: FoodViewModel) {

    var search by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    val scrollState = rememberScrollState()

    val filteredFoods = foodViewModel.foodList.filter { food ->

        val matchesSearch =
            food.name.contains(search.trim(), ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    food.category == selectedCategory

        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        // Location Header

        Text(
            text = "Deliver to",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "📍 Kolkata, India",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Heading

        Text(
            text = "What would you like to eat?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar

        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                selectedCategory = "All"
            },
            placeholder = {
                Text("Search your favorite food")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Promotional Banner

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFF6B35),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Special Offer",
                color = Color.White,
                fontSize = 14.sp
            )

            Text(
                text = "Get 50% OFF",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "On your first order",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    search = ""
                    selectedCategory = "All"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFFF6B35)
                )
            ) {
                Text("Order Now")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Categories Heading

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Categories",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = {
                    search = ""
                    selectedCategory = "All"
                }
            ) {
                Text(
                    text = "View All",
                    color = Color(0xFFFF6B35)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Category Items

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CategoryItem(
                emoji = "🍔",
                name = "Burger",
                isSelected = selectedCategory == "Burger",
                onClick = {
                    search = ""
                    selectedCategory = "Burger"
                }
            )

            CategoryItem(
                emoji = "🍕",
                name = "Pizza",
                isSelected = selectedCategory == "Pizza",
                onClick = {
                    search = ""
                    selectedCategory = "Pizza"
                }
            )

            CategoryItem(
                emoji = "🍗",
                name = "Chicken",
                isSelected = selectedCategory == "Chicken",
                onClick = {
                    search = ""
                    selectedCategory = "Chicken"
                }
            )

            CategoryItem(
                emoji = "🍜",
                name = "Noodles",
                isSelected = selectedCategory == "Noodles",
                onClick = {
                    search = ""
                    selectedCategory = "Noodles"
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Food Section Heading

        Text(
            text = when {
                search.isNotBlank() ->
                    "Search Results (${filteredFoods.size})"

                selectedCategory == "All" ->
                    "Popular Food (${filteredFoods.size})"

                else ->
                    "$selectedCategory (${filteredFoods.size})"
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Empty Search Result

        if (filteredFoods.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "No food items found",
                    color = Color.Gray
                )

                TextButton(
                    onClick = {
                        search = ""
                        selectedCategory = "All"
                    }
                ) {
                    Text(
                        text = "Show All Food",
                        color = Color(0xFFFF6B35)
                    )
                }
            }
        }

        // Dynamic Food Cards

        filteredFoods.chunked(2).forEach { foodRow ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                foodRow.forEach { food ->

                    FoodCard(
                        food = food,
                        foodViewModel = foodViewModel,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (foodRow.size == 1) {

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


// Category Composable

@Composable
fun CategoryItem(
    emoji: String,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(65.dp)
                .background(
                    color = if (isSelected)
                        Color(0xFFFF6B35)
                    else
                        Color(0xFFFFF0E8),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = emoji,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            fontSize = 13.sp,
            color = if (isSelected)
                Color(0xFFFF6B35)
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}


// Food Card Composable

@Composable
fun FoodCard(
    food: FoodItem,
    foodViewModel: FoodViewModel,
    modifier: Modifier = Modifier
) {

    val isFavorite = foodViewModel.isFavorite(food)

    val quantity = foodViewModel.cartItems[food] ?: 0

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        color = Color(0xFFFFF0E8),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = food.emoji,
                    fontSize = 60.sp
                )

                // Favorite Button

                IconButton(
                    onClick = {
                        foodViewModel.toggleFavorite(food)
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {

                    Icon(
                        painter = painterResource(
                            id = R.drawable.heart
                        ),
                        contentDescription = if (isFavorite)
                            "Remove from favorites"
                        else
                            "Add to favorites",
                        tint = if (isFavorite)
                            Color(0xFFFF6B35)
                        else
                            Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = food.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "⭐ ${food.rating}",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "₹${food.price}",
                color = Color(0xFFFF6B35),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Add to Cart Button

            Button(
                onClick = {
                    foodViewModel.addToCart(food)
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B35)
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
    }
}