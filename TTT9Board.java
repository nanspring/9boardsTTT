import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TTT9Board {
	TTTBoard b1;
	TTTBoard b2;
	TTTBoard b3;
	TTTBoard b4;
	TTTBoard b5;
	TTTBoard b6;
	TTTBoard b7;
	TTTBoard b8;
	TTTBoard b9;
	HashMap<Integer,TTTBoard> TTTMap;
	public TTT9Board(String player){
		b1=new TTTBoard(player);
		b2=new TTTBoard(player);
		b3=new TTTBoard(player);
		b4=new TTTBoard(player);
		b5=new TTTBoard(player);
		b6=new TTTBoard(player);
		b7=new TTTBoard(player);
		b8=new TTTBoard(player);
		b9=new TTTBoard(player);
		this.TTTMap=new HashMap<Integer,TTTBoard>();
		this.TTTMap.put(1, b1);
		this.TTTMap.put(2, b2);
		this.TTTMap.put(3, b3);
		this.TTTMap.put(4, b4);
		this.TTTMap.put(5, b5);
		this.TTTMap.put(6, b6);
		this.TTTMap.put(7, b7);
		this.TTTMap.put(8, b8);
		this.TTTMap.put(9, b9);
		
	}
	public void TTT9PrintBoard(){
		System.err.println("_____________________");
		for(int i=1;i<=9;i=i+3){
			System.err.printf("%s\n%s\n%s\n%s\n",
					TTTMap.get(i).board[0]+" "+TTTMap.get(i).board[1]+" "+TTTMap.get(i).board[2]+" | "+TTTMap.get(i+1).board[0]+" "+TTTMap.get(i+1).board[1]+" "+TTTMap.get(i+1).board[2]+" | "+TTTMap.get(i+2).board[0]+" "+TTTMap.get(i+2).board[1]+" "+TTTMap.get(i+2).board[2],
					TTTMap.get(i).board[3]+" "+TTTMap.get(i).board[4]+" "+TTTMap.get(i).board[5]+" | "+TTTMap.get(i+1).board[3]+" "+TTTMap.get(i+1).board[4]+" "+TTTMap.get(i+1).board[5]+" | "+TTTMap.get(i+2).board[3]+" "+TTTMap.get(i+2).board[4]+" "+TTTMap.get(i+2).board[5],
					TTTMap.get(i).board[6]+" "+TTTMap.get(i).board[7]+" "+TTTMap.get(i).board[8]+" | "+TTTMap.get(i+1).board[6]+" "+TTTMap.get(i+1).board[7]+" "+TTTMap.get(i+1).board[8]+" | "+TTTMap.get(i+2).board[6]+" "+TTTMap.get(i+2).board[7]+" "+TTTMap.get(i+2).board[8],
					"_____________________");
		}
	}
	public void action9(String player,int boardNum, int index){
		index--;
		//System.out.println("index at action 9 "+index);
		TTTMap.get(boardNum).board[index]=player;
	}
	public boolean terminalStateTTT9(){
		for(int i:TTTMap.keySet()){
			if(TTTMap.get(i).terminalState()){
				return true;
			}
		}
		return false;
	}
	public boolean isValid(TTTBoard b){
		List<String>stringList=Arrays.asList(b.board);
		if(b.finalState()){
			return false;
		}
		if((new HashSet<>(stringList).size()==1)){
			String temp1=(String) new HashSet<>(stringList).toArray()[0];
			if(temp1.equals("_")){
				return false;//if the board is empty, it is not valid
			}
		}
		return true;
	}
	public double h_Utility(String player){
		double utility=0;
		if(terminalStateTTT9()){
			for(int i:this.TTTMap.keySet()){
				if(TTTMap.get(i).terminalState()){
					return (TTTMap.get(i).utility(player)*100);
				}
			}
		}
		else{
			List<TTTBoard> boardList=new ArrayList<TTTBoard>();
			for(int i:this.TTTMap.keySet()){
				if(isValid(TTTMap.get(i))){
					boardList.add(TTTMap.get(i));
				}
			}
			for(TTTBoard b:boardList){
				double value=b.value(player);
				utility=utility+value;
				}
		}
		
		return utility;
		
	}
	public void copy9board(TTT9Board b){
		this.b1.copy(b.b1);
		this.b2.copy(b.b2);
		this.b3.copy(b.b3);
		this.b4.copy(b.b4);
		this.b5.copy(b.b5);
		this.b6.copy(b.b6);
		this.b7.copy(b.b7);
		this.b8.copy(b.b8);
		this.b9.copy(b.b9);
		this.TTTMap=new HashMap<Integer,TTTBoard>();
		this.TTTMap.put(1, b1);
		this.TTTMap.put(2, b2);
		this.TTTMap.put(3, b3);
		this.TTTMap.put(4, b4);
		this.TTTMap.put(5, b5);
		this.TTTMap.put(6, b6);
		this.TTTMap.put(7, b7);
		this.TTTMap.put(8, b8);
		this.TTTMap.put(9, b9);
	}
}
