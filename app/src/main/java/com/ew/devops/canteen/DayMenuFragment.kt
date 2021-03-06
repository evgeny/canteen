package com.ew.devops.canteen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ew.devops.canteen.di.DayMenuPresenter
import com.ew.devops.canteen.network.Category
import com.ew.devops.canteen.utils.UiUtils
import kotlinx.android.synthetic.main.fragment_day_menu.*

/**
 * represent a ui tab with menu for one day defined by given date
 */
class DayMenuFragment : Fragment() {

    lateinit var presenter: DayMenuPresenter

    //    
    companion object Factory {
        fun newInstance(date: String): DayMenuFragment {
            val args = Bundle(1)
            args.putString("date", date)

            val fragment = DayMenuFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date: String = arguments!!.getString("date", "")

        presenter = DayMenuPresenter(date)
        val cats = presenter.menuObservable().Categories

        empty_view.visibility = View.GONE
        parseResponse(cats, day_menu_content)
    }

    private fun parseResponse(categories: List<Category>, container: ViewGroup?) {
        val inflater = LayoutInflater.from(context)
        categories?.forEach {
            val menuItem = inflater?.inflate(R.layout.card_day_menu_item, container, false) as CardView

            val categoryView = menuItem.findViewById<TextView>(R.id.day_menu_category)
            categoryView.text = it.Name

            // set price and description
            val productView = menuItem.findViewById<TextView>(R.id.day_menu_product)
//            val priceView = menuItem.findViewById(R.id.day_menu_price) as TextView
//            if (it.Products[0].Name.contains("€")) {
//                priceView.text = it.Products[0].Name
            productView.text = it.Products[0].Name
//            } else {
//                priceView.text = it.Products[1].Name
//                productView.text = it.Products[0].Name
//            }

            // set header icon
            val imageView = menuItem.findViewById<ImageView>(R.id.image)
            val header = menuItem.findViewById<View>(R.id.background)
            imageView.setImageResource(UiUtils.getCategoryDrawable(it.Id))
            header.setBackgroundColor(ContextCompat.getColor(activity!!, UiUtils.getCategoryColor(it.Id)))

            // set comment count
            //TODO
            // add card to fragment layout
            container?.addView(menuItem)

//            menuItem.setOnClickListener { view ->
//                presenter.category = it
//                val dishActivity = Intent(activity, CategoryActivity::class.java)
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imageView, "test")
//                startActivity(dishActivity, options.toBundle())
//            }
        }
    }
}
