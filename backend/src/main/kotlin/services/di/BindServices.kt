package services.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import services.ResourceService
import services.UsersService
import services.VehicleService
import services.request.*

fun DI.MainBuilder.bindServices() {
    bind<ResourceService>() with singleton {
        ResourceService(
            imageRepository = instance(),
            videoRepository = instance()
        )
    }

    bind<RequestService>() with singleton {
        RequestService(
            requestRepository = instance(),
            vehicleRepository = instance(),
            driverRepository = instance(),
            mechanicRepository = instance(),
            imageRepository = instance()
        )
    }

    bind<UnconfirmedRequestService>() with singleton {
        UnconfirmedRequestService(
            requestRepository = instance(),
            vehicleRepository = instance(),
            driverRepository = instance(),
            imageRepository = instance()
        )
    }

    bind<ActiveRequestService>() with singleton {
        ActiveRequestService(
            requestRepository = instance(),
            vehicleRepository = instance(),
            mechanicRepository = instance()
        )
    }

    bind<ArchiveRequestService>() with singleton {
        ArchiveRequestService(
            requestRepository = instance(),
            mechanicRepository = instance(),
            vehicleRepository = instance()
        )
    }

    bind<RejectRequestService>() with singleton {
        RejectRequestService(
            requestRepository = instance(),
            vehicleRepository = instance(),
            mechanicRepository = instance(),
            imageRepository = instance()
        )
    }

    bind<UsersService>() with singleton {
        UsersService(
            driverRepository = instance(),
            mechanicRepository = instance(),
            observerRepository = instance()
        )
    }

    bind<VehicleService>() with singleton {
        VehicleService(
            vehicleRepository = instance()
        )
    }

    bind<DeleteRequestService>() with singleton {
        DeleteRequestService(
            requestRepository = instance()
        )
    }
}