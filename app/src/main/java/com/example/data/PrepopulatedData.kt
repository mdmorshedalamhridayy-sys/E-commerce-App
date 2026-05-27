package com.example.data

object PrepopulatedData {

    val sellers = listOf(
        SellerEntity(1, "Hriday Store", "Hriday Retailers", "hriday@store.com", "01712345678", 4.9f, 48200.0, 32, 45000.0, 3200.0, "2026-01-01"),
        SellerEntity(2, "Anika Fashion", "Anika's Jamdani House", "anika@jamdani.com", "01812345678", 4.8f, 32500.0, 18, 30000.0, 2500.0, "2026-01-15"),
        SellerEntity(3, "Karujog Handicrafts", "Karujog Ltd", "info@karujog.com", "01912345678", 4.7f, 22000.0, 15, 20000.0, 2000.0, "2026-02-01"),
        SellerEntity(4, "Dhaka Electro", "Dhaka Electronics Hub", "sales@dhakaelectro.com", "01512345678", 4.5f, 65000.0, 42, 60000.0, 5000.0, "2026-02-10"),
        SellerEntity(5, "Rangpur Shatranji", "Shatranji Weaver Association", "rangpur@shatranji.com", "01612345678", 4.9f, 18500.0, 12, 17000.0, 1500.0, "2026-02-20"),
        SellerEntity(6, "Jessore Nakshi Kantha", "Nakshi Kantha Karu Shilpa", "jessore@nakshi.com", "01787654321", 4.8f, 29000.0, 21, 27000.0, 2000.0, "2026-03-01"),
        SellerEntity(7, "Comilla Clay Art", "Comilla Pottery Association", "comilla@pottery.com", "01887654321", 4.6f, 12500.0, 9, 11000.0, 1500.0, "2026-03-05"),
        SellerEntity(8, "Rajshahi Silk Craft", "Silk Heritage Rajshahi", "rajshahi@silk.com", "01987654321", 4.9f, 41000.0, 25, 38000.0, 3000.0, "2026-03-12"),
        SellerEntity(9, "Bengal Gadgets", "Bengal Gadget World", "info@bengalgadgets.com", "01587654321", 4.4f, 54000.0, 31, 50000.0, 4000.0, "2026-03-18"),
        SellerEntity(10, "Eco Wood & Bamboo", "Eco Craft BD", "eco@craftbd.com", "01687654321", 4.7f, 15000.0, 11, 14000.0, 1000.0, "2026-03-22"),
        SellerEntity(11, "Tangail Weaves", "Tangail Tant Ghor", "tangail@tant.com", "01723456789", 4.8f, 2600.0, 16, 24000.0, 2000.0, "2026-03-25"),
        SellerEntity(12, "Brass Heritage", "Dhamrai Metal Crafts", "brass@dhamrai.com", "01823456789", 4.8f, 31000.0, 14, 28000.0, 3000.0, "2026-03-29"),
        SellerEntity(13, "Jute Wonders", "Jute Craft Bangladesh", "jute@wonders.com", "01923456789", 4.7f, 19500.0, 13, 18000.0, 1500.0, "2026-04-02"),
        SellerEntity(14, "Tech Solutions BD", "Tech Solutions Dhaka", "admin@techsolbd.com", "01523456789", 4.3f, 78000.0, 48, 72000.0, 6000.0, "2026-04-05"),
        SellerEntity(15, "Sheetal Pati weavers", "Sylhet Traditional Pati Ry", "sylhet@pati.com", "01623456789", 4.6f, 10400.0, 8, 9000.0, 1400.0, "2026-04-09"),
        SellerEntity(16, "Traditional Leather", "Hazaribagh Leather Craft", "leather@craft.com", "01734567890", 4.5f, 22400.0, 15, 20000.0, 2400.0, "2026-04-12"),
        SellerEntity(17, "Smart Living", "Smart Living Smart Home", "living@smart.com", "01834567890", 4.4f, 37000.0, 22, 34000.0, 3000.0, "2026-04-15"),
        SellerEntity(18, "Bengal Wood Art", "Bengal Woodcrafts", "wood@bengalart.com", "01934567890", 4.7f, 21500.0, 14, 19500.0, 2000.0, "2026-04-18"),
        SellerEntity(19, "Smart Accessories", "Accessories BD Retail", "sales@accbd.com", "01534567890", 4.6f, 49000.0, 35, 45000.0, 4000.0, "2026-04-20"),
        SellerEntity(20, "Local Pride", "Handmade & Small Batch BD", "local@pride.com", "01634567890", 4.8f, 13200.0, 10, 12000.0, 1200.0, "2026-04-22")
    )

    val products: List<ProductEntity>
        get() {
            val list = mutableListOf<ProductEntity>()

            // 1. Fashion (35 items)
            val fashionItems = listOf(
                Triple("Premium Dhakai Jamdani Saree", "প্রিমিয়াম ঢাকাই জামদানি শাড়ি", listOf(8500.0, 12000.0, 15000.0, 18000.0, 22000.0)),
                Triple("Traditional Tangail Cotton Saree", "ঐতিহ্যবাহী টাঙ্গাইল সুতি শাড়ি", listOf(1500.0, 1800.0, 2200.0, 2500.0, 3000.0)),
                Triple("Rajshahi Pure Silk Saree", "রাজশাহী খাঁটি সিল্ক শাড়ি", listOf(4500.0, 5500.0, 6500.0, 8000.0)),
                Triple("Designer Semi-Fit Cotton Panjabi", "ডিজাইনার সেমি-ফিট সুতি পাঞ্জাবি", listOf(1200.0, 1600.0, 2000.0, 2500.0, 3500.0)),
                Triple("Exclusive Kabli Set for Men", "পুরুষদের এক্সক্লুসিভ কাবলি সেট", listOf(2200.0, 2800.0, 3500.0, 4200.0)),
                Triple("Traditional Handloom Lungi", "ঐতিহ্যবাহী তাঁতের লুঙ্গি", listOf(350.0, 450.0, 600.0, 800.0)),
                Triple("Embroidered Cotton Kurti", "এমব্রয়ডারি করা সুতি কুর্তি", listOf(800.0, 1200.0, 1500.0, 1800.0)),
                Triple("Boutique Salwar Kameez Set", "বুটিক সালোয়ার কামিজ সেট", listOf(1800.0, 2200.0, 2800.0, 3500.0)),
                Triple("Premium Leather Sandals", "প্রিমিয়াম চামড়ার স্যান্ডেল", listOf(1500.0, 2000.0, 2500.0)),
                Triple("Handcrafted Jute Sandal", "হস্তনির্মিত পাটের স্যান্ডেল", listOf(450.0, 600.0, 750.0))
            )

            var idCounter = 1L
            for (i in 0 until 35) {
                val base = fashionItems[i % fashionItems.size]
                val seller = sellers[i % 5] // Distribute fashion among first 5 sellers
                val price = base.third[i % base.third.size]
                val variantNum = (i / fashionItems.size) + 1
                val suffixEn = if (variantNum > 1) " (Style $variantNum)" else ""
                val suffixBn = if (variantNum > 1) " (ডিজাইন $variantNum)" else ""

                list.add(
                    ProductEntity(
                        id = idCounter++,
                        titleEn = base.first + suffixEn,
                        titleBn = base.second + suffixBn,
                        descriptionEn = "Beautiful traditional handloom clothing representing the deep heritage and exquisite artistic craftsmanship of Bangladesh. Made from 100% premium materials, carefully selected for comfort, design durability, and active everyday usage.",
                        descriptionBn = "বাংলাদেশের গভীর ঐতিহ্য এবং চমৎকার শৈল্পিক কারুশিল্পের প্রতিনিধিত্বকারী ঐতিহ্যবাহী পোশাক। আরাম, দীর্ঘস্থায়ী স্থায়িত্ব এবং প্রতিদিনের ব্যবহারের জন্য অত্যন্ত যত্নসহকারে ১০০% প্রিমিয়াম সুতা থেকে তৈরি করা হয়েছে।",
                        price = price,
                        category = "Fashion",
                        stock = 10 + (i * 3) % 40,
                        rating = 4.0f + (i % 10) * 0.1f,
                        reviewCount = 5 + (i * 7) % 80,
                        popularityIndex = 100 - i,
                        isApproved = true,
                        sellerId = seller.id,
                        sellerBusinessName = seller.businessName,
                        imageUrlMarkdown = "fashion_${(i % 5) + 1}",
                        promoApplied = if (i % 3 == 0) "10% OFF" else ""
                    )
                )
            }

            // 2. Handicrafts (35 items)
            val handicraftItems = listOf(
                Triple("Handmade Jessore Nakshi Kantha", "হস্তনির্মিত যশোর নকশিকাঁথা", listOf(3500.0, 4500.0, 6000.0, 8000.0)),
                Triple("Traditional Rangpur Shatranji Mat", "ঐতিহ্যবাহী রংপুর শতরঞ্জি ম্যাট", listOf(800.0, 1200.0, 1600.0, 2200.0)),
                Triple("Clay Ceramic Pottery Vase", "মাটির সিরামিক শোপিস ফুলদানী", listOf(350.0, 500.0, 750.0, 1000.0)),
                Triple("Hand-woven Jute Handbag/Tote", "হাতে বোনা পাটের হ্যান্ডব্যাগ / টোট", listOf(400.0, 600.0, 850.0)),
                Triple("Traditional Sylhet Sheetol Pati", "ঐতিহ্যবাহী সিলেট শীতল পাটি", listOf(1500.0, 2500.0, 3500.0)),
                Triple("Bamboo Handcrafted Fruit Basket", "বাঁশের হস্তশিল্পের ফলের ঝুড়ি", listOf(250.0, 400.0, 600.0)),
                Triple("Dhamrai Casting Brass Statue", "ধামরাই মেটাল কাস্টিং পিতলের মূর্তি", listOf(2500.0, 4500.0, 7500.0)),
                Triple("Hand-painted Coconut Shell Lamp", "হাতে আঁকা নারকেলের মালার ল্যাম্প", listOf(650.0, 850.0, 1200.0)),
                Triple("Rickshaw Art Painted Wooden Tray", "রিকশাচিত্র আঁকা কাঠের ট্রে", listOf(800.0, 1100.0, 1500.0)),
                Triple("Handcrafted Monipuri Shawl", "মনিপুরী হস্তশিল্পের চাদর", listOf(1200.0, 1800.0, 2500.0))
            )

            for (i in 0 until 35) {
                val base = handicraftItems[i % handicraftItems.size]
                val seller = sellers[5 + (i % 8)] // Distribute among sellers 5 to 12
                val price = base.third[i % base.third.size]
                val variantNum = (i / handicraftItems.size) + 1
                val suffixEn = if (variantNum > 1) " (Aesthetic $variantNum)" else ""
                val suffixBn = if (variantNum > 1) " (ডিজাইন $variantNum)" else ""

                list.add(
                    ProductEntity(
                        id = idCounter++,
                        titleEn = base.first + suffixEn,
                        titleBn = base.second + suffixBn,
                        descriptionEn = "Handmade in rural villages of Bangladesh by passionate master artisans keeping age-old cultural legacies alive. Incorporates locally-sourced materials like organic bamboo, pure clay, premium jute, and brass with rich details and native designs.",
                        descriptionBn = "যুগের পর যুগ ধরে চলে আসা বাঙালি ঐতিহ্য বাঁচিয়ে রাখা দক্ষ কারিগরদের ভালোবাসা ও অক্লান্ত পরিশ্রমে তৈরি পণ্য। এতে অর্গানিক বাঁশ, খাঁটি কাদামাটি, প্রিমিয়াম পাট এবং পিতলের মতো শতভাগ দেশীয় প্রাকৃতিক উপকরণ ব্যবহার করা হয়েছে।",
                        price = price,
                        category = "Handicraft",
                        stock = 5 + (i * 2) % 25,
                        rating = 4.3f + (i % 8) * 0.1f,
                        reviewCount = 3 + (i * 5) % 50,
                        popularityIndex = 200 - i,
                        isApproved = i % 15 != 0, // Mock some unapproved products for admin testing!
                        sellerId = seller.id,
                        sellerBusinessName = seller.businessName,
                        imageUrlMarkdown = "handicraft_${(i % 5) + 1}",
                        promoApplied = if (i % 4 == 0) "Free Delivery" else ""
                    )
                )
            }

            // 3. Electronics (30 items)
            val electronicItems = listOf(
                Triple("Dual-Port USB Fast Wall Charger", "ডুয়াল পোর্ট ইউএসবি ফাস্ট চার্জার", listOf(450.0, 650.0, 850.0)),
                Triple("Portable Bluetooth Wireless Speaker", "বহনযোগ্য ব্লুটুথ ওয়্যারলেস স্পিকার", listOf(1200.0, 1800.0, 2500.0, 3500.0)),
                Triple("10000mAh Power Bank Li-Polymer", "১০০০০ মেগাঅ্যাম্পিয়ার পাওয়ার ব্যাংক", listOf(950.0, 1350.0, 1800.0)),
                Triple("Active Wireless Bluetooth Neckband", "অ্যাক্টিভ ওয়্যারলেস ব্লুটুথ নেকব্যান্ড", listOf(750.0, 1100.0, 1500.0)),
                Triple("Adjustable LED Desk Study Lamp", "অ্যাডজাস্টেবল রিডিং টেবিল এলইডি ল্যাম্প", listOf(550.0, 850.0, 1200.0)),
                Triple("True Wireless Stereo Earbuds (TWS)", "খাঁটি ওয়্যারলেস স্টিরিও ইয়ারবাডস", listOf(1200.0, 1950.0, 2800.0)),
                Triple("Desktop Multifunctional Phone Stand", "ডেস্কটপ মাল্টিফাংশনাল মোবাইল স্ট্যান্ড", listOf(250.0, 400.0, 600.0)),
                Triple("Smart Bluetooth Display Watch v2", "স্মার্ট ব্লুটুথ ডিসপ্লে ঘড়ি", listOf(1800.0, 2600.0, 3800.0, 5500.0)),
                Triple("Mini High-Speed Rechargeable Desk Fan", "মিনি হাই-স্পিড রিচার্জেবল টেবিল ফ্যান", listOf(850.0, 1250.0, 1750.0)),
                Triple("USB Selfie LED Ring Light Studio", "ইউএসবি সেলফি এলইডি রিং লাইট", listOf(650.0, 950.0, 1400.0))
            )

            for (i in 0 until 30) {
                val base = electronicItems[i % electronicItems.size]
                val seller = sellers[12 + (i % 8)] // Distribute among sellers 12 to 19
                val price = base.third[i % base.third.size]
                val variantNum = (i / electronicItems.size) + 1
                val suffixEn = if (variantNum > 1) " (Pro $variantNum)" else ""
                val suffixBn = if (variantNum > 1) " (প্রো $variantNum)" else ""

                list.add(
                    ProductEntity(
                        id = idCounter++,
                        titleEn = base.first + suffixEn,
                        titleBn = base.second + suffixBn,
                        descriptionEn = "High performance gadgets and electronics essential for modern life. Tested completely for electrical standards, high power efficiency, long-term battery performance, and smart automation capabilities.",
                        descriptionBn = "আজকের আধুনিক জীবনের জন্য অত্যন্ত প্রয়োজনীয় উচ্চ মানের ইলেকট্রনিক্স পণ্য। বৈদ্যুতিক নিরাপত্তা, স্থায়িত্ব এবং দীর্ঘ ব্যাটারি ব্যাকআপ নিশ্চিত করতে সম্পূর্ণভাবে পরীক্ষিত এবং স্মার্ট ফিচারে ভরপুর।",
                        price = price,
                        category = "Electronics",
                        stock = 15 + (i * 5) % 60,
                        rating = 4.1f + (i % 10) * 0.1f,
                        reviewCount = 8 + (i * 9) % 150,
                        popularityIndex = 300 - i,
                        isApproved = true,
                        sellerId = seller.id,
                        sellerBusinessName = seller.businessName,
                        imageUrlMarkdown = "electronics_${(i % 5) + 1}",
                        promoApplied = if (i % 5 == 0) "Top Choice" else ""
                    )
                )
            }

            return list
        }
}
