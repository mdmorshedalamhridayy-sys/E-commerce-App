package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sellers")
data class SellerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val businessName: String,
    val email: String,
    val phone: String,
    val rating: Float = 4.5f,
    val totalSales: Double = 0.0,
    val totalOrdersCount: Int = 0,
    val balance: Double = 0.0,
    val pendingPayouts: Double = 0.0,
    val joiningDate: String = "2026-01-10"
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titleEn: String,
    val titleBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val price: Double,
    val category: String, // "Fashion", "Handicraft", "Electronics"
    val stock: Int,
    val rating: Float = 4.2f,
    val reviewCount: Int = 12,
    val popularityIndex: Int = 0, // for popularity filtering
    val isApproved: Boolean = true,
    val sellerId: Long,
    val sellerBusinessName: String,
    val imageUrlMarkdown: String = "", // Holds color representation or asset name/icon description since we load local SVG or Canvas
    val secondaryImageUrls: String = "", // Comma-separated or color names
    val promoApplied: String = "" // "Save 10%", etc.
) {
    fun getTitle(isEnglish: Boolean): String = if (isEnglish) titleEn else titleBn
    fun getDescription(isEnglish: Boolean): String = if (isEnglish) descriptionEn else descriptionBn
}

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val productTitleEn: String,
    val productTitleBn: String,
    val price: Double,
    val imageUrlMarkdown: String,
    val quantity: Int,
    val sellerId: Long,
    val sellerBusinessName: String
) {
    fun getProductTitle(isEnglish: Boolean): String = if (isEnglish) productTitleEn else productTitleBn
}

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val buyerName: String,
    val buyerPhone: String,
    val buyerAddress: String,
    val totalPrice: Double,
    val paymentMethod: String, // "Cash on Delivery" or "Online Payment"
    val paymentStatus: String, // "Paid" or "Pending"
    val orderStatus: String, // "Pending", "Processing", "Shipped", "Delivered", "Disputed"
    val trackingNumber: String,
    val orderDate: String,
    val disputeMessage: String = ""
)

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val productId: Long,
    val productTitleEn: String,
    val productTitleBn: String,
    val quantity: Int,
    val price: Double,
    val sellerId: Long,
    val sellerBusinessName: String
) {
    fun getProductTitle(isEnglish: Boolean): String = if (isEnglish) productTitleEn else productTitleBn
}

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val productId: Long
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
