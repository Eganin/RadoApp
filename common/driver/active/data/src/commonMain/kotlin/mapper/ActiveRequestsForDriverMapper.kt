package mapper

import models.ActiveRequestsForDriverItem
import models.SmallActiveRequestForDriverResponse
import other.Mapper

class ActiveRequestsForDriverMapper : Mapper<List<SmallActiveRequestForDriverResponse>,ActiveRequestsForDriverItem> {
    override fun map(source: List<SmallActiveRequestForDriverResponse>): ActiveRequestsForDriverItem {
        TODO("Not yet implemented")
    }
}