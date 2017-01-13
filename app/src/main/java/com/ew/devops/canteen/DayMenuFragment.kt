package com.ew.devops.canteen

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.card_day_menu_item.*
import kotlinx.android.synthetic.main.fragment_day_menu.*


class DayMenuFragment : Fragment() {
    companion object Factory {
        fun newInstance(date: String): DayMenuFragment {
            val args: Bundle = Bundle(1)
            args.putString("date", date)

            val fragment: DayMenuFragment = DayMenuFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val date: String = arguments.getString("date")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_day_menu, container, false)

        val presenter = MainActivityPresenter()

        presenter.getMenu(activity.getPreferences(Context.MODE_PRIVATE), date = "2017-01-13").subscribe({ response ->
            Log.d("TAG", "menu response=" + response)
            day_menu_category.text = response.Content.Categories[0].Name
            day_menu_product.text = response.Content.Categories[0].Products[0].Name
        }, { error -> Log.e("TAG", "", error) })

        return view
    }
}
