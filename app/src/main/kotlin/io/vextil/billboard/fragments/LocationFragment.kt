package io.vextil.billboard.fragments

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View

import io.vextil.billboard.R
import io.vextil.billboard.adapters.FunctionsAdapter
import io.vextil.billboard.activities.FunctionActivity
import io.vextil.billboard.api.Functions
import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.recycler.view.*

abstract class LocationFragment : RetrofitFragment() {

    override fun onCreateSuccessView(data: Any): View {
        val view = inflate(R.layout.recycler)
        view.recycler.setHasFixedSize(true)
        view.recycler.layoutManager = GridLayoutManager(context, 1)
        view.recycler.adapter = FunctionsAdapter(context, data as Functions) { name, id ->
            val intent = Intent(context, FunctionActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            context.startActivity(intent)
        }
        return view
    }

}
