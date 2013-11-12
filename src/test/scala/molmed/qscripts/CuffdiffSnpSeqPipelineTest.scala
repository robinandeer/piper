package molmed.qscripts

import org.testng.annotations._
import molmed.queue.SnpSeqBaseTest
import org.broadinstitute.sting.queue.pipeline._

/**
 * TODO
 * Implement cluster style testing as in AlignWithBwaSnpSeqPipelineTest
 */

class CuffdiffSnpSeqPipelineTest {

  val pathToScript = "-S src/main/scala/molmed/qscripts/Cuffdiff.scala"

  val snpSeqBaseTest = SnpSeqBaseTest

  var run: Boolean = false

  @BeforeClass
  @Parameters(Array("runpipeline"))
  def init(runpipeline: Boolean): Unit = {
    this.run = runpipeline
  }

  @Test
  def testBasicCuffdiff {
    val projectName = "test1"

    val geneExp = "gene_exp.diff"
    val isoforms = "isoform_exp.diff"

    val spec = new PipelineTestSpec
    spec.jobRunners = Seq("Shell")
    spec.name = "BasicCuffdiff"
    spec.args = Array(
      pathToScript,
      " -i " + snpSeqBaseTest.pathToCuffdiffCohortFile,
      " --reference " + snpSeqBaseTest.hg19,
      " --annotations " + snpSeqBaseTest.hg19annotations,
      " --path_to_cuffdiff " + snpSeqBaseTest.pathToCuffdiff,
      " --library_type " + " fr-secondstrand ",
      " -startFromScratch ").mkString

    spec.fileMD5s += geneExp -> "7ec98841c806d6e56d67693f1b799e7f"
    spec.fileMD5s += isoforms -> "a954b8ab4293a587a66351071a9f72a7"

    spec.run = run
    PipelineTest.executeTest(spec)
  }

}