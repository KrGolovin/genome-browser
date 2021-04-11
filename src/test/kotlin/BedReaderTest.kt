import org.junit.Assert
import org.junit.Test
import ru.krgolovin.genome.browser.BedEntry
import ru.krgolovin.genome.browser.BedReaderImpl
import java.nio.file.Path


class BedReaderTest {
    @Test
    fun emptyDbSearch() {
        val bedReader = BedReaderImpl()
        val bedPath = Path.of("src/test/resources/EmptyDbSearchTest.bed")
        val indexPath = Path.of("src/test/resources/EmptyDbSearchTest.indx")
        bedReader.createIndex(
            bedPath,
            indexPath
        )
        Assert.assertEquals(
            bedReader.findWithIndex(
                bedReader.loadIndex(indexPath),
                bedPath,
                "chr7",
                1,
                3
            ), listOf<BedEntry>()
        )
    }

    @Test
    fun emptyAnswerSearch() {
        val bedReader = BedReaderImpl()
        val bedPath = Path.of("src/test/resources/EmptyAnswerSearchTest.bed")
        val indexPath = Path.of("src/test/resources/EmptyAnswerSearchTest.indx")
        bedReader.createIndex(
            bedPath,
            indexPath
        )
        Assert.assertEquals(
            bedReader.findWithIndex(
                bedReader.loadIndex(indexPath),
                bedPath,
                "chr7",
                127471196,
                127472363
            ), listOf<BedEntry>()
        )
    }

    @Test
    fun singleEntryAnswerSearch() {
        val bedReader = BedReaderImpl()
        val bedPath = Path.of("src/test/resources/SingleEntryAnswerSearchTest.bed")
        val indexPath = Path.of("src/test/resources/SingleEntryAnswerSearchTest.indx")
        bedReader.createIndex(
            bedPath,
            indexPath
        )
        Assert.assertEquals(
            bedReader.findWithIndex(
                bedReader.loadIndex(indexPath),
                bedPath,
                "chr7",
                127471196,
                127472364
            ), listOf<BedEntry>(
                BedEntry(
                    "chr7",
                    127471196,
                    127472363,
                    "Pos1	0	+	127471196	127472363	255,0,0".split('\t')
                )
            )
        )
    }

    @Test
    fun bigDataSearch() {
        val bedReader = BedReaderImpl()
        val bedPath = Path.of("src/test/resources/BigDataSearchTest.bed")
        val indexPath = Path.of("src/test/resources/BigDataSearchTest.indx")
        bedReader.createIndex(
            bedPath,
            indexPath
        )
        Assert.assertEquals(
            bedReader.findWithIndex(
                bedReader.loadIndex(indexPath),
                bedPath,
                "chr7",
                156447603,
                156496110
            ), listOf<BedEntry>(
                //chr7	156447605	156449580	uc010lqp.1	0	+	156447626	156448738	0,255,0	.	NOM1
                //chr7	156479506	156494890	uc003wmz.2	0	-	156479506	156479506	255,0,0	.	AY927465
                //chr7	156479506	156496108	uc003wna.2	0	-	156479580	156495805	255,0,0	.	MNX1
                BedEntry(
                    "chr7",
                    156447605,
                    156449580,
                    "uc010lqp.1\t0\t+\t156447626\t156448738\t0,255,0\t.\tNOM1".split('\t')
                ),
                BedEntry(
                    "chr7",
                    156479506,
                    156496108,
                    "uc003wna.2\t0\t-\t156479580\t156495805\t255,0,0\t.\tMNX1".split('\t')
                ),
                BedEntry(
                    "chr7",
                    156479506,
                    156494890,
                    "uc003wmz.2\t0\t-\t156479506\t156479506\t255,0,0\t.\tAY927465".split('\t')
                )
            )
        )
    }
}