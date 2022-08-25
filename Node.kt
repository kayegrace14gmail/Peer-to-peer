data class Node (
    val ipAddress : String = "",
    val publicAddress : String = "",
    val id : Int = 0, //Between 1 and max-ring-capacity (300)
    val pid : String = "",
    val rrt : Float = 0.0F,
    val successor_id : Int = 0,
    val successor_address : String = "",
    val successor_port : Int = 0,
    val predecessor_id : Int = 0,
    val predecessor_address : String = "",
    val predecessor_port : Int = 0
)