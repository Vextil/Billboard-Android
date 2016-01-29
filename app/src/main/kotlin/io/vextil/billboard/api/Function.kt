package io.vextil.billboard.api

class Function {

    var message: String = ""
    var poster: Poster = Poster()
    var function: Function = Function()

    class Function {
        var name: String = ""
        var language: String = ""
        var DDD: Boolean = false
        var poster: String = ""
        var rating: Int = 0
        var premiere: String = ""
        var age_restriction: String = ""
        var genre: String = ""
        var duration: String = ""
        var description: String = ""
        var theatres: Array<Theatres> = emptyArray()

        class Theatres {
            var id: String = ""
            var name: String = ""
            var screenings: Array<Screenings> = emptyArray()

            class Screenings {
                var day: String = ""
                var hours: Array<String> = emptyArray()
            }
        }
    }
}
