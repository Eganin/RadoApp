package org.company.rado.dao.di

import org.company.rado.dao.images.ImagesDaoFacade
import org.company.rado.dao.images.ImagesDaoImpl
import org.company.rado.dao.register.driver.DriverDaoFacade
import org.company.rado.dao.register.driver.DriverDaoImpl
import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.register.mechanic.MechanicDaoImpl
import org.company.rado.dao.register.observer.ObserverDaoFacade
import org.company.rado.dao.register.observer.ObserverDaoImpl
import org.company.rado.dao.request.RequestDaoFacade
import org.company.rado.dao.request.RequestDaoImpl
import org.company.rado.dao.vehicles.VehicleDaoFacade
import org.company.rado.dao.vehicles.VehicleDaoImpl
import org.company.rado.dao.videos.VideosDaoFacade
import org.company.rado.dao.videos.VideosDaoImpl
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