package pt.up.fe.cpm.tiktek.core.network.retrofit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

internal class ResultCall<T>(private val call: Call<T>, private val json: Json) : Call<NetworkResult<T>> {
    @OptIn(ExperimentalSerializationApi::class)
    private fun onResponse(response: Response<T>): Response<NetworkResult<T>> =
        if (response.isSuccessful) {
            Response.success(NetworkResult.Success(response.body()!!))
        } else {
            try {
                Response.success(NetworkResult.Error(json.decodeFromStream(response.errorBody()!!.byteStream())))
            } catch (e: Throwable) {
                onFailure()
            }
        }

    private fun onFailure(): Response<NetworkResult<T>> = Response.success(NetworkResult.Failure)

    override fun clone() = ResultCall(call.clone(), json)

    override fun execute(): Response<NetworkResult<T>> =
        try {
            onResponse(call.execute())
        } catch (e: Throwable) {
            onFailure()
        }

    override fun enqueue(callback: Callback<NetworkResult<T>>) =
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>,
                ) {
                    callback.onResponse(
                        this@ResultCall,
                        onResponse(response),
                    )
                }

                override fun onFailure(
                    call: Call<T>,
                    t: Throwable,
                ) {
                    callback.onResponse(this@ResultCall, onFailure())
                }
            },
        )

    override fun isExecuted() = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled() = call.isCanceled

    override fun request() = call.request()

    override fun timeout() = call.timeout()
}

internal class ResultCallAdapterFactory
    @Inject
    constructor(private val json: Json) : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ): CallAdapter<*, *>? {
            if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) return null

            val upper = getParameterUpperBound(0, returnType)

            return if (upper is ParameterizedType && upper.rawType == NetworkResult::class.java) {
                object : CallAdapter<Any, Call<NetworkResult<*>>> {
                    override fun responseType() = getParameterUpperBound(0, upper)

                    override fun adapt(call: Call<Any>): Call<NetworkResult<*>> = ResultCall(call, json) as Call<NetworkResult<*>>
                }
            } else {
                null
            }
        }
    }
