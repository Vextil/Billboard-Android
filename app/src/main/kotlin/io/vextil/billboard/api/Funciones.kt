package io.vextil.billboard.api

import com.google.gson.annotations.SerializedName

class Funciones {
    var poster_url: String = ""
    var poster_url_grande: String = ""
    var poster_extension: String = ""
    var funciones: Array<Funciones> = emptyArray()
    var mensaje: String = ""

    class Funciones {
        var id: Int = 0
        var nombre: String = ""
        var lenguaje: String = ""
        @SerializedName("3D") var threeD: Boolean = false
        var estreno: String = ""
        var rating: Int = 0
        var poster: String = ""
    }
}
