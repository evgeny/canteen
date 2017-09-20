package com.ew.devops.canteen.dish


import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ew.devops.canteen.R
import kotlinx.android.synthetic.main.fragment_rating.*


/**
 * A simple [Fragment] subclass.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingFragment : DialogFragment() {

    interface ReviewInterface {
        fun postReview(rating: Float = 0f, comment: String = "", dishId: Int)
    }

    private var dishId: Int = 0

    private lateinit var listener: ReviewInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            dishId = arguments.getInt(ARG_DISH_ID)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = activity as ReviewInterface
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity.toString() + " must implement DialogInterface.OnClickListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.fragment_rating, null))
        builder.setMessage("Rating")
//                .setPositiveButton("Post", { dialog, which -> Log.d("TAG", "click on post") })
                .setPositiveButton("Post", { dialog, which -> listener.postReview(rating.rating, comment.text.toString(), 1) })
                .setNegativeButton("Dismiss", { dialog, which ->  dialog.dismiss()})
        return builder.create()
    }

    companion object {
        private val ARG_DISH_ID = "dish_id"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param dishId dish id
         * @return A new instance of fragment RatingFragment.
         */
        fun newInstance(dishId: Int): RatingFragment {
            val fragment = RatingFragment()
            val args = Bundle()
            args.putInt(ARG_DISH_ID, dishId)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
