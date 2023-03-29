package com.asgribovskaya.newsapp.util

//  Wrapper class for Retrofit responses
sealed interface ApiResponse<T>

class ApiSuccess<T>(val data: T): ApiResponse<T>
class ApiError<T>(val data: T? = null, val message: String): ApiResponse<T>
class ApiLoading<T>: ApiResponse<T>