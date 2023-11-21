package org.company.rado.services.di

import org.company.rado.services.request.ActiveRequestService
import org.company.rado.services.request.ArchiveRequestService
import org.company.rado.services.request.DeleteRequestService
import org.company.rado.services.request.RejectRequestService
import org.company.rado.services.request.RequestService
import org.company.rado.services.request.UnconfirmedRequestService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.company.rado.services.ResourceService
import org.company.rado.services.UsersService
import org.company.rado.services.VehicleService

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
            imageRepository = instance(),
            videoRepository = instance()
        )
    }

    bind<UnconfirmedRequestService>() with singleton {
        UnconfirmedRequestService(
            requestRepository = instance(),
            vehicleRepository = instance(),
            driverRepository = instance(),
            imageRepository = instance(),
            videoRepository = instance()
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