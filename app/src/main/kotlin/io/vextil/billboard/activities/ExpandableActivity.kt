package io.vextil.billboard.activities

import android.os.Bundle

import com.klinker.android.sliding.SlidingActivity
import com.siercuit.cartelera.R

class ExpandableActivity : SlidingActivity() {

    override fun init(savedInstanceState: Bundle?) {
        setContent(R.layout.funcion)

        title = intent.getStringExtra("name")
    }

}
