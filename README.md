# Extractor
Extracts date range, gender, and sentiment from JSON paragraph

## Assumptions
- For Time Duration, it only looks for dates of the format MM/DD/YYYY.
- For Gender, it looks for pronouns “he/him” and/or “she/her” (must get 90% diff).
- For Sentiment, it looks for positive and negative keywords (must get 75% diff):

## Ops

[SBT](https://www.scala-sbt.org/) is required for below instructions but binaries w/ launcher script can be sent if preferred.

***To Test***: 

```
extractor> sbt test
```

***To Run***:

```
extractor> sbt run [in_file_name (default: in.json)] [out_file_name (default: out.json)]
```
note: default in/output files above are in src/main/resources
