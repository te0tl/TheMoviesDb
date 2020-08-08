
import android.R
import androidx.appcompat.app.AlertDialog
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.robolectric.shadows.ShadowDialog

class KAlertDialog : KBaseView<KAlertDialog>({ isRoot() }) {

    init {
        inRoot { isDialog() }
    }

    val positiveButton = KButton { withId(R.id.button1) }
            .also { it.inRoot { isDialog() } }

    val negativeButton = KButton { withId(R.id.button2) }
            .also { it.inRoot { isDialog() } }

    val neutralButton = KButton { withId(R.id.button3) }
            .also { it.inRoot { isDialog() } }

/*
    val title = KTextView { withId(R.id.alertTitle) }
            .also { it.inRoot { isDialog() } }
*/

    val message = KTextView { withId(R.id.message) }
            .also { it.inRoot { isDialog() } }

    val icon = KImageView { withId(R.id.icon) }
            .also { it.inRoot { isDialog() } }

    /**
     * Hack to interact with dialogs in Robolectric, do not use this in instrumented test
     */
    fun clickPositive() {
        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick()

    }

    /**
     * Hack to interact with dialogs in Robolectric, do not use this in instrumented test
     */
    fun clickNegative() {
        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).performClick()

    }
}