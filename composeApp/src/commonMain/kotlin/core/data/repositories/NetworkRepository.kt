package core.data.repositories


import core.domain.NetworkErrors
import core.domain.Resource
import io.ktor.client.network.sockets.*
import io.ktor.utils.io.errors.*

abstract class NetworkRepository {
    suspend fun <Dto, DomainModel> getMany(
        call: suspend () -> List<Dto>,
        toDomain: (Dto) -> DomainModel
    ): Resource<List<DomainModel>, NetworkErrors> {
        return try {
            val dtos = call()
            Resource.Success(dtos.map { toDomain(it) })
        } catch (e: IOException) {
            catch(e)
        } 
    }

    suspend fun <Dto, DomainModel> getOne(
        call: suspend () -> Dto,
        toDomain: (Dto) -> DomainModel
    ): Resource<DomainModel, NetworkErrors> {
        return try {
            val dto = call()
            Resource.Success(toDomain(dto))
        }  catch (e: Exception) {
            catch(e)
        }
    }
    
    private fun catch(e: Exception): Resource.Failure<NetworkErrors> = when(e) {
        is IOException -> Resource.Failure(NetworkErrors.NO_INTERNET)
        is SocketTimeoutException -> Resource.Failure(NetworkErrors.REQUEST_TIMEOUT)
        else -> Resource.Failure(NetworkErrors.UNKNOWN)
    }
}