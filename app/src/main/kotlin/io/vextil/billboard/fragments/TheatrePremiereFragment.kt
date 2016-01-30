package io.vextil.billboard.fragments

import io.vextil.billboard.App
import io.vextil.billboard.api.Functions
import rx.Observable

class TheatrePremiereFragment : LocationFragment() {

    override fun onGetObservable(): Observable<Functions> {
        return App.API().getTheatrePremiere()
    }

}