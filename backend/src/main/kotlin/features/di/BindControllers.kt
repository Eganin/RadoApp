package features.di

import features.login.LoginController
import features.register.RegisterController
import features.request.*
import features.resources.ResourcesController
import features.users.UserController
import features.vehicles.VehicleController
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
            rejectRequestService = instance()
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