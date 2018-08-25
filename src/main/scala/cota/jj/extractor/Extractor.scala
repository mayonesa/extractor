package cota.jj.extractor

import java.text.SimpleDateFormat

class Extractor(source: String) {
  private lazy val millisPerDay = 86400000
  private lazy val words = source.toLowerCase.split("\\W+").toSeq
  private lazy val dateFormat = new SimpleDateFormat("MM/dd/yyyy")
  private lazy val datePattern = raw"(\d{2})/(\d{2})/(\d{4})".r
  private lazy val timesInMillis = datePattern.findAllIn(source).map(dateFormat.parse(_).getTime).toSet
  private lazy val nDaysVal = if (timesInMillis.size < 2) 0
                              else ((timesInMillis.max - timesInMillis.min) / millisPerDay + 1) 
  private lazy val genderVal = cat(Set("he", "him"), Set("she", "her"), "male", "female", "unknown", .9f) 
  private lazy val sentimentVal = cat(Set("happy", "glad", "jubilant", "satisfied"), 
                                      Set("sad", "disappointed", "angry", "frustrated"),
                                      "positive", "negative", "mixed", .75f)
  
  // assumption: max-min MM/DD/YYYY inclusive diff
  private[extractor] def nDays = nDaysVal
    
  // assumptions: 
  //  ● only "he"/"him" and "she"/"her" considered
  //  ● gender >= 90%
  private[extractor] def gender = genderVal
    
  // assumptions: 
  //  ● Positive sentiments: Happy, Glad, Jubilant, Satisfied
  //  ● Negative sentiments: Sad, Disappointed, Angry, Frustrated  
  //  ● sentiment >= 75%
  private[extractor] def sentiment = sentimentVal
    
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