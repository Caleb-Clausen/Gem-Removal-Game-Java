// Import all java.util* but note Only javaArrays are being used from the pakage
import java.util.*;

// Greedy player is an AI that only selects the best move one at a time until there are no more
// Gems left
public class GreedyPlayer implements Player{
  
//Declare a Private Coord Array "holder" to hold all the coords that have the highest scoring potential
   private ArrayList<Coord> holder = new ArrayList<>();
  
  public void executeMove(Game game) {
   
    // Int "Bestscore" will hold the best scoreand and "comScore" holds the best score of the current move 
    int bestScore = 0;
    int comScore = 0;
    Board board = game.getBoard();
    int col = (board.getCols());
    int row = (board.getRows());
    
    // Basic for loop to go through and grab each gem and call the score policy on it
    // it stores the best opton in best score
    for(int i = 0; i < row; i++){
      for(int ii = 0; ii < col; ii++){
        if(board.gemAt(i,ii).isEmpty() == true){
          continue;
        }
        comScore = game.getPolicy().scoreMove(i,ii,board);
        if(comScore >= bestScore){
          bestScore = comScore;
        }
      }
    }
    
    // This for loop makes a Coord with the rows and col i and ii to keep track of where the best gem was located 
    for(int i = 0; i < row; i++){
      for(int ii = 0; ii < col; ii++){
        if(board.gemAt(i,ii).isEmpty() == true){
          continue;
        }
        if(game.getPolicy().scoreMove(i,ii,board) == bestScore){
          holder.add(new Coord(i,ii));
        }
      }
    }
    
    // For loop to choose the best Gem out of the holders Coords to call "removeGemAdjustScore()"
    for(int i = 0; i <  holder.size(); i++){
      if(holder.size() == 1){
        game.removeGemAdjustScore(holder.get(i).row,holder.get(i).col);
        break;
      }
      game.removeGemAdjustScore(holder.get(i).row,holder.get(i).col);
      break;
    }
    
    // Resets the holder and best score for the next move
    holder = new ArrayList<Coord>();
    bestScore = 0;
    
  }
  
  
  
  
  public String toString(){ return "GreedyPlayer"; }
  public String saveString(){ return "GreedyPlayer"; }
  
}