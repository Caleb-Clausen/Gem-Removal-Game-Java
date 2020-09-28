// Imported Java.util.* and used the ArrayList<> out of the package
import java.util.*;
// The Board class is used to do basic oporations on a 2d Gem Array that it holds as a private varible
// it is the representation of a 2d Gem Array. I have made two helper methods to help with the "doRemovals()"
// method. they are called "dropgems()" "columgemsmove()"  and  they are broken into logical steps, first
// "dropgems()" is called to drop all the gems into place and it returns an arraylist of all the
// empty columns that "columgemsmove()" uses to keep count of how many it needs to move to the left.
// they are both called Once when "doRemvals()" is called. 
public class Board {
  
  // Declared a Gem 2d Array "gemArray" to hold the actual Gems for the Board
  private Gem gemArray[][];
  
  // Declared ints row and col to hold the dimensions of the board and how many rows there are
  private int row;
  private int col;
  
  // Convenience constructor to build from a 2D array of ints. If
  // negative int appears, treat it as an empty gem. Otherwise, the
  // numbers represent the kinds of the gems to be created.
  public Board(int g[][]){
    this.row = g.length;
    this.col = g[0].length;
    
    // Made a new 2d Gem array from the g[][] diamensions
    gemArray = new Gem[g.length][g[0].length];
    
    // For loop to fill in the "gemArray" with the correct gems from g[][] number representation
    for(int i = 0; i < g.length; i++){
      for(int ii = 0; ii < g[0].length; ii++){
        
        if(g[i][ii] <= -1){
          gemArray[i][ii] =  Gem.makeEmpty();
          continue;
        }
        
        gemArray[i][ii] =  new Gem(g[i][ii]);
      }
    }
  }
  
  
  // Make a deep copy of this Board. Requires that the 2D array of
  // gems be copied entirely to prevent shallow references to the same
  // arrays.  One of the required constructors is useful for this method.
  public Board clone(){
    
    // String "temp" calles this boards "saveSring()" method to be able to later invoke the "fromSaveString"
    // Board methed to save time.
    String temp = this.saveString();
    
    // "clone1" is used to make a board that shares no references with the current board 
    Board clone1 = this.fromSaveString(temp);
    
    // for loop to go through and set correct flags on the new cloned Board
    for(int i = 0; i < gemArray.length; i++){
      for(int ii = 0; ii < gemArray[0].length; ii++){
        
        if(gemArray[i][ii].flagged() == true){
          clone1.gemAt(i,ii).setFlag();
        }
        
      }
    }
    return clone1;
  }
  
  // Access the number of rows and columns. Assumes all rows have equal length.
  public int getRows(){
    return this.row;
  }
  public int getCols(){
    return this.col;
  }
  
  // True if the given position is in bounds and contains a gem which
  // can be removed. False if the row/col is out of bounds or the
  // board is empty at the specified position.
  public boolean validGemAt(int r, int c){
    
    // uUes Three if statements to check all three conditions and make sure the gem at r and c is valid
    if(r >= this.row || c >= this.col || c < 0 || r < 0){
      return false;
    }
    if(this.gemAt(r,c).isFilled() == true){
      return true;
    }
    if(this.gemAt(r,c).isEmpty() == true){
      return false;
      
    }
    return true;
  }
  
  
  // Return true if at least one gem exists on the board. False if all
  // board positions are empty.
  public boolean hasValidGem(){
    
    // "count" keeps track of all the valid Gems on the board
    int count = 0;
    
    // Loop Through this Boards 2d "gemArray" to count all valid gems 
    for(int i = 0; i < gemArray.length; i++){
      for(int ii = 0; ii < gemArray[0].length; ii++){
        if(this.validGemAt(i,ii) == true){
          count = count + 1;
        }
      }
    }
    // Checks to make sure the condition of at least one valid gem on the baord is true
    // by making sure "count" is greater then 0
    if(count > 0){
      return true;
    }
    return false;
  }
  
  // Retrieve gem at given location. Do not do bounds checking; out of
  // bounds positions should automatically raise an
  // IndexOutOfBoundsException.
  public Gem gemAt(int i, int j){
    return this.gemArray[i][j];
  }
  
  // Clear all flags of gems on the board by invoking their
  // clearFlag() method.
  public void clearFlags(){
    
    // Loop Through the 2d "gemArray" clearing all flags one at a time.
    for(int i = 0; i < gemArray.length; i++){
      for(int ii = 0; ii < gemArray[0].length; ii++){
        gemArray[i][ii].clearFlag();
      }
    }
  }
  
  // Any gem flagged for removal will be removed.  Blocks that should
  // "fall" will do so and columns that should shift left do so.
  // Example: The board below has 4 Gems marked with X's which are
  // flagged.
  // You may wish to write some private helper methods to help break
  // this task down into manageable chunks.
  public void doRemovals(){
    
    // Initial double for loop to clear all flagged gems
    for(int i = 0; i < gemArray.length; i++){
      for(int ii = 0; ii < gemArray[0].length; ii++){
        if(this.gemAt(i,ii).flagged() == true){
          this.gemArray[i][ii] = Gem.makeEmpty();
        }
      }
    }
    // After all flagged gems have been removed make calls to the helper methods "collumgemsmove()"
    // which in turn first calls "dropgems()" dropping all the gems first then "collumgemsmove()" using 
    // the returned Interger ArrayList from "dropgems()" moves all the columns to the left
    this.columgemsmove(this.dropgems());
    
  }
  
  
  // "dropgems" is a helper method that "doRemovals" calls to move all the gems down after
  // all Flagged gems have been removed returns an ArrayList of Integer locations of empty columns
  public ArrayList<Integer> dropgems(){
    
    //Declare an Array list to hold the spots that there are empty columns, I used a list becuase it had
    // to Grow and I wanted to advoid making more loops
    ArrayList<Integer> places = new ArrayList<Integer>();
    
    // Created two Gem arrays "temp" and "temp2" to hold the new locations of the Gems that would be 
    // cloned by going down each row by column
    Gem [] temp = new Gem[this.gemArray.length];
    Gem [] temp2 = new Gem[this.gemArray.length];
   // "count4" is used to keep track of how many empty gems there are
    int count4 = 0;
   // "count" is used to incement the while loop
    int count = 0;
   // "count 2 is used fill in "temp" and "temp2" gems only when they are not empty to 
   // shift all the spaces by row to the top and all values to the bottom to make it look like
   // the gems are falling 
    int count2 = 0;
    
   // this is the top for loop to keep track of which row postion is being used its backawrds
    // instead of going from the "gemArray" length it grabs the actual rows lenth of "gemArray"
    for(int i = 0; i < gemArray[0].length; i++){     
    
      // Nested while loop to go through the the 2d array by each spot in the rows of "gemArray" 
      while(count < this.gemArray.length){
        
       // Makes sure each value in "temp" and"temp2" is a valid gem
        temp[count] = Gem.makeEmpty();
        temp2[count] = Gem.makeEmpty();
        
       // if the gem at "gemArray[count][i]" is an actual gem and not empty put it in "temp" and
       // "temp2" representation of the row its supposed to be in 
        if( this.gemArray[count][i].isEmpty() == false){
          temp[count2] = this.gemArray[count][i].clone();
          temp2[count2] = this.gemArray[count][i].clone();
          count2 = count2 + 1;
        }
        // keep track of how many empty gems are in a row to see if its an empty column
        if( this.gemArray[count][i].isEmpty() == true){
          count4 = count4 + 1;
        }
        // check to see if "count4" is equal to the 2d "gemArray", if true then
        // there is an empty column and add and int that reprensents the spot that 
        // the empty colmn is at 
        if(count4 == this.gemArray.length){
          places.add(i);
        }
        
        // Increment for the while loop
        count = count + 1;
        
      }
      
      // this for loop is inside the top loop i and is used to switch all the values 
      // inside "temp" to the correct locations
      for(int iii= 0; iii < (this.gemArray.length)-count4; iii++){
        temp[iii] = temp2[((this.gemArray.length-1)-count4)-iii].clone();
      }
      // Start for postion "temp"
      int count3 = 0;
      
      // this for loop is inside the top loop i and finally changes all the values by column in the 
      // 2d "gemArray" to the correct fallen gems spots by copying all the gems from the Array "temp"
      for(int ii =this.gemArray.length-1; ii >= 0 ; ii--){
        this.gemArray[ii][i] = temp[count3].clone();
        count3 = count3 +1;
      }
      // reset all counters to go through the entire process again untill all columns have been replaced
      count4 = 0;
      count2 = 0;
      count = 0;
      count3 = 0;
    }
    // This is outside the main loop and returns the ArrayList "places" for "columgemsmove()" to use 
    // as so it can find the empty columns
    return places;
  }
  
  
  // "columgemsmove()" is the final helper method step that "doRemoval()" calls after "dropgems()" is called
  // it moves all of the actual spaces in each item in the "gemArray" to the left if there is an empty
  // column. It uses loccount to know what positons to move to the left 
  public void columgemsmove(ArrayList<Integer> loccount){
   
    // Declare a Gem Array "temp" to hold the new positions of the gems that are moved to the left
    // this Arrray will be assinged to the row  in "gemArray"
    Gem [] temp = new Gem[this.gemArray[0].length];
    
    // "count" is used to increment the while loop
    int count = 0;
    
    // "count2"  is used to look at the spots in the gemArray
    int count2 = 0;
    
    //  Since we ae moving all the elements in each column of gemArray to the left if it is
    // and an empty column "spot" is used to see if the current columns spot in in the "loccount"
    // to see of it needs to be moved.
    boolean spot = false;
    
    // top for loop, it will be marking what row we are on
    for(int i = 0; i < gemArray.length; i++){  
      
      // while loop to grab the columns value using the correct row from i
      while(count < this.gemArray[0].length){
        
        // set spot to false so we can do the correct amount of loops
        spot = false;
        
        // for loop to look through "loccount" and see if the current column is empty
        for(int ii = 0; ii < loccount.size(); ii++){
          if(count == loccount.get(ii)){
            spot = true;
          }
        }
        
       // fill each gem in "temp' so there are no null pointers 
        temp[count] = Gem.makeEmpty();
        
        // Two if statments to see if the current spot in temp should be moved if its an part of an
        // empty column
        if( spot == true){
          count = count + 1;
          continue;
        }
        if( spot == false){
          temp[count2] = this.gemArray[i][count].clone();
          count2 = count2 + 1;
        }
        
        // while loop increment
        count = count + 1;
        
      }
      
      // this for loop reasigns all the values in "temp" to the correct row in "gemArray" 
      for(int ii = 0; ii < this.gemArray[0].length; ii++){
        this.gemArray[i][ii] = temp[ii].clone();
      }
      
      // Reset all counters
      count2 = 0;
      count = 0;
      spot = false;
    }
  }
  
  
  // Convert to a simple saveable string. This string should have each
  // gem space separated for easy reading with Scanner. Empty
  // locations on the board should be denoted by a period (.) as in
  // the following sample:
  //
  // . . 4 . . 
  // . 10 6 8 2 
  // . 5 1 5 9 
  // 2 5 5 8 4 
  // 5 9 4 9 5 
  // 
  // Each line ends with a newline (\n) character.
  public String saveString(){
    
    // Two Strings, "part" is the small portion that will be added to "full" over and over to build
    // the full String
    String full = "";
    String part = "";
    
    // Basic for loops to go through the 2d "gemArray" and build a String from each gem
    for(int i = 0; i < gemArray.length; i++){
      for(int ii = 0; ii < gemArray[0].length; ii++){
        if(gemArray[i][ii].isEmpty() == true){
          part = part + "." + " ";
          continue;
        }
        
        // check to see if the gem is flagged an make a String representation of it
        if(gemArray[i][ii].flagged() == true){
          part =  part +gemArray[i][ii].kindString()+"*"+ " ";
          continue;
        }
        
        // add the String representation of the gem into part
        part =  part +gemArray[i][ii].kindString()+ " ";
      }
      
      // get rid of the last space and add a \n char to part then add it to full
      part.trim();
      part = part + "\n";
      full = full + part;
      part= "";
    }
    return full;
  }
  
  // Create a board from the given string.  The format accepted should
  // be identical to what is produced by saveString(). Parsing this
  // string will require two passes through the string, first to count
  // the size, then to read the gems and spaces.
  public static Board fromSaveString(String s){
   
    // Declare int "rows" and "cols" so the scanners can give them the correct vaules of each String board 
    int rows = 0;
    int cols = 0;
    
    //  "temp" will be used to hold the lines that each scanner parses over
    String temp = "";
    // Three scanner, "first" is used to find how many row/cols there are "second" is used
    // to find each gem and third I had to use becuase if the number was bigger then a one digit number such 11
    // it would use it as an extra item for length.
    Scanner first = new Scanner(s);
    Scanner second = new Scanner(s);
    Scanner third;
    
    // parse through "s" and to see how many columns and rows will need to be built
    while(first.hasNextLine()){
      temp = first.nextLine();
      rows = rows + 1;
    }
    
    //  Use the third scanner to go through each token in temp to see how many columns will be needed
    third = new Scanner(temp);
    while(third.hasNext() == true){
      cols = cols + 1;
      third.next();
    }
    
    third.close();
    first.close();
    // Make a new int Array and build it up with he the ints from the String s
    int [][] placehold = new int[rows][cols];
    for(int i = 0; i < placehold.length; i++){
      for(int ii = 0; ii < placehold[0].length; ii++){
        if(second.hasNextInt() == true){
          placehold[i][ii] =  second.nextInt();
          continue;
        }
        if(second.hasNext() == true){
          placehold[i][ii] =  -1;
          second.next();
        }
      }
    }
    
    second.close();
    
    // make a new board from the int[] "placehold"
    Board stringBoard = new Board(placehold);
    return stringBoard;
  }
  
  
  
  
  
  // Implementation Provided. Fancy display string version of the
  // board; assumes gem kinds fit in 2 chars. Flagged gems have an
  // asterisk put to the right of them.
  public String toString(){
    StringBuilder sb = new StringBuilder();
    // Col header
    sb.append("   ");
    for(int j=0; j<this.getCols(); j++){
      sb.append(String.format("%3d",j));
    }
    sb.append("\n");
    sb.append("   ");
    for(int j=0; j<this.getCols(); j++){
      sb.append("---");
    }
    sb.append("\n");
    // Main body of board
    for(int i=0; i<this.getRows(); i++){
      sb.append(String.format("%2d|",i));
      for(int j=0; j<this.getCols(); j++){
        Gem g = this.gemAt(i,j);
        char flag = g.flagged() ? '*' : ' ';
        if(j==0){
          sb.append(String.format("%3s%c",g.kindString(),flag));
        }
        else{
          sb.append(String.format("%2s%c",g.kindString(),flag));
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
