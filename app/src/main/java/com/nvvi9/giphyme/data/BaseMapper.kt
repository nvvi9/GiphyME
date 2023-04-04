package com.nvvi9.giphyme.data

interface BaseMapper<in T, out R> {
    fun map(value: T): R
}