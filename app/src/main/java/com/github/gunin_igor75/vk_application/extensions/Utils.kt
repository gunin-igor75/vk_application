package com.github.gunin_igor75.vk_application.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge


fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}