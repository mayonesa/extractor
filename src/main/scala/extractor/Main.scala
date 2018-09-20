package extractor

import java.io._
import io.Source.fromFile
import play.api.libs.json.Json.{ parse => json }

object Main {
  def main(args: Array[String]): Unit = {
    val in = args(0)
    val out = args(1)
    val p = (json(fromFile(in).getLines.mkString) \ "paragraph").as[String]
    val ext = new Extractor(p)
    val pw = new PrintWriter(new File(out))
    pw.write(s"""{ "timeDuration": ${ext.nDays}, "gender": "${ext.gender}", "sentiment": "${ext.sentiment}" }""")
    pw.close()
  }
}