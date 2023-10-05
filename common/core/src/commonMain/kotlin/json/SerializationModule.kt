package json

import org.kodein.di.DI
import kotlinx.serialization.json.Json
import org.kodein.di.bind
import org.kodein.di.singleton

val serializationModule = DI.Module(name="serializationModule"){
    bind<Json>() with singleton {
        Json {
            isLenient=true
            ignoreUnknownKeys=true
        }
    }
}