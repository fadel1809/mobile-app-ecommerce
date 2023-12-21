package id.ac.umn.mobileapp.order

data class Order (
    val imageResourceName: String,
    val tvNameOrder: String,
    val tvHarga: Long,
    val quantity:String,
    val selectedSize : String
)