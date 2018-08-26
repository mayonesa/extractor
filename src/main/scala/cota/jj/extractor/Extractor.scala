package cota.jj.extractor

import java.text.SimpleDateFormat

class Extractor(source: String) {
  private lazy val millisPerDay = 86400000
  private lazy val words = source.toLowerCase.split("\\W+").toSeq
  private lazy val dateFormat = new SimpleDateFormat("MM/dd/yyyy")
  private lazy val datePattern = raw"(\d{2})/(\d{2})/(\d{4})".r
  private lazy val timesInMillis = datePattern.findAllIn(source).map(dateFormat.parse(_).getTime).toSet

  // assumption: max-min MM/DD/YYYY inclusive diff
  private[extractor] lazy val nDays = if (timesInMillis.size < 2) 0
                                      else ((timesInMillis.max - timesInMillis.min) / millisPerDay + 1)
    
  // assumptions: 
  //  ● only "he"/"him" and "she"/"her" considered
  //  ● gender >= 90%
  private[extractor] lazy val gender = cat(Set("he", "him"), Set("she", "her"), "male", "female", "unknown", .9f)
    
  // assumptions: 
  //  ● Positive sentiments: Happy, Glad, Jubilant, Satisfied
  //  ● Negative sentiments: Sad, Disappointed, Angry, Frustrated  
  //  ● sentiment >= 75%
  private[extractor] lazy val sentiment = cat(Set("happy", "glad", "jubilant", "satisfied"),
                                              Set("sad", "disappointed", "angry", "frustrated"),
                                              "positive", "negative", "mixed", .75f)

  private def cat(lTkns: Set[String],
                  rTkns: Set[String],
                  lLbl: String, // left label
                  rLbl: String, // right label
                  nLbl: String, // neither left or right (just staying home tonight) label
                  lThreshold: Float) = {
    def n(tkns: Set[String]) = words.filter(tkns).size   
    val nLs = n(lTkns)
    val nRs = n(rTkns)
    val total = nLs + nRs
    lazy val lPercDiff = nLs.toFloat / total
    if (total == 0) "unknown"
    else if (lPercDiff >= lThreshold) lLbl 
    else if (lPercDiff <= 1 - lThreshold) rLbl 
    else nLbl
  }
}