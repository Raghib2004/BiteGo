package com.example.bitego.screens.cartscreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitego.data.FoodItem
import com.example.bitego.viewmodel.FoodViewModel

@Composable
fun CartScreen(foodViewModel: FoodViewModel) {

    val cartItems = foodViewModel.cartItems

    val subtotal = foodViewModel.getSubtotal()

    val deliveryFee = foodViewModel.getDeliveryFee()

    val total = foodViewModel.getTotal()

    val itemCount = cartItems.values.sum()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Cart Heading

        Text(
            text = "My Cart",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$itemCount items in your cart",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Empty Cart

        if (cartItems.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🛒",
                    fontSize = 80.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your cart is empty",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Add some delicious food from Home.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

        } else {

            // Scrollable Cart Items

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                cartItems.entries.forEach { (food, quantity) ->

                    CartFoodCard(
                        food = food,
                        quantity = quantity,

                        onIncrease = {
                            foodViewModel.addToCart(food)
                        },

                        onDecrease = {
                            foodViewModel.decreaseQuantity(food)
                        },

                        onRemove = {
                            foodViewModel.removeFromCart(food)
                        }
                    )
                }
            }
        }

        // Order Summary

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Order Summary",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        PriceRow("Subtotal", subtotal)

        Spacer(modifier = Modifier.height(8.dp))

        PriceRow("Delivery Fee", deliveryFee)

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(12.dp))

        // Total Price

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Total",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "₹$total",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6B35)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Checkout Button

        Button(
            onClick = {
                // Checkout navigation will be added later
            },
            enabled = cartItems.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6B35)
            )
        ) {

            Text(
                text = "Proceed to Checkout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// Cart Food Card

@Composable
fun CartFoodCard(
    food: FoodItem,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {

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

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "₹${food.price} each",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Text(
                    text = "₹${food.price * quantity}",
                    color = Color(0xFFFF6B35),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                // Quantity Controls

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextButton(
                        onClick = onDecrease
                    ) {

                        Text(
                            text = "−",
                            fontSize = 22.sp,
                            color = Color(0xFFFF6B35)
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(
                        onClick = onIncrease
                    ) {

                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            color = Color(0xFFFF6B35)
                        )
                    }
                }

                // Remove Item

                TextButton(
                    onClick = onRemove,
                    contentPadding = PaddingValues(0.dp)
                ) {

                    Text(
                        text = "Remove",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


// Price Row

@Composable
fun PriceRow(
    title: String,
    amount: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.Gray
        )

        Text(
            text = "₹$amount",
            fontWeight = FontWeight.Medium
        )
    }
}