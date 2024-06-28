package com.example.shoppinglist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

import com.example.shoppinglist.bottomnavbar.*
import com.example.shoppinglist.utils.hasDecimalNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shoppingListItem(
    item: ShoppingItem,
    onRemove: () -> Unit,
) {
    // Creates the CoroutineScope
    val coroutineScope = rememberCoroutineScope()

    // Creates the dismiss state
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {
                    delay(225.milliseconds)
                    onRemove()
                }
                true
            } else {
                false
            }
        }
    )

    var showIcon by remember { mutableStateOf(false) }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.tertiaryContainer
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                }, label = "Changing color"
            )

            showIcon = when (dismissState.targetValue){
                SwipeToDismissBoxValue.Settled -> false
                SwipeToDismissBoxValue.StartToEnd -> true
                SwipeToDismissBoxValue.EndToStart -> true
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(0.dp, Color.Transparent),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(color,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (showIcon) {
                    Icon(
                        imageVector = (if (color == MaterialTheme.colorScheme.errorContainer) Icons.Default.Delete else Icons.Default.Edit),
                        contentDescription = "Dynamic Swipe Icon",
                        modifier = Modifier.align(if (color == MaterialTheme.colorScheme.errorContainer) Alignment.CenterEnd else Alignment.CenterStart)
                            .padding(horizontal = 14.dp),
                        tint = Color.White
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSecondary),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            if (item.description.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 14.dp)
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSecondary),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = item.quantityDesc,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.End
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.description,
                            modifier = Modifier.padding(horizontal = 24.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = item.quantityDesc,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
fun ShoppingListApp(){
    var showDialog by remember { mutableStateOf(false) }
    var showDescDialog by remember { mutableStateOf(false) }
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }

    fun resetItemFields(){
        itemName = ""
        itemQuantity = ""
        itemDescription = ""
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Shopping List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        },

        bottomBar = {
            val items = listOf(
                BottomNavItem.Shopping,
                BottomNavItem.Notes,
                BottomNavItem.Todo,
                BottomNavItem.People
            )
            BottomNavigation(items)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(sItems, key = { it.id }){ item ->
                            shoppingListItem(
                                item,
                                onRemove = { sItems = sItems.filterNot { it.id == item.id } }
                            )
                        }
                    }
                }
            }

            if (showDialog){
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        resetItemFields()
                    },

                    title = {
                        Text(
                            text = "Add a product",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },

                    text = {
                        Column {
                            OutlinedTextField(
                                value = itemName,
                                onValueChange = { itemName = it },
                                label = {Text(text = "Product Name")},
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = itemQuantity,
                                onValueChange = { itemQuantity = it },
                                label = {Text(text = "Quantity")},
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .padding(14.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Description", modifier = Modifier.padding(vertical = 14.dp), fontSize = 16.sp)
                                Switch(
                                    checked = showDescDialog,
                                    onCheckedChange = {
                                        showDescDialog = it
                                    }
                                )
                            }

                            if (showDescDialog){
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = itemDescription,
                                    onValueChange = { itemDescription = it },
                                    label = {Text(text = "Optional Description")},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                )
                            }
                        }
                    },

                    confirmButton = {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)) {

                            Button(onClick = {
                                if (itemName.isNotBlank()) {
                                    val finalQuantityDesc: String
                                    val finalQuantity: Float

                                    // Check if the input is an integer
                                    val intValue = itemQuantity.toIntOrNull()
                                    val floatValue = itemQuantity.toFloatOrNull()
                                    if (intValue != null || floatValue != null) {
                                        if (hasDecimalNumber(itemQuantity)){
                                            finalQuantityDesc = "x" + floatValue.toString()
                                            finalQuantity = itemQuantity.toFloat()
                                        } else {
                                            finalQuantityDesc = "x" + intValue.toString()
                                            finalQuantity = itemQuantity.toFloat()
                                        }
                                    } else {
                                        finalQuantityDesc = itemQuantity
                                        finalQuantity = 0f
                                    }

                                    val newItem = ShoppingItem(
                                        id = sItems.size + 1,
                                        name = itemName,
                                        // itemQuantity is the fetched data from the text field
                                        quantityDesc = finalQuantityDesc,
                                        quantity = finalQuantity,
                                        description = itemDescription
                                    )
                                    sItems = sItems + newItem
                                    showDialog = false
                                    resetItemFields()
                                }

                            }) {
                                Text(text = "Add")
                            }
                        }
                    },
                )
            }
        }
    }
}