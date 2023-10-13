package mapper

import models.CreateRequestIdItem
import models.CreateRequestIdResponse
import org.company.rado.core.MainRes
import other.Mapper

class CreateRequestIdMapper: Mapper<CreateRequestIdResponse,CreateRequestIdItem> {
    override fun map(source: CreateRequestIdResponse): CreateRequestIdItem {
       return if (source.id != -1){
            CreateRequestIdItem.Success(requestId = source.id)
        }else{
            CreateRequestIdItem.Error(message = MainRes.string.request_is_not_create)
        }
    }
}