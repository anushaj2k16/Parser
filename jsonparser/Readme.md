# JSON parser 

 * **Dataset**  - json format [extracted from DBLP, ACM, MAG and others. Published by Aminer, contains 3,079,007 papers and 25,166,994 citation relationships (2017-10-27) stored in four seperate files]
 
 
 * **libraries used** - gson and json-simple(Maven dependencies are added)
 
 
  **dblp_parser.java**
  
  * Reads a line from the file of the format
 	 
 	 * ({"abstract": "The purpose of this study is to develop a learning tool for high school students studying the scientific aspects of information and  communication net- works. More specifically, we focus on the basic principles of network proto- cols as the aim to develop our learning tool. Our tool gives students hands-on experience to help understand the basic principles of network protocols.", "authors": ["Makoto Satoh", "Ryo Muramatsu", "Mizue Kayama", "Kazunori Itoh", "Masami Hashimoto", "Makoto Otani", "Michio Shimizu", "Masahiko Sugimoto"], "n_citation": 0, "references": ["51c7e02e-f5ed-431a-8cf5-f761f266d4be", "69b625b9-ebc5-4b60-b385-8a07945f5de9"], "title": "Preliminary Design of a Network Protocol Learning Tool Based on the Comprehension of High School Students: Design by an Empirical Study Using a Simple Mind Map", "venue": "international conference on human-computer interaction", "year": 2013, "id": "00127ee2-cb05-48ce-bc49-9de556b93346"})*
 	 
  * Every line is converted into jsonArray by enclosing with "[]"  then explicitly converted to json object and then each attribute value is extracted based on its key and written into csv/dsv files (delimiter - "$:$")
  
  
  * This process is carried out on each file, one file at a time.
  
  **authorRelation.java**
  
   * Reads a line from the file.
   
   For every author,
    
   	- unique ID is assigned and added to authorVertex.dsv  
   		
   	- for every paper, paper-author relation is added to paperAuthorRelationship.dsv
   		
   	- co-authorship relation between authors for every paper is added to coauthorshipRelationship.dsv ( distinct records are selected by querying in oracle database)
   
  Redis hashes, is used to store authorName and Author ID as field-value pairs in authorList hashes to avoid duplication of authors. Before writing author details to files it is checked if it already exists, if it does then relevant author ID is fetched, else new id is created and written.
  
  
  **journalRelation.java**
  * Reads a line from the file dblp+ref-newdelimiter-all.csv
   
   For every journal,
    
   	- unique ID is assigned and added to journalVertex.dsv  
   		
   	- for every paper, paper-journal relation is added to paperJournalRelationship.dsv
   		   
  Redis hashes, is used to store journalName and journal ID as field-value pairs in journalList hashes to avoid duplication of journals. Before writing journal details to files it is checked if it already exists, if it does then relevant journal ID is fetched, else new id is created and written.
  
   **venueRelation.java**
  * Reads a line from the file dblp+ref-newdelimiter-all.csv
   
   For every venue,
    
   	- unique ID is assigned and added to venueVertex.dsv  
   		
   	- for every paper, paper-journal relation is added to paperVenueRelationship.dsv
   		   
  Redis hashes, is used to store venueName and venue ID as field-value pairs in venueList hashes to avoid duplication of venues. Before writing venue details to files it is checked if it already exists, if it does then relevant venue ID is fetched, else new id is created and written.
  
  
  **referenceRelation.java**
  
  * Reads a line from the file dblp+ref-newdelimiter-all.csv, If a paper contains reference information then reference relationship (paperid, referenceid) is written in the file.