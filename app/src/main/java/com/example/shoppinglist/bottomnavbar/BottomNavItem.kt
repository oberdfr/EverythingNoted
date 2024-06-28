package com.example.shoppinglist.bottomnavbar

import androidx.compose.runtime.Immutable
import com.example.shoppinglist.R

@Immutable
sealed class BottomNavItem(
    var title: String,
    var icon: Int
) {
    object Shopping :
        BottomNavItem(
            "Shopping",
            R.drawable.shoppingcart
        )

    object Notes :
        BottomNavItem(
            "Notes",
            R.drawable.notes_24px
        )

    object Todo :
        BottomNavItem(
            "Todo",
            R.drawable.calendar_month_24px
        )

    object People :
        BottomNavItem(
            "People",
            R.drawable.contacts_product_24px
        )
}