package io.vextil.billboard.activities

import android.os.Bundle

import com.klinker.android.sliding.SlidingActivity
import io.vextil.billboard.R
import io.vextil.billboard.fragments.FunctionFragment

class FunctionActivity : SlidingActivity() {

    override fun init(savedInstanceState: Bundle?) {
        setContent(R.layout.fragment)
        val fragment = FunctionFragment()
        fragment.setId(intent.getStringExtra("id"))
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
        title = intent.getStringExtra("name")
    }

}
