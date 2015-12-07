package io.vextil.billboard.services

class PreciosSnacksService {
    var imagen_url: String = ""
    var imagen_extension: String = ""
    var snacks: Array<Snacks> = emptyArray()

    class Snacks {
        var nombre: String = ""
        var precio: String = ""
        var contenido: String = ""
        var imagen: String = ""
    }
}
