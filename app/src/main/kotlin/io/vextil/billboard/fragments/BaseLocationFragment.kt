package io.vextil.billboard.fragments

import android.content.Intent
import android.view.View

import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter
import io.vextil.billboard.R
import io.vextil.billboard.adapters.FunctionsAdapter
import com.siercuit.cartelera.utilities.ColumnCalculator
import io.vextil.billboard.activities.FunctionActivity
import io.vextil.billboard.api.Functions
import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.adaptable_list.view.*
import kotlinx.android.synthetic.main.functions_grid_item.view.*

abstract class BaseLocationFragment : RetrofitFragment() {

    override fun onCreateSuccessView(data: Any): View {
        val view = inflate(R.layout.adaptable_list)
        val adapter = FunctionsAdapter(activity, data as Functions)
        adapter.numColumns = ColumnCalculator.get(activity)!!
        adapter.setOnGridClickListener { view, position, itemId ->
            val intent = Intent(context, FunctionActivity::class.java)
            intent.putExtra("id", position.toString())
            intent.putExtra("name", view.name.text.toString())
            context.startActivity(intent)
        }

        val animationAdapter = SwingLeftInAnimationAdapter(adapter)
        animationAdapter.setAbsListView(view.listView)
        view.listView.adapter = animationAdapter
        return view
    }

}
