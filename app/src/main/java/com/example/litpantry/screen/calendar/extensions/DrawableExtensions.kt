
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import com.example.litpantry.R


fun Context.getDot(): Drawable {
    val drawable = ContextCompat.getDrawable(this, R.drawable.dot)
    //Add padding to too large icon
    return InsetDrawable(drawable, 24, 14, 24, 4)
}