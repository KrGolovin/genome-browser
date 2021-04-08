package ru.krgolovin.genome.browser

data class BedEntry(val chromosome: String, val start: Int, val end: Int, val other: List<Any>)