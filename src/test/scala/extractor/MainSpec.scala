package extractor

import org.scalatest.FlatSpec

import java.io.File
import io.Source.fromFile
import play.api.libs.json.Json.{ parse => json }

class MainSpec extends FlatSpec {
  private val path = "src/test/resources/"
  private val outFile = path + "out.test.json"
  Main.main(Array(path + "in.test.json", outFile))
  private val outJson = json(fromFile(outFile).getLines.mkString)
  
  "app" should "return proper days" in {
    assert((outJson \ "timeDuration").as[Int] == 7)
  }
  it should "return proper gender" in {
    assert((outJson \ "gender").as[String] == "male")
  }
  it should "return proper sentiment" in {
    assert((outJson \ "sentiment").as[String] == "mixed")
  }
}