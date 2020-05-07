import kotlinx.serialization.Serializable

@Serializable
data class Post(val userId: Int,
                val id: Int,
                val title: String,
                val body: String) {

    fun isEmpty() : Boolean {
        return userId == 0 && id == 0
    }
}

fun emptyPost() : Post {
    return Post(0, 0, "","")
}