import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TTTBoard {
	String[] board;
	String player;
	public TTTBoard(String player){
		//initial state
		board=new String[9];
		this.player=player;
		Arrays.fill(board, "_");
	}
	public void printBoard(){
		System.err.println("_____");
		int index=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				System.err.print(this.board[index]+" ");
				index++;
			}
			System.err.println();
		}
		System.err.println("_____");
	}
	public List<Integer> actions(TTTBoard TTT){
		List<Integer> available=new ArrayList<Integer>();
		for(int i=0;i<TTT.board.length;i++){
			if(TTT.board[i].equals("_")){
				available.add(i);
			}
		}
		return available;
	}
	
	public void action(String player, int index){
		index--;
		board[index]=player;
	}
	public boolean goalState(String player){
		for(int i=0;i<=6;i=i+3){//check if the row are same
			List<String>strings=Arrays.asList(this.board[i],this.board[i+1],this.board[i+2]);
			if(new HashSet<>(strings).size()==1){
				String temp=(String) new HashSet<>(strings).toArray()[0];
				if(temp.equals(player)){
					return true;
				}				
			}
		}
		for(int i=0;i<3;i++){//check if coloumn are same
			List<String>strings=Arrays.asList(this.board[i],this.board[i+3],this.board[i+6]);
			if(new HashSet<>(strings).size()==1){
				String temp=(String) new HashSet<>(strings).toArray()[0];
					if(temp.equals(player)){
						return true;
					}
			}
		}
		List<String>stringDiag1=Arrays.asList(this.board[0],this.board[4],this.board[8]);
		List<String>stringDiag2=Arrays.asList(this.board[2],this.board[4],this.board[6]);
		if(new HashSet<>(stringDiag1).size()==1){
			String temp1=(String) new HashSet<>(stringDiag1).toArray()[0];
			if(temp1.equals(player)){
				return true;
			}		
		}
		if(new HashSet<>(stringDiag2).size()==1){
			String temp2=(String) new HashSet<>(stringDiag2).toArray()[0];
			if(temp2.equals(player)){
				return true;
			}
			
		}
		return false;		
	}
	public boolean finalState(){
		List<String>s=Arrays.asList(this.board);
		if(new HashSet<>(s).contains("_")==false){
			return true;
		}
		return false;
	}
	public boolean terminalState(){
		if(goalState("X")){
			return true;
		}else if(goalState("O")){
			return true;
		}else if(finalState()){
			return true;
		}
		else{
			return false;
		}
	}
	public void copy(TTTBoard state){
		this.board=(state.board.clone());
		this.player=state.player;
	}
	public int utility(String player){
		String oponent;
		if(player.equals("X")){
			oponent="O";
		}else{
			oponent="X";
		}
		if(goalState(player)){
			return 1;
		}else if(goalState(oponent)){
			return -1;
		}else{
			return 0;
		}
	}
	public double value(String player){
		double good=0;
		double bad=0;
		if(this.board[4].equals(player)){
			good=good+2;
		}else if(!this.board[4].equals("_")){
			bad=bad-2;
		}
		
		for(String i:this.board){
			if(i.equals(player)){
				good++;
			}else if(!i.equals("_")){
				bad++;
			}
		}
		double odd;
		if(good>bad){
			if(bad==0){
				odd=good;
			}else{
				odd=good/bad;
			}
		}else if(good<bad){
			if(good==0){
				odd=-bad;
			}else{
				odd=-(bad/good);
			}
		}else{
			odd=0;
		}
		return odd;
	}
}