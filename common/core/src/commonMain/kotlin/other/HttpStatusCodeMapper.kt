package other

import io.ktor.http.HttpStatusCode
import org.company.rado.core.MainRes

internal class HttpStatusCodeMapper: Mapper<HttpStatusCode,WrapperForResponse> {
    override fun map(source: HttpStatusCode): WrapperForResponse {
        return if (source == HttpStatusCode.OK){
            WrapperForResponse.Success()
        }else{
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }
}