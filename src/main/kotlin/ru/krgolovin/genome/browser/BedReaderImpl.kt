package ru.krgolovin.genome.browser

import java.io.RandomAccessFile
import java.nio.file.Path

class BedReaderImpl : BedReader {
    override fun createIndex(bedPath: Path, indexPath: Path) {
        val raf = RandomAccessFile(bedPath.toString(), "r")
        val bedIndex = BedIndexImpl()
        var currFilePointer = 0L
        var line = raf.readLine()
        while (line != null) {
            val parts = line.split('\t')
            bedIndex.addEntry(
                parts[0],
                parts[1].toInt(),
                parts[2].toInt(),
                currFilePointer
            )
            currFilePointer = raf.filePointer
            line = raf.readLine()
        }
        bedIndex.sort()
        bedIndex.write(indexPath)
    }


    override fun loadIndex(indexPath: Path): BedIndex {
        val bedIndex = BedIndexImpl()
        bedIndex.read(indexPath)
        return bedIndex
    }


    override fun findWithIndex(
        index: BedIndex, bedPath: Path,
        chromosome: String, start: Int, end: Int
    ): List<BedEntry> {
        val resultList = mutableListOf<BedEntry>()
        val listOfSeeks = index.search(chromosome, start, end)
        val raf = RandomAccessFile(bedPath.toString(), "r")
        listOfSeeks.forEach() { seek ->
            raf.seek(seek)
            val parts = raf.readLine().split('\t')
            resultList.add(
                BedEntry(
                    parts[0],
                    parts[1].toInt(),
                    parts[2].toInt(),
                    parts.subList(3, parts.size)
                )
            )
        }
        return resultList
    }
}