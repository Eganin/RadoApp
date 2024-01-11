package org.company.rado.services

import org.company.rado.dao.register.driver.DriverDaoFacade
import org.company.rado.dao.register.mechanic.MechanicDaoFacade
import org.company.rado.dao.register.observer.ObserverDaoFacade
import org.company.rado.models.users.LoginResponse
import org.company.rado.models.users.Position
import org.company.rado.models.users.UserDTO
import org.company.rado.models.users.UserIdResponse

class UsersService(
    private val driverRepository: DriverDaoFacade,
    private val mechanicRepository: MechanicDaoFacade,
    private val observerRepository: ObserverDaoFacade
) {

    suspend fun createDriver(fullName: String, phone: String): UserIdResponse {
        val id = driverRepository.createDriver(driver = UserDTO(fullName = fullName, phone = phone)) ?: Int.MIN_VALUE
        return UserIdResponse(userId = id)
    }

    suspend fun createMechanic(fullName: String, phone: String): UserIdResponse {
        val id = mechanicRepository.createMechanic(mechanic = UserDTO(fullName = fullName, phone = phone)) ?: Int.MIN_VALUE
        return UserIdResponse(userId = id)
    }

    suspend fun createObserver(fullName: String, phone: String): UserIdResponse {
        val id = observerRepository.createObserver(observer = UserDTO(fullName = fullName, phone = phone)) ?: Int.MIN_VALUE
        return UserIdResponse(userId = id)
    }

    suspend fun loginDriver(fullName: String): LoginResponse {
        val driver = driverRepository.findByFullName(username = fullName)
        return if (driver != null) {
            LoginResponse(
                userId = driver.id,
                position = Position.DRIVER.position,
                fullName = driver.fullName,
                phone = driver.phone
            )
        } else LoginResponse(
            userId = -1,
            position = "",
            fullName = "",
            phone = ""
        )
    }

    suspend fun loginMechanic(fullName: String): LoginResponse {
        val mechanic = mechanicRepository.findByFullName(username = fullName)
        return if (mechanic != null) {
            LoginResponse(
                userId = mechanic.id,
                position = Position.MECHANIC.position,
                fullName = mechanic.fullName,
                phone = mechanic.phone
            )
        } else LoginResponse(
            userId = -1,
            position = "",
            fullName = "",
            phone = ""
        )
    }

    suspend fun loginObserver(fullName: String): LoginResponse {
        val observer = observerRepository.findByFullName(username = fullName)
        return if (observer != null) {
            LoginResponse(
                userId = observer.id,
                position = Position.OBSERVER.position,
                fullName = observer.fullName,
                phone = observer.phone
            )
        } else LoginResponse(
            userId = -1,
            position = "",
            fullName = "",
            phone = ""
        )
    }

    suspend fun removeDriver(fullName: String): Boolean {
        return driverRepository.deleteDriver(username = fullName)
    }

    suspend fun removeMechanic(fullName: String): Boolean {
        return mechanicRepository.deleteMechanic(username = fullName)
    }

    suspend fun removeObserver(fullName: String): Boolean {
        return observerRepository.deleteObserver(username = fullName)
    }

    suspend fun allDrivers():List<UserDTO> {
        return driverRepository.allDrivers()
    }

    suspend fun allMechanics():List<UserDTO>{
        return mechanicRepository.allMechanics()
    }

    suspend fun allObservers():List<UserDTO>{
        return observerRepository.allObservers()
    }
}