package dao.di

import dao.images.ImagesDaoFacade
import dao.images.ImagesDaoImpl
import dao.register.driver.DriverDaoFacade
import dao.register.driver.DriverDaoImpl
import dao.register.mechanic.MechanicDaoFacade
import dao.register.mechanic.MechanicDaoImpl
import dao.register.observer.ObserverDaoFacade
import dao.register.observer.ObserverDaoImpl
import dao.request.RequestDaoFacade
import dao.request.RequestDaoImpl
import dao.vehicles.VehicleDaoFacade
import dao.vehicles.VehicleDaoImpl
import dao.videos.VideosDaoFacade
import dao.videos.VideosDaoImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun DI.MainBuilder.bindRepositories() {
    bind<ImagesDaoFacade>() with singleton { ImagesDaoImpl() }

    bind<RequestDaoFacade>() with singleton { RequestDaoImpl() }

    bind<VehicleDaoFacade>() with singleton { VehicleDaoImpl() }

    bind<DriverDaoFacade>() with singleton { DriverDaoImpl() }

    bind<MechanicDaoFacade>() with singleton { MechanicDaoImpl() }

    bind<ObserverDaoFacade>() with singleton { ObserverDaoImpl() }

    bind<VideosDaoFacade>() with singleton { VideosDaoImpl() }
}