package io.vextil.billboard.api

import com.google.gson.annotations.SerializedName

class Function {
    var poster_url: String = ""
    var poster_url_grande: String = ""
    var poster_extension: String = ""
    var funcion: Funcion = Funcion()

    class Funcion {
        var nombre: String = ""
        var lenguaje: String = ""
        @SerializedName("3D") var threeD: Boolean = false
        var poster: String = ""
        var rating: Int = 0
        var estreno: String = ""
        var clasificacion: String = ""
        var genero: String = ""
        var duracion: String = ""
        var descripcion: String = ""
        var salas: Array<Salas> = emptyArray()

        class Salas {
            var id: String = ""
            var nombre: String = ""
            var horarios: Array<Horarios> = emptyArray()

            class Horarios {
                var dia: String = ""
                var horas: Array<String> = emptyArray()
            }
        }
    }
}
