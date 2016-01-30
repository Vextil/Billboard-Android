package io.vextil.billboard.api

class Locations {

    var message: String = ""
    var poster: Poster = Poster()
    var locations: Array<Locations> = emptyArray()

    class Locations {
        var id: Int = 0
        var name: String = ""
        var functions: Array<Functions.Functions> = emptyArray()
    }
}