package ru.krgolovin.genome.browser

import java.nio.file.Path


interface BedReader {

    fun createIndex(bedPath: Path, indexPath: Path)


    fun loadIndex(indexPath: Path): BedIndex


    fun findWithIndex(
        index: BedIndex, bedPath: Path,
        chromosome: String, start: Int, end: Int
    ): List<BedEntry>

}