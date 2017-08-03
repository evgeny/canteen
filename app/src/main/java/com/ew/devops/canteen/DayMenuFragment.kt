package com.ew.devops.canteen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.fragment_day_menu.*
import javax.inject.Inject
import android.support.v4.app.ActivityOptionsCompat
import com.ew.devops.canteen.utils.UiUtils
import com.google.firebase.database.FirebaseDatabase


class DayMenuFragment : Fragment() {

    @Inject lateinit var presenter: MainActivityPresenter

    companion object Factory {
        fun newInstance(date: String): DayMenuFragment {
            val args: Bundle = Bundle(1)
            args.putString("date", date)

            val fragment = DayMenuFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CanteenApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_day_menu, container, false)

        val date: String = arguments.getString("date")

        presenter.getMenu(activity.getPreferences(Context.MODE_PRIVATE), date).subscribe({ response ->
            Log.d("TAG", "menu response=" + response)
            empty_view.visibility = View.GONE
            parseResponse(response.Content, day_menu_content, inflater)
        }, { error -> Log.e("TAG", "", error) })

        return view
    }

    fun parseResponse(content: ContentMenu, container: ViewGroup?, inflater: LayoutInflater?) {
//        val dbRef = FirebaseDatabase.getInstance().reference
        val cats = content.Categories ?: return

        cats.forEach {
            val menuItem = inflater?.inflate(R.layout.card_day_menu_item, container, false) as CardView

            val categoryView = menuItem.findViewById(R.id.day_menu_category) as TextView
            categoryView.text = it.Name

            // set price and description
            val productView = menuItem.findViewById(R.id.day_menu_product) as TextView
            val priceView = menuItem.findViewById(R.id.day_menu_price) as TextView
//            if (it.Products[0].Name.contains("€")) {
//                priceView.text = it.Products[0].Name
                productView.text = it.Products[0].Name
//            } else {
//                priceView.text = it.Products[1].Name
//                productView.text = it.Products[0].Name
//            }

            // set header icon
            val imageView = menuItem.findViewById(R.id.image) as ImageView
            val header = menuItem.findViewById(R.id.background)
            imageView.setImageResource(UiUtils.getCategoryDrawable(it.Id))
            header.setBackgroundColor(ContextCompat.getColor(activity, UiUtils.getCategoryColor(it.Id)))

            // set comment count
            //TODO
            // add card to fragment layout
            container?.addView(menuItem)

            menuItem.setOnClickListener { view ->
                presenter.category = it
                val dishActivity = Intent(activity, CategoryActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "test")
                startActivity(dishActivity, options.toBundle())
            }
        }
    }
}
