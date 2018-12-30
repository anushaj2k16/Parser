

* cleaner.java is used to remove extra records been added due to new line character present in the abstract of the downloaded dblp+citation dataset. The program input should be output of json parser and should be run for each file (total of 4 files). The records identified by program should be corrected manually.


* comparator.java picks a line of parsed file from aminer checks for matching line in parsed file of dblp, if it matches then attribute from both files will be integrated and written to new file.( Not efficient for large files, hence database joins were chosen over this)

* integratedFataStruct.java defines data structure to hold values before writing into files.
