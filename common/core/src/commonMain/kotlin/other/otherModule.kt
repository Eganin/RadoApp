package other

import io.ktor.http.HttpStatusCode
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal val otherModule = DI.Module(name="other_module"){
    bind<Mapper<HttpStatusCode,WrapperForResponse>>() with singleton {
        HttpStatusCodeMapper()
    }
}