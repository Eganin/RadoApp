import database.databaseModule
import json.serializationModule
import ktor.ktorModule
import org.kodein.di.DI
import other.otherModule
import settings.settingsModule

val coreModule = DI.Module("coreModule") {
    importAll(
        otherModule,
        ktorModule,
        serializationModule,
        settingsModule,
        databaseModule
    )
}