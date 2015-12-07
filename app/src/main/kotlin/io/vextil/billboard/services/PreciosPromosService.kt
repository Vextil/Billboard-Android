package io.vextil.billboard.services

class PreciosPromosService {
    var categorias: Array<Categorias> = emptyArray()

    class Categorias {
        var nombre: String = ""
        var promos: Array<Promos> = emptyArray()

        class Promos {
            var nombre: String = ""
            var descripcion: String = ""
        }
    }
}
