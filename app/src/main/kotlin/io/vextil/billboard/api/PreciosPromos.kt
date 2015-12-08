package io.vextil.billboard.api

class PreciosPromos {
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
