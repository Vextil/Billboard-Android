package io.vextil.billboard.api

class Functions {
    var message: String = ""
    var poster: Poster = Poster()
    var function: Function.Function = Function.Function()
    var functions: Array<Functions> = emptyArray()

    class Functions {
        var id: String = ""
        var name: String = ""
        var language: String = ""
        var DDD: Boolean = false
        var poster: String = ""
        var rating: Int = 0
        var premiere: String = ""
    }
}
