package com.example.shoppinglist

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