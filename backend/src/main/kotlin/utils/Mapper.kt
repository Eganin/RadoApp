package utils

interface Mapper<R,S> {

    fun map(source:S):R
}