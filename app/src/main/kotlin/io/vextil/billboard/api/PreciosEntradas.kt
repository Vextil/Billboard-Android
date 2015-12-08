package io.vextil.billboard.api

class PreciosEntradas {
    var precios: Array<Precios> = emptyArray()

    class Precios {
        var nombre: String = ""
        var opciones: Array<Opciones> = emptyArray()

        class Opciones {
            var nombre: String = ""
            var horarios: Array<Horarios> = emptyArray()

            class Horarios {
                var nombre: String = ""
                var nombre_sub: String = ""
                var precio: String = ""
            }
        }
    }
}
