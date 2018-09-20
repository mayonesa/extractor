package extractor

import org.scalatest.FlatSpec

class ExtractorSpec extends FlatSpec {
  "A paragraph void of dates" should "return 0" in {
    assert(new Extractor("I don't have a date").nDays == 0)
  }
  "A paragraph with only one date" should "return 0" in {
    assert(new Extractor("my only date is 10/24/1993 because that's the only one that really matters").nDays == 0)
  }
  it should "return 0 even when there are no leading or trailing spaces around the date" in {
    assert(new Extractor("my only date is:10/24/1993.because that's the only one that really matters").nDays == 0)
  }
  "A paragraph with only two dates" should "return their absolute inclusive diff" in {
    assert(new Extractor("10/27/1993 was different from previous 10/27s, because 10/24/1993").nDays == 4)
  }
  "A paragraph with more than two dates" should "return their greatest absolute inclusive diff" in {
    assert(new Extractor(
      "10/27/1993 was different from previous 10/27s, because 10/24/1993. However, 10/24/1994 is going to be the best"
    ).nDays == 366)
  }
  
  private val unknown = "unknown"
  private val male = "male"
  private val female = "female"
  private val positive = "positive"
  private val negative = "negative"
  private val mixed = "mixed"
  private val empty = new Extractor("")

  "An empty paragraph" should "return 0 # of days" in {
    assert(empty.nDays == 0)
  }
  it should s"return '$unknown' gender" in {
    assert(empty.gender == unknown)
  }
  it should s"return '$unknown' sentiment" in {
    assert(empty.sentiment == unknown)
  }
  
  val escapees = new Extractor(
      "\"10/27/1993\" was different from previous 10/27s, \"because\" 10/24/1993. However, in 10/24/1994, he was \"happy\"")

  "Escapees" should "still return the # of days" in {
    assert(escapees.nDays == 366)
  }
  they should "still return the gender" in {
    assert(escapees.gender == male)
  }
  they should s"still return the sentiment" in {
    assert(escapees.sentiment == positive)
  }
  
  "90% " + male should "return " + male in {
    assert(new Extractor(
      "he is cool. he is he. he rocks. he is the best. he is awesome. he is wonderful. he is truth. then why does she hate him?"
    ).gender == male) 
  }
  "80% " + male should s"return $unknown in spite of capitalization" in {
    assert(new Extractor(
      "he is cool. he is him. he rocks. he is the best. he is awesome. he is wonderful. She is truth. then why does She hate him?"
    ).gender == unknown) 
  }
  "90% " + female should s"return $female in spite of capitalization" in {
    assert(new Extractor(
      "she is cool. she is HER. she rocks. she is the best. she is awesome. she is wonderful. she is her own truth. then why does she hate him?"
    ).gender == female) 
  }
  "80% " + female should "return " + unknown in {
    assert(new Extractor(
      "she is cool. she is her. she rocks. she is the best. she is awesome. she is wonderful. he is truth. then why does she hate him?"
    ).gender == unknown) 
  }
  
  "50% " + positive should "return " + mixed in {
    assert(new Extractor(
      "Happy is the man who is like a tree. Planted by the streams in season bearing fruit even when disappointed."
    ).sentiment == mixed)
  }
  "75% " + positive should "return " + positive in {
    assert(new Extractor(
      "Happy is the glad man who is like a jubilant tree. Planted by the streams in season bearing fruit even when disappointed."
    ).sentiment == positive)
  }
  "0 sentiment tokens" should "return " + unknown in {
    assert(new Extractor(
      "toston is the gladiator man who is like a tree. Planted by the streams in season bearing fruit."
    ).sentiment == unknown)
  }
  "75% " + negative should "return " + negative in {
    assert(new Extractor(
      "Happy is the angry man who is like a frustrated tree. Planted by the streams in season bearing fruit even when disappointed."
    ).sentiment == negative)
  }
}