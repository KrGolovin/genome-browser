package ru.krgolovin.genome.browser

import java.nio.file.Path

interface BedIndex {
    fun addEntry(chromosome: String, start: Int, end: Int, seek: Long)

    fun sort()

    fun write(indexPath: Path)

    fun search(chromosome: String, start: Int, end: Int) : List<Long>
}