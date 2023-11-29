package org.company.rado.features.di

import org.company.rado.features.login.LoginController
import org.company.rado.features.register.RegisterController
import org.company.rado.features.request.ActiveRequestController
import org.company.rado.features.request.ArchiveRequestController
import org.company.rado.features.request.DeleteRequestController
import org.company.rado.features.request.RejectRequestController
import org.company.rado.features.request.RequestController
import org.company.rado.features.request.UnconfirmedRequestController
import org.company.rado.features.resources.ResourcesController
import org.company.rado.features.users.UserController
import org.company.rado.features.vehicles.VehicleController
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun DI.MainBuilder.bindControllers() {
    bind<RegisterController>() with singleton {
        RegisterController(usersService = instance())
    }

    bind<LoginController>() with singleton {
        LoginController(
            usersService = instance()
        )
    }

    bind<UserController>() with singleton {
        UserController(
            usersService = instance()
        )
    }

    bind<RequestController>() with singleton {
        RequestController(
            requestService = instance()
        )
    }

    bind<UnconfirmedRequestController>() with singleton {
        UnconfirmedRequestController(
            unconfirmedRequestService = instance()
        )
    }

    bind<ActiveRequestController>() with singleton {
        ActiveRequestController(
            activeRequestService = instance(),
            requestService = instance()
        )
    }

    bind<ArchiveRequestController>() with singleton {
        ArchiveRequestController(
            archiveRequestService = instance(),
            requestService = instance()
        )
    }

    bind<RejectRequestController>() with singleton {
        RejectRequestController(
            rejectRequestService = instance(),
            requestService = instance()
        )
    }

    bind<VehicleController>() with singleton {
        VehicleController(
            vehicleService = instance()
        )
    }

    bind<DeleteRequestController>() with singleton {
        DeleteRequestController(
            deleteRequestService = instance()
        )
    }

    bind<ResourcesController>() with singleton {
        ResourcesController(
            resourceService = instance()
        )
    }
}