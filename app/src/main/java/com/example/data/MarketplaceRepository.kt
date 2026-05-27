package com.example.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketplaceRepository(private val dao: MarketplaceDao) {

    val allSellers: Flow<List<SellerEntity>> = dao.getAllSellers()
    val allProducts: Flow<List<ProductEntity>> = dao.getAllProducts()
    val approvedProducts: Flow<List<ProductEntity>> = dao.getApprovedProducts()
    val cartItems: Flow<List<CartItemEntity>> = dao.getCartItems()
    val allOrders: Flow<List<OrderEntity>> = dao.getAllOrders()
    val allOrderItems: Flow<List<OrderItemEntity>> = dao.getAllOrderItems()
    val favoriteProductIds: Flow<List<Long>> = dao.getFavoriteProductIds()
    val allNotifications: Flow<List<NotificationEntity>> = dao.getAllNotifications()

    init {
        // Run database initialization on a background coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sellersList = dao.getAllSellers().first()
                if (sellersList.isEmpty()) {
                    Log.d("MarketplaceRepository", "Initializing Database with 20 sellers and 100 products...")
                    
                    // Insert sellers
                    PrepopulatedData.sellers.forEach { seller ->
                        dao.insertSeller(seller)
                    }

                    // Insert products
                    PrepopulatedData.products.forEach { product ->
                        dao.insertProduct(product)
                    }

                    // Insert initial notifications
                    dao.insertNotification(
                        NotificationEntity(
                            title = "Welcome to Hriday Store / হৃদয় স্টোরে স্বাগতম!",
                            message = "Enjoy premium shopping with dual language support and fast Cash on Delivery across Bangladesh. / দেশজুড়ে ক্যাশ অন ডেলিভারি সুবিধায় কেনাকাটা করুন।"
                        )
                    )
                    dao.insertNotification(
                        NotificationEntity(
                            title = "Exclusive Campaign Active / স্পেশাল ক্যাম্পেইন চালু",
                            message = "New Traditional Handicraft styles and Fashion wears have been added for your choice! / আপনার জন্য নতুন ঐতিহ্যবাহী হস্তশিল্প এবং ফ্যাশন ডিজাইনের কালেকশন সাজানো হয়েছে।"
                        )
                    )
                    Log.d("MarketplaceRepository", "Database initialized successfully.")
                }
            } catch (e: Exception) {
                Log.e("MarketplaceRepository", "Failed to initialize database prepopulation", e)
            }
        }
    }

    // --- Product Methods ---
    suspend fun getProductById(id: Long): ProductEntity? = withContext(Dispatchers.IO) {
        dao.getProductById(id)
    }

    suspend fun insertProduct(product: ProductEntity): Long = withContext(Dispatchers.IO) {
        dao.insertProduct(product)
    }

    suspend fun updateProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        dao.updateProduct(product)
    }

    suspend fun deleteProduct(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteProductById(id)
    }

    suspend fun updateProductApproval(id: Long, isApproved: Boolean) = withContext(Dispatchers.IO) {
        dao.updateProductApproval(id, isApproved)
        // Add a notification for visual feedback
        val prodName = dao.getProductById(id)?.titleEn ?: "Product"
        dao.insertNotification(
            NotificationEntity(
                title = "Product Approved / পণ্য অনুমোদিত",
                message = "Seller listing '$prodName' has been approved by Admin and is now live on the Hriday Store marketplace!"
            )
        )
    }

    suspend fun updateProductStock(id: Long, newStock: Int) = withContext(Dispatchers.IO) {
        dao.updateProductStock(id, newStock)
    }

    // --- Cart Methods ---
    suspend fun addToCart(product: ProductEntity, quantity: Int = 1) = withContext(Dispatchers.IO) {
        val existing = dao.getCartItemByProductId(product.id)
        if (existing != null) {
            dao.updateCartItemQuantity(existing.id, existing.quantity + quantity)
        } else {
            dao.insertCartItem(
                CartItemEntity(
                    productId = product.id,
                    productTitleEn = product.titleEn,
                    productTitleBn = product.titleBn,
                    price = product.price,
                    imageUrlMarkdown = product.imageUrlMarkdown,
                    quantity = quantity,
                    sellerId = product.sellerId,
                    sellerBusinessName = product.sellerBusinessName
                )
            )
        }
    }

    suspend fun updateCartQuantity(cartItemId: Long, quantity: Int) = withContext(Dispatchers.IO) {
        if (quantity <= 0) {
            dao.deleteCartItem(cartItemId)
        } else {
            dao.updateCartItemQuantity(cartItemId, quantity)
        }
    }

    suspend fun deleteCartItem(cartItemId: Long) = withContext(Dispatchers.IO) {
        dao.deleteCartItem(cartItemId)
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        dao.clearCart()
    }

    // --- Order Methods ---
    suspend fun placeOrder(
        buyerName: String,
        buyerPhone: String,
        buyerAddress: String,
        paymentMethod: String,
        items: List<CartItemEntity>,
        totalPrice: Double
    ): Long = withContext(Dispatchers.IO) {
        val trackingNumber = "HS-" + (100000..999999).random()
        val order = OrderEntity(
            buyerName = buyerName,
            buyerPhone = buyerPhone,
            buyerAddress = buyerAddress,
            totalPrice = totalPrice,
            paymentMethod = paymentMethod,
            paymentStatus = if (paymentMethod == "Online Payment") "Paid" else "Pending",
            orderStatus = "Pending",
            trackingNumber = trackingNumber,
            orderDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
        )

        val orderId = dao.insertOrder(order)

        // Insert order items and decrease stock
        items.forEach { cartItem ->
            dao.insertOrderItem(
                OrderItemEntity(
                    orderId = orderId,
                    productId = cartItem.productId,
                    productTitleEn = cartItem.productTitleEn,
                    productTitleBn = cartItem.productTitleBn,
                    quantity = cartItem.quantity,
                    price = cartItem.price,
                    sellerId = cartItem.sellerId,
                    sellerBusinessName = cartItem.sellerBusinessName
                )
            )

            // Update product stock if available
            val product = dao.getProductById(cartItem.productId)
            if (product != null) {
                val newStock = (product.stock - cartItem.quantity).coerceAtLeast(0)
                dao.updateProductStock(product.id, newStock)
            }

            // Update seller analytics & earnings
            dao.updateSellerSales(cartItem.sellerId, cartItem.price * cartItem.quantity)
        }

        // Send a push notification simulation
        dao.insertNotification(
            NotificationEntity(
                title = "Order Confirmed / অর্ডার নিশ্চিত করা হয়েছে",
                message = "Your order with Tracking ID $trackingNumber of BDT $totalPrice has been placed successfully via $paymentMethod!"
            )
        )

        // Clear the cart
        dao.clearCart()
        orderId
    }

    suspend fun updateOrderStatus(orderId: Long, status: String) = withContext(Dispatchers.IO) {
        dao.updateOrderStatus(orderId, status)
        val order = dao.getOrderById(orderId)
        if (order != null) {
            dao.insertNotification(
                NotificationEntity(
                    title = "Order Status Updated / অর্ডার আপডেট",
                    message = "Your order #${order.trackingNumber} has been marked as '$status' by Hriday Store logistics."
                )
            )
        }
    }

    suspend fun updatePaymentStatus(orderId: Long, status: String) = withContext(Dispatchers.IO) {
        dao.updatePaymentStatus(orderId, status)
    }

    suspend fun fileDispute(orderId: Long, message: String) = withContext(Dispatchers.IO) {
        dao.fileOrderDispute(orderId, message)
        val order = dao.getOrderById(orderId)
        if (order != null) {
            dao.insertNotification(
                NotificationEntity(
                    title = "Dispute Filed / বিরোধ দায়ের করা হয়েছে",
                    message = "A dispute has been raised on Order #${order.trackingNumber}. Reason: $message"
                )
            )
        }
    }

    suspend fun getOrderItemsForOrder(orderId: Long): List<OrderItemEntity> = withContext(Dispatchers.IO) {
        dao.getOrderItemsForOrder(orderId)
    }

    // --- Favorite Methods ---
    suspend fun toggleFavorite(productId: Long) = withContext(Dispatchers.IO) {
        val isFav = dao.isFavorite(productId)
        if (isFav) {
            dao.removeFavorite(productId)
        } else {
            dao.addFavorite(FavoriteEntity(productId))
        }
    }

    suspend fun isFavorite(productId: Long): Boolean = withContext(Dispatchers.IO) {
        dao.isFavorite(productId)
    }

    // --- Seller Admin Methods ---
    suspend fun getSellerById(id: Long): SellerEntity? = withContext(Dispatchers.IO) {
        dao.getSellerById(id)
    }

    suspend fun insertSeller(seller: SellerEntity): Long = withContext(Dispatchers.IO) {
        dao.insertSeller(seller)
    }

    suspend fun clearNotifications() = withContext(Dispatchers.IO) {
        dao.clearNotifications()
    }

    suspend fun markAllNotificationsAsRead() = withContext(Dispatchers.IO) {
        dao.markAllNotificationsAsRead()
    }
}
