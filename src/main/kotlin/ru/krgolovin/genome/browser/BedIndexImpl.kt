package ru.krgolovin.genome.browser

import java.io.File
import java.io.RandomAccessFile
import java.nio.file.Path

class BedIndexImpl : BedIndex {

    override fun addEntry(chromosome: String, start: Int, end: Int, seek: Long) {
        val indexEntry = IndexEntry(start, end, seek)
        entriesMap.computeIfAbsent(chromosome) { mutableListOf() }
        entriesMap.getValue(chromosome).add(indexEntry)
    }

    override fun sort() {
        for (indexEntries in entriesMap.values) {
            indexEntries.sortedBy { it.begin }
        }
    }

    override fun write(indexPath: Path) {
        val fw = File(indexPath.toString())
        fw.printWriter().use { out ->
            out.println("${entriesMap.size}")
            for (chrName in entriesMap.keys) {
                out.println("${chrName}\n${entriesMap.getValue(chrName).size}")
                entriesMap.getValue(chrName).forEach() {
                    out.println("${it.begin} ${it.end} ${it.seek}")
                }
            }
        }
    }

    override fun read(indexPath: Path) {
        val raf = RandomAccessFile(indexPath.toString(), "r")
        val countOfChromosomes = raf.readLine()
        repeat(countOfChromosomes.toInt()) {
            val chromosome = raf.readLine()
            val countOfEntries = raf.readLine().toInt()
            repeat(countOfEntries) {
                val parts = raf.readLine().split(' ')
                addEntry(
                    chromosome,
                    parts[0].toInt(),
                    parts[1].toInt(),
                    parts[2].toLong()
                )
            }
        }
    }

    override fun search(chromosome: String, start: Int, end: Int): List<Long> {
        val resultList = mutableListOf<Long>()
        val receivedList = entriesMap[chromosome]
        receivedList ?: return resultList
        val startIndex = leftBinSearch(receivedList, start)
        if (startIndex == receivedList.size) {
            return resultList
        }
        for (i in startIndex until receivedList.size) {
            if (receivedList[i].end < end) {
                resultList.add(receivedList[i].seek)
            }
        }
        return resultList
    }

    /**
     * an implementation was needed
     * because standard binary search searches for any element equal to the desired one,
     * but we need only left element
     */
    private fun leftBinSearch(listOfIndexEntry: List<IndexEntry>, key: Int): Int {
        var l = -1
        var r = listOfIndexEntry.size
        while (l < (r - 1)) {
            val m = (l + r) / 2
            if (listOfIndexEntry[m].begin < key) {
                l = m
            } else {
                r = m
            }
        }
        return r
    }

    private val entriesMap: MutableMap<String, MutableList<IndexEntry>> = mutableMapOf()

    data class IndexEntry(val begin: Int, val end: Int, val seek: Long)

}