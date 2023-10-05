package other

interface Mapper<S,R> {

    fun map(source:S):R
}