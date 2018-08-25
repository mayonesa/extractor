package cota.jj.extractor

import java.io._
import io.Source.fromFile
import play.api.libs.json.Json.{ parse => json }

object Main {
  def main(args: Array[String]): Unit = {
    val in = if (args.isEmpty) "in.json" else args(0)
    val out = if (args.length > 1) args(1) else "out.json"
    val p = (json(fromFile(in).getLines.mkString) \ "paragraph").as[String]
    val ext = new Extractor(p)
    val pw = new PrintWriter(new File(out))
    pw.write(s"""{ "timeDuration": ${ext.nDays}, "gender": "${ext.gender}", "sentiment": "${ext.sentiment}" }""")
    pw.close()
  }
}