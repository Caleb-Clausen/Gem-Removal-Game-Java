// This policy picks a gem on the board and removes each Adjacent gem to its left,right,up and down
// that are of the same Gem Kind
public class AdjacentRemovalPolicy implements RemovalPolicy{
  // "keepscore" gets plused if there is a gem found at any of the Adjacent gems matching the picked gem
  private int keepscore = 0;
  public String description(){
    return "Adjacent gems of the same kind will be removed";
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
    // Checks to the left the picked Gem for the same kind of gem if true "keepscore" +1
    if( b.validGemAt(row,col-1) == true){
      if(center.sameKind(b.gemAt(row,col-1)) == true){
        b.gemAt(row,col-1).setFlag();
        keepscore = keepscore + 1; 
      }
         }

     // Checks to the right the picked Gem for the same kind of gem if true "keepscore" +1
       if( b.validGemAt(row,col+1) == true){
      if(center.sameKind(b.gemAt(row,col+1)) == true){
        b.gemAt(row,col+1).setFlag();
        keepscore = keepscore + 1; 
      }
         }
     // Checks below the picked Gem for the same kind of gem if true "keepscore" +1     
        if( b.validGemAt(row-1,col) == true){
      if(center.sameKind(b.gemAt(row-1,col)) == true){
        b.gemAt(row-1,col).setFlag();
       keepscore = keepscore + 1; 
      }
         }
       // Checks above the picked Gem for the same kind of gem if true "keepscore" +1          
             if( b.validGemAt(row+1,col) == true){
      if(center.sameKind(b.gemAt(row+1,col)) == true){
        b.gemAt(row+1,col).setFlag();
        keepscore = keepscore + 1; 
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
    return (keepscore* keepscore) ;
  }    
    
  public String toString(){ return "AdjacentRemovalPolicy"; }
  public String saveString(){ return "AdjacentRemovalPolicy"; }
}
    
