package models.users

import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

class UserDTOMapperForDriver : Mapper<UserDTO, ResultRow> {
    override fun map(source: ResultRow): UserDTO {
        return UserDTO(
            id = source[Drivers.id],
            fullName = source[Drivers.fullName],
            phone = source[Drivers.phone]
        )
    }
}

class UserDTOMapperForMechanic : Mapper<UserDTO, ResultRow> {
    override fun map(source: ResultRow): UserDTO {
        return UserDTO(
            id = source[Mechanics.id],
            fullName = source[Mechanics.fullName],
            phone = source[Mechanics.phone]
        )
    }
}

class UserDTOMapperForObserver : Mapper<UserDTO, ResultRow> {
    override fun map(source: ResultRow): UserDTO {
        return UserDTO(
            id = source[Observers.id],
            fullName = source[Observers.fullName],
            phone = source[Observers.phone]
        )
    }
}