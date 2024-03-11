package com.rlc.webtoon.dto.enumerated

enum class ProductType(val price: Int, val productName: String) {
    LOWEST(100, "100잎"),
    MIDDLE(300, "300잎"),
    HIGHEST(500, "500잎")
}
