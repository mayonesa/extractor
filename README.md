# Extractor
Extracts date range, gender, and sentiment from JSON paragraph

## Assumptions
- For Time Duration, it only looks for dates of the format MM/DD/YYYY.
- For Gender, it looks for pronouns “he/him” and/or “she/her” (must be 90% diff or above).
- For Sentiment, it looks for positive and negative keywords (must be 75% diff or above):

## Ops

[SBT](https://www.scala-sbt.org/) is required for below instructions.

***To Test***: 

```
extractor> sbt test
```

***To Run***:

```
extractor> sbt run <in_file_name> <out_file_name>
```
