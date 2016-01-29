package io.vextil.billboard.api

class Home {
    var message: String = ""
    var poster: Poster = Poster()
    var categories: Array<Categories> = emptyArray()

    class Categories {
        var name: String = ""
        var icon: String = ""
        var items: Array<Items> = emptyArray()

        class Items {
            var name: String = ""
            var poster: String = ""
            var id: Int = 0
            var language: String = ""
            var DDD: Boolean = false
        }
    }
}

