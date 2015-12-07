package io.vextil.billboard.services

class PosterService {
    var url: String = ""
    var sizes: Sizes = Sizes()

    class Sizes {
        var small: String = ""
        var medium: String = ""
        var big: String = ""
    }

    fun getSmall(poster: String): String {
        return url + '/' + sizes.small + '/' + poster
    }

    fun getMedium(poster: String): String {
        return url + '/' + sizes.medium + '/' + poster
    }

    fun getBig(poster: String): String {
        return url + '/' + sizes.big + '/' + poster
    }
}
