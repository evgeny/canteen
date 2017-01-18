package com.ew.devops.canteen

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.fragment_day_menu.*


class DayMenuFragment : Fragment() {
    companion object Factory {
        fun newInstance(date: String): DayMenuFragment {
            val args: Bundle = Bundle(1)
            args.putString("date", date)

            val fragment = DayMenuFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_day_menu, container, false)

        val presenter = MainActivityPresenter()

        val date: String = arguments.getString("date")

        presenter.getMenu(activity.getPreferences(Context.MODE_PRIVATE), date).subscribe({ response ->
            Log.d("TAG", "menu response=" + response)
            parseResponse(response.Content, day_menu_content, inflater)
        }, { error -> Log.e("TAG", "", error) })

        return view
    }

    fun parseResponse(content: ContentMenu, container: ViewGroup?, inflater: LayoutInflater?) {
        content.Categories.forEach {
            val menuItem = inflater?.inflate(R.layout.card_day_menu_item, container, false) as CardView
            val categoryView = menuItem.findViewById(R.id.day_menu_category) as TextView
            categoryView.text = it.Name

            val productView = menuItem.findViewById(R.id.day_menu_product) as TextView
            productView.text = it.Products[1].Name
//            day_menu_product.text = it.Products[1].Name

            val priceView = menuItem.findViewById(R.id.day_menu_price) as TextView
            priceView.text = it.Products[0].Name
//            day_menu_price.text = it.Products[0].Name
            container?.addView(menuItem)
        }
    }
}
