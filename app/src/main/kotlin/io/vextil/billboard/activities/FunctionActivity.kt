package io.vextil.billboard.activities

import android.os.Bundle

import com.klinker.android.sliding.SlidingActivity
import io.vextil.billboard.R
import io.vextil.billboard.fragments.FunctionFragment

class FunctionActivity : SlidingActivity() {

    override fun init(savedInstanceState: Bundle?) {
        setContent(R.layout.fragment)
        val fragment = FunctionFragment()
        var args = Bundle()
        args.putString("id", intent.getStringExtra("id"))
        fragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
        title = intent.getStringExtra("name")
    }

}
