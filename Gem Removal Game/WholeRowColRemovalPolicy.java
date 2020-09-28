// This policy picks a gem on the board and removes all gems
// that are of the same Gem Kind using for loops to go all the way through each row and
// column to its left,right,up and down. it stops if the next Gem is not of the same Kind
public class WholeRowColRemovalPolicy implements RemovalPolicy{
 // "keepscore" gets plused if there is a gem found at any of the Adjacent gems matching the picked gem
  private int keepscore = 0;
  public String description(){
    return "Adjacent gems in whole row/column will be removed";
  }
  
  // Mark all gems connected to the gem at row/col on the given board
  public void flagConnectedGems(int row, int col, Board b) {
    // Basic check to make sure the starting Gem is valid
    if(!b.validGemAt(row,col)){
      String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
      throw new RuntimeException(msg);
    }
    b.clearFlags();
    Gem center = b.gemAt(row,col);
    center.setFlag();
    // "keepscore is assumed to be at least one as the picked Gem is valid
    keepscore = keepscore + 1;
    
    // Checks the whole row down from the picked Gem for the same kind of gem if true "keepscore" +1
    // if the Gem is empty or not of the same kind it breaks the loop to advoid adding onto "keepscore"
    for(int i = row; i < b.getRows(); i--){
      if( b.validGemAt(i-1,col) == false){
        break;
      }
      if( b.validGemAt(i-1,col) == true){
        if(center.sameKind(b.gemAt(i-1,col)) == true){
          b.gemAt(i-1,col).setFlag();
          keepscore = keepscore + 1; 
        }else{break;}
      }
    }
    
     // Checks the whole row up from the picked Gem for the same kind of gem if true "keepscore" +1
    // if the Gem is empty or not of the same kind it breaks the loop to advoid adding onto "keepscore"
    for(int i = row; i < b.getRows(); i++){
      if( b.validGemAt(i+1,col) == false){
        break;
      }
      if( b.validGemAt(i+1,col) == true){
        if(center.sameKind(b.gemAt(i+1,col)) == true){
          b.gemAt(i+1,col).setFlag();
          keepscore = keepscore + 1; 
        }else{break;}
      }
    }
    
     // Checks the whole Column left from the picked Gem for the same kind of gem if true "keepscore" +1
    // if the Gem is empty or not of the same kind it breaks the loop to advoid adding onto "keepscore"
    for(int i = col; i < b.getCols(); i--){
      if( b.validGemAt(row,i-1) == false){
        break;
      }
      if( b.validGemAt(row,i-1) == true){
        if(center.sameKind(b.gemAt(row,i-1)) == true){
          b.gemAt(row,i-1).setFlag();
          keepscore = keepscore + 1; 
        }else{break;}
      }
    }
    
     // Checks the whole Column left from the picked Gem for the same kind of gem if true "keepscore" +1
    // if the Gem is empty or not of the same kind it breaks the loop to advoid adding onto "keepscore"
    for(int i = col; i < b.getCols(); i++){
      if( b.validGemAt(row,i+1) == false){
        break;
      }
      if( b.validGemAt(row,i+1) == true){
        if(center.sameKind(b.gemAt(row,i+1)) == true){
          b.gemAt(row,i+1).setFlag();
          keepscore = keepscore + 1; 
        }else{break;}
      }
    }
  }
  
  // Determine the score for removing the gem at row/col; do not
  // actually perform the removal on the board, at most flag the gems
  // for removal.  The numeric score is the square of the number of
  // gems removed.
  public int scoreMove(int row, int col, Board b) {
    if(!b.validGemAt(row,col)){
      String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
      throw new RuntimeException(msg);
    }
    // Reset "keepScore" for the next call of score move
    this.keepscore = 0;
    // Use "flagconnections()" to advoid reduntant calls to scoreMove 
    this.flagConnectedGems(row,col,b);
    // Return the sqaure of the current amount of gems that could be removed form this move
    return (keepscore*keepscore) ;
  }    
  
  public String toString(){ return "WholeRowColRemovalPolicy"; }
  public String saveString(){ return "WholeRowColRemovalPolicy"; }
}

