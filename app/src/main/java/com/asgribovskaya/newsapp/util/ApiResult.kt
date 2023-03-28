package com.asgribovskaya.newsapp.util

//  Wrapper class for Retrofit responses
sealed interface ApiResult<T>

class ApiSuccess<T>(val data: T): ApiResult<T>
class ApiError<T>(val data: T? = null, val message: String): ApiResult<T>
class ApiLoading<T>: ApiResult<T>