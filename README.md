# Extractor
Extracts date range, gender, and sentiment from JSON paragraph

Pretend you are working on a project to extract particular facts from freeform English text.
This app:
- Reads a text file of a specified format
- Outputs specified data points to a text file
Program inputs:
Format : JSON
paragraph:
String – paragraph of English text
Example:
{ paragraph: "John downloaded the Pokemon Go app on
07/15/2017. By 07/22/2017, he was on level 24.
Initially, he was very happy with the app. However, he
soon became very disappointed with the app because it
was crashing very often. As soon as he reached level 24,
he uninstalled the app."
 }
Expected output:
We want to exact 3 kinds of information from each paragraph of input: duration if there are at least two dates, gender of the subject, sentiment expressed in the text.
Once these are extracted, the data should be written out to a file.
Format : JSON
timeDuration:
Integer – time duration in days (inclusive of the given dates). If unknown or uncomputable, then 0.
gender:
String – “male”, “female”, or “unknown”
sentiment:
String – “positive”, “negative”, “mixed” or “unknown”
Expected output for example:
{ timeDuration: 8, gender: "male", sentiment: "mixed" }
Simplifying assumptions:
For Time Duration, it only looks for dates of the format MM/DD/YYYY. For Gender, it looks for pronouns “he/him” and/or “she/her” (must get 90% diff).
For Sentiment, it looks for the following keywords (must get 75% diff):
● Positive sentiments: Happy, Glad, Jubilant, Satisfied
● Negative sentiments: Sad, Disappointed, Angry, Frustrated

## Ops

[SBT](https://www.scala-sbt.org/) is required for below instructions but binaries w/ launcher script can be sent if preferred.

***To Test***: 

```
extractor> sbt test
```

***To Run***:

```
extractor> sbt run [*in_file_name*] [*out_file_name*]
