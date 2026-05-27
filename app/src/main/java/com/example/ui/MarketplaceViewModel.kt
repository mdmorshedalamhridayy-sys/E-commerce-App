package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UserAccount(
    val name: String,
    val phone: String,
    val pass: String,
    val address: String
)

class MarketplaceViewModel(
    application: Application,
    private val repository: MarketplaceRepository
) : AndroidViewModel(application) {

    // --- LANGUAGE & SECTION STATES ---
    var isEnglish = MutableStateFlow(true)
        private set

    var currentSection = MutableStateFlow("BUYER") // "BUYER", "SELLER", "ADMIN"
        private set

    // --- USER LOGIN & REGISTRATION SESSIONS ---
    var isUserLoggedIn = MutableStateFlow(false)
        private set

    var registeredUsers = MutableStateFlow(listOf(
        UserAccount("Morshed Alam Hriday", "01754237253", "user123", "Mirpur-10, Dhaka, Bangladesh")
    ))
        private set

    // --- USER PROFILE & ROLE SELECTION ---
    val currentBuyerName = MutableStateFlow("Morshed Alam Hriday")
    val currentBuyerPhone = MutableStateFlow("01754237253")
    val currentBuyerAddress = MutableStateFlow("Mirpur-10, Dhaka, Bangladesh")

    var selectedSellerIdForPortal = MutableStateFlow<Long>(1L) // Logged in as Seller #1 by default
        private set

    var isAdminAuthenticated = MutableStateFlow(false)
        private set

    // --- SEARCH, FILTER & CATEGORY ---
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All") // "All", "Fashion", "Handicraft", "Electronics"
    val sortOption = MutableStateFlow("Popularity") // "Popularity", "LowToHigh", "HighToLow"

    // --- ACTIVE SELECTIONS FOR PAGES ---
    val selectedProductId = MutableStateFlow<Long?>(null)
    val checkoutActive = MutableStateFlow(false)
    val activeTabInProfile = MutableStateFlow("ORDERS") // "ORDERS", "WISHLIST"
    val isDisputingOrderId = MutableStateFlow<Long?>(null)
    val activeNotificationCenterOpen = MutableStateFlow(false)

    // --- PRODUCTS & CATEGORIES FLOWS ---
    val allSellers: StateFlow<List<SellerEntity>> = repository.allSellers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProducts: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteProductIds: StateFlow<List<Long>> = repository.favoriteProductIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotifications: StateFlow<List<NotificationEntity>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationsCount: StateFlow<Int> = repository.allNotifications
        .map { list -> list.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Reactive filtering of approved products for Buyer Home Screen
    val filteredProducts: StateFlow<List<ProductEntity>> = combine(
        repository.approvedProducts,
        searchQuery,
        selectedCategory,
        sortOption
    ) { products, query, category, sort ->
        var list = products

        // Search
        if (query.isNotBlank()) {
            list = list.filter {
                it.titleEn.contains(query, ignoreCase = true) ||
                        it.titleBn.contains(query, ignoreCase = true) ||
                        it.descriptionEn.contains(query, ignoreCase = true) ||
                        it.descriptionBn.contains(query, ignoreCase = true)
            }
        }

        // Category
        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }

        // Sort
        when (sort) {
            "LowToHigh" -> list.sortedBy { it.price }
            "HighToLow" -> list.sortedByDescending { it.price }
            else -> list.sortedByDescending { it.popularityIndex } // Popularity
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- CART STATE ---
    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartSubtotal: StateFlow<Double> = repository.cartItems
        .map { list -> list.sumOf { it.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val cartTotalItemsCount: StateFlow<Int> = repository.cartItems
        .map { list -> list.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- ORDER & TRANSACTION HISTORY FLOWS ---
    val allOrders: StateFlow<List<OrderEntity>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOrderItems: StateFlow<List<OrderItemEntity>> = repository.allOrderItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- SELLER CONTEXT DEPENDENT FLOWS ---
    val currentLoggedInSeller: StateFlow<SellerEntity?> = selectedSellerIdForPortal
        .flatMapLatest { id ->
            repository.allSellers.map { sellers -> sellers.find { it.id == id } }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val sellerProducts: StateFlow<List<ProductEntity>> = selectedSellerIdForPortal
        .flatMapLatest { id ->
            repository.allProducts.map { products -> products.filter { it.sellerId == id } }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sellerOrders: StateFlow<List<Pair<OrderEntity, List<OrderItemEntity>>>> = combine(
        repository.allOrders,
        repository.allOrderItems,
        selectedSellerIdForPortal
    ) { orders, items, sellerId ->
        orders.mapNotNull { order ->
            val matchingItems = items.filter { it.orderId == order.id && it.sellerId == sellerId }
            if (matchingItems.isNotEmpty()) {
                Pair(order, matchingItems)
            } else {
                null
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- ACTIONS & OPERATIONS ---

    fun toggleLanguage() {
        isEnglish.value = !isEnglish.value
    }

    fun setSection(section: String) {
        currentSection.value = section
    }

    fun changeSellerIdentity(id: Long) {
        selectedSellerIdForPortal.value = id
    }

    fun authenticateAdmin(pass: String): Boolean {
        return if (pass == "admin123" || pass == "admin") {
            isAdminAuthenticated.value = true
            true
        } else {
            false
        }
    }

    fun logoutAdmin() {
        isAdminAuthenticated.value = false
    }

    // --- USER PROFILE AUTHENTICATION ACTIONS ---
    fun authenticateUser(phone: String, pass: String): Boolean {
        if (phone.isBlank() || pass.isBlank()) return false
        val user = registeredUsers.value.find { it.phone == phone && it.pass == pass }
        return if (user != null) {
            currentBuyerName.value = user.name
            currentBuyerPhone.value = user.phone
            currentBuyerAddress.value = user.address
            isUserLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun registerUser(name: String, phone: String, pass: String, address: String): Boolean {
        if (name.isBlank() || phone.isBlank() || pass.isBlank()) return false
        val exists = registeredUsers.value.any { it.phone == phone }
        if (exists) return false
        
        val newList = registeredUsers.value + UserAccount(name, phone, pass, address)
        registeredUsers.value = newList
        currentBuyerName.value = name
        currentBuyerPhone.value = phone
        currentBuyerAddress.value = address
        isUserLoggedIn.value = true
        return true
    }

    fun logoutUser() {
        isUserLoggedIn.value = false
        currentBuyerName.value = "Guest User"
        currentBuyerPhone.value = ""
        currentBuyerAddress.value = ""
    }

    // --- BUYER ACTIONS ---
    fun toggleFavorite(productId: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(productId)
        }
    }

    fun addItemToCart(product: ProductEntity, quantity: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(product, quantity)
        }
    }

    fun adjustCartQuantity(cartItemId: Long, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(cartItemId, quantity)
        }
    }

    fun removeCartItem(cartItemId: Long) {
        viewModelScope.launch {
            repository.deleteCartItem(cartItemId)
        }
    }

    // Custom notification can be logged or triggered
    fun sendCustomNotification(title: String, message: String) {
        // Simple log or helper
    }

    fun triggerOrderPlacement(paymentMethod: String) {
        viewModelScope.launch {
            val items = cartItems.value
            val total = cartSubtotal.value + if (cartSubtotal.value > 1500) 0.0 else 100.0 // 100 Taka delivery charge under 1500
            
            if (items.isNotEmpty()) {
                repository.placeOrder(
                    buyerName = currentBuyerName.value,
                    buyerPhone = currentBuyerPhone.value,
                    buyerAddress = currentBuyerAddress.value,
                    paymentMethod = paymentMethod,
                    items = items,
                    totalPrice = total
                )
                checkoutActive.value = false
            }
        }
    }

    fun fileOrderDispute(orderId: Long, reason: String) {
        viewModelScope.launch {
            repository.fileDispute(orderId, reason)
            isDisputingOrderId.value = null
        }
    }

    // --- SELLER ACTIONS ---
    fun submitNewListing(
        titleEn: String,
        titleBn: String,
        descEn: String,
        descBn: String,
        price: Double,
        stock: Int,
        category: String
    ) {
        viewModelScope.launch {
            val seller = currentLoggedInSeller.value ?: return@launch
            val newProduct = ProductEntity(
                titleEn = titleEn,
                titleBn = titleBn,
                descriptionEn = descEn,
                descriptionBn = descBn,
                price = price,
                category = category,
                stock = stock,
                rating = 5.0f,
                reviewCount = 0,
                popularityIndex = 200,
                isApproved = false, // Must be approved by Admin first!
                sellerId = seller.id,
                sellerBusinessName = seller.businessName,
                imageUrlMarkdown = when (category) {
                    "Fashion" -> "fashion_custom"
                    "Electronics" -> "electronics_custom"
                    else -> "handicraft_custom"
                }
            )
            repository.insertProduct(newProduct)
        }
    }

    fun updateSellerProductStock(productId: Long, newStock: Int) {
        viewModelScope.launch {
            repository.updateProductStock(productId, newStock)
        }
    }

    fun deleteSellerProduct(productId: Long) {
        viewModelScope.launch {
            repository.deleteProduct(productId)
        }
    }

    fun updateMerchantOrderStatus(orderId: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
        }
    }

    // --- ADMIN ACTIONS ---
    fun approveProduct(productId: Long) {
        viewModelScope.launch {
            repository.updateProductApproval(productId, true)
        }
    }

    fun rejectAndDeleteProduct(productId: Long) {
        viewModelScope.launch {
            repository.deleteProduct(productId)
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            repository.clearNotifications()
        }
    }

    fun markNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }
}

class MarketplaceViewModelFactory(
    private val application: Application,
    private val repository: MarketplaceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketplaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketplaceViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
