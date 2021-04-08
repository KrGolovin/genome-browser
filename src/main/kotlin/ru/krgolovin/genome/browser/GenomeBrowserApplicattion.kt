package ru.krgolovin.genome.browser

import java.nio.file.Path

fun main(args: Array<String>) {
    val bedReader = BedReaderImpl()
    val bedPath = Path.of(args[0])
    val indexPath = Path.of(args[1])
    val chromosome = "chr7"
    val start = 127474690
    val end = 127481690
    bedReader.createIndex(bedPath, indexPath)
    bedReader.loadIndex(indexPath)
    print(bedReader.findWithIndex(
        bedReader.loadIndex(indexPath),
        bedPath,
        chromosome,
        start,
        end
    ).size)
}
