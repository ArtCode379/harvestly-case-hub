package harvestly.accessories.harvestlycasehub.data.repository

import harvestly.accessories.harvestlycasehub.data.model.Product
import harvestly.accessories.harvestlycasehub.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products = listOf(
        Product(
            1,
            "Forest Shield MagSafe Case",
            "A slim shock-absorbing case with a soft-touch recycled shell, raised camera rim and reliable magnetic alignment for everyday charging.",
            ProductCategory.CASES,
            29.00,
            "https://images.unsplash.com/photo-1603313011101-320f26a4f6f6?w=1200"
        ),
        Product(
            2, "Harvest Gold Clear Case", "Clear protection with reinforced corners and an anti-yellowing finish.",
            ProductCategory.CASES, 24.00, "https://images.unsplash.com/photo-1574944985070-8f3ebc6b79d2?w=1200"
        ),
        Product(
            3, "Braided USB-C Cable", "A durable two-metre fast-charge cable with reinforced connectors.",
            ProductCategory.CHARGING, 16.00, "https://images.unsplash.com/photo-1615526675159-e248c3021d3f?w=1200"
        ),
        Product(4, "Pocket Power Bank", "Compact 10,000 mAh backup power with USB-C delivery.", ProductCategory.CHARGING, 38.00, "https://images.unsplash.com/photo-1609592424824-8a1f9b5e0e4f?w=1200"),
        Product(
            5, "Everyday Wireless Earbuds", "Balanced sound, clear calls and up to 24 hours of listening.",
            ProductCategory.AUDIO, 49.00, "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=1200"
        ),
        Product(
            6, "Studio Mini Speaker", "Warm sound, splash resistance and a tactile carry loop.",
            ProductCategory.AUDIO, 45.00, "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=1200"
        ),
        Product(
            7, "Tempered Glass Duo", "Two clear protectors with an easy alignment frame.",
            ProductCategory.PROTECTION, 18.00, "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=1200"
        ),
        Product(
            8, "Camera Lens Guard", "Optical-glass lens covers that guard against scratches.",
            ProductCategory.PROTECTION, 14.00, "https://images.unsplash.com/photo-1592899677977-9c10ca588bbd?w=1200"
        ),
        Product(
            9, "Adjustable Desk Stand", "A fold-flat aluminium stand with an adjustable viewing angle.",
            ProductCategory.ESSENTIALS, 27.00, "https://images.unsplash.com/photo-1586953208448-b95a79798f07?w=1200"
        ),
        Product(
            10, "Travel Tech Organiser", "A compact organiser for cables, adapters and earbuds.",
            ProductCategory.ESSENTIALS, 32.00, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=1200"
        ),
        Product(11, "Magnetic Car Mount", "A low-profile mount for safer navigation.", ProductCategory.ESSENTIALS, 22.00, "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=1200"),
        Product(
            12, "Dual Port Fast Charger", "A compact 40W charger that powers two devices at once.",
            ProductCategory.CHARGING, 31.00, "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=1200"
        )
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)
}
