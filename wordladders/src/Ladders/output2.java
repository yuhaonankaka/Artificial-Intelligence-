package Ladders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;

public class output2{
	
	public static List<String> wordList(String fileName) throws FileNotFoundException {
		
		File file = new File(fileName);
        List<String> wordlist = new ArrayList<String>();
        @SuppressWarnings("resource")
        Scanner scan=new Scanner(file);
		
        while (scan.hasNext()) {    
            wordlist.add(scan.nextLine());}
        return wordlist;
        }
	
   
    
    public static Map<Integer, List<String>> wordsByLength(List<String> wordlist) {
               
        Map<Integer, List<String>> wordsByLength = new TreeMap<Integer, List<String>>();
              
        for (String word : wordlist)
        {
        List<String> lengthlist = wordsByLength.get(word.length());
        if (lengthlist == null) {
              lengthlist = new ArrayList<String>();
              wordsByLength.put(word.length(), lengthlist);
        }
        if(!lengthlist.contains(word))
           lengthlist.add(word);
        }
        
         return wordsByLength;   
      }
    
   
	public static List<String> expand (Map<Integer, List<String>> wordlistbylength, String startword, String endword) {
		
	      Queue<String> node = new LinkedList<String>();
	      Map<String, String> wordmap = new HashMap<String, String>();
	      
		 node.offer(startword);
		    while(!node.isEmpty()) {
		    	 String current = node.poll();
		    	 List<String> adjcent =leavesWords(wordlistbylength,current);
		 
		    	 for (String nextword : adjcent) {
		    		 
		    		 if(comparestring(endword,nextword)) {
		    			 wordmap.put(nextword, current);
		    			 wordmap.put(endword, nextword);
		    			 wordmap.put(startword, null);
		    			 return findthepath(wordmap, startword, endword);
		    			 
		    		 }
		    			 
		    		 
		    		 if(wordmap.get(nextword) == null){
		    			 wordmap.put(nextword, current);
		    			 node.offer(nextword);
		    		 }
		    	 }
		    		 
		
		  } 
		  wordmap.put(startword, null);
		  return findthepath(wordmap, startword, endword);
	
	}
	private static List<String> findthepath (Map<String, String> wordmap, String startword, String endword){
		         LinkedList<String> result = null;
		      
		         if(wordmap.get(endword) != null){
		        	    
		              result = new LinkedList<String>();
		              for(String s = endword; s != null; s = wordmap.get(s)) {
		            	       
		                    result.addFirst(s);
		              }
		         }
		         return result;
	   }
	
	
	public static List<String> leavesWords(Map<Integer, List<String>> wordlistbyLength,String word){
		
		  int len=word.length();
		  List<String> templist=new ArrayList<String>();
	      List<String> leavesWords = new ArrayList<String>();
		  
		  if(wordlistbyLength.get(len-1)!=null) {
		         List<String> part1=wordlistbyLength.get(len-1);
		         templist.addAll(part1);
		  }
		  if(wordlistbyLength.get(len+1)!=null) {
			     List<String> part2=wordlistbyLength.get(len+1);
			     templist.addAll(part2);
		  }
			
		  if(!templist.isEmpty()) {
		      String[] tempwords = new String[templist.size()];
		      templist.toArray(tempwords);
		     
              for (int i = 0; i < tempwords.length; i++) {
               	  
		          if (comparestring(word, tempwords[i])) {
		        	  leavesWords.add( tempwords[i]);
		         }
		 
              }
		  }
		return leavesWords;
	}
	
	public static boolean comparestring (String s1,String s2) {
	  if(s1.length()!=(s2.length()+1)&&s1.length()!=(s2.length()-1))
	      return false;
	    char [] sc1 = new char [s1.length()];
	    char [] sc2 = new char [s2.length()];
	    sc1 = s1.toCharArray();
	    sc2 = s2.toCharArray();
	   String [] ss1 = new String [s1.length()];
	    String [] ss2 = new String [s2.length()]; 
	    for (int i = 0;i<s1.length();i++) {
	      ss1[i] = String.valueOf(sc1[i]);
	    }
	    for (int i = 0;i<s2.length();i++) {
	      ss2[i] = String.valueOf(sc2[i]);
	    }
	    
	    List<String> list1 = (List<String>) Arrays.asList(ss1);
	    List<String> list2 = (List<String>) Arrays.asList(ss2);
	    Collections.sort(list1);
	    Collections.sort(list2);
	    /*  System.out.println(list1);
	    System.out.println(list2);*/
	    for (int i = 0;i<s1.length();i++) {
	      ss1[i] = list1.get(i);
	    }
	    for (int i = 0;i<s2.length();i++) {
	      ss2[i] = list2.get(i);
	    }
	    for (int i = 0;i<s1.length();i++) {
	      for (int j = 0;j<s2.length();j++) {
	        if (ss1[i].equalsIgnoreCase(ss2[j])) {
	          ss1[i] = "yes";
	          ss2[j] = "yes";
	          break;
	        }
	      }
	    }
	    
	   
	    
	    int minnumber = Math.min(s1.length(), s2.length());
	    int samenumber=0;
	    
	    
	    
	    for (int i = 0;i<s1.length();i++) {
	      if (ss1[i].equalsIgnoreCase("yes")) {
	        samenumber++;
	      }
	    }
	    
	    return (samenumber== minnumber);
	    
	  }
		 
	
	
	
     public static void main (String[] args) throws FileNotFoundException {
    	 
    	    List<String> wordlist= wordList("wordList.txt");
    	    Map<Integer, List<String>> wordsbylengthmap=wordsByLength(wordlist);
    	    List<String> path=expand ( wordsbylengthmap, args[0], args[1]);
    	    String [] a = new String [path.size()];
    	    path.toArray(a);
    	    for (int i = 0;i<a.length;i++) {
    	      System.out.print((i+1)+": "+a[i]+"\n");
    	      }
    	 
    	    
    	    
      
     }
     
     
  

}
