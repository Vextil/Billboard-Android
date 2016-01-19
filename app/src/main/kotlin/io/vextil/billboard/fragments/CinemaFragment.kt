package io.vextil.billboard.fragments

import com.siercuit.cartelera.App
import io.vextil.billboard.api.Functions
import rx.Observable

class CinemaFragment : BaseMultiLocationFragment() {

    override fun onGetObservable(): Observable<Functions> {
        return App.API().getCinema()
    }

}
