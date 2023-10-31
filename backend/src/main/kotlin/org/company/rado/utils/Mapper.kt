package org.company.rado.utils

interface Mapper<R,S> {

    fun map(source:S):R
}