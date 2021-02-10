import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.annotation.Nullable
import com.kasa.zulakasasimulator.R


class AddPhotoBottomDialogFragment : BottomSheetDialogFragment() {

    fun newInstance(): AddPhotoBottomDialogFragment {
        return AddPhotoBottomDialogFragment()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.bottom_sheet, container, false)

    }

}