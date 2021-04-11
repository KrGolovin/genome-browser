package ru.krgolovin.genome.browser

import java.nio.file.Path

interface BedIndex {

    /**
     * add Entry to index, where chromosome is a name of [chromosome],
     * [start] is chromStart, [end] is chromEnd and [seek] is position of this entry in Bed file
     */
    fun addEntry(chromosome: String, start: Int, end: Int, seek: Long)

    /**
     * this method must be called after adding all entries from Bed file
     */
    fun sort()

    /**
     * save index to [indexPath]
     */
    fun write(indexPath: Path)

    /**
     * read index from [indexPath]
     */
    fun read(indexPath: Path)

    /**
     * return list of Entries at the specified request
     */
    fun search(chromosome: String, start: Int, end: Int) : List<Long>
}