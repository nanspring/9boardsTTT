import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game9board {
	TTT9Board board9;
	public Game9board(TTT9Board b){
		this.board9=b;
	}
	public TTT9Board nextState(TTT9Board currentState,int boardNum,int index,String player){
		TTT9Board newState=new TTT9Board(player);
		newState.copy9board(currentState);
		newState.TTTMap.get(boardNum).board[index]=player;
		return newState;
	}
	public double alphabeta(TTT9Board b,int depth,int bNum,double alpha,double beta,String player,String oponent,String currentTerm){
		if(depth==0||b.terminalStateTTT9()){
			return b.h_Utility(player);
		}
		if(currentTerm.equals(player)){
			double v=Integer.MIN_VALUE;
				for(int index:b.TTTMap.get(bNum).actions(b.TTTMap.get(bNum))){
					TTT9Board nextState=nextState(b,bNum,index,player);	
					currentTerm=oponent;
					v=Math.max(v, alphabeta(nextState,depth-1,index+1,alpha,beta,player,oponent,currentTerm));
					alpha=Math.max(alpha, v);
					if(beta<=alpha){
						break;
					}
				}				
			
			return v;
		}else{
			double v=Integer.MAX_VALUE;
				for(int index:b.TTTMap.get(bNum).actions(b.TTTMap.get(bNum))){
					TTT9Board nextState=nextState(b,bNum,index,oponent);
					currentTerm=player;
					v=Math.min(v, alphabeta(nextState,depth-1,index+1,alpha,beta,player,oponent,currentTerm));
					beta=Math.min(beta, v);
					if(beta<=alpha){
						break;
					}
				}
			
			return v;
		}
	}
	public String computerPlayer(String humanplayer){
		String computer;
		if(humanplayer.equals("X")){
			computer="O";
		}else{
			computer="X";
		}
		return computer;
	}
	public static String caseInsensitive(String player){//human player can choose x or X. Capital letter does not matter at all
		if(player.equals("x")){
			player="X";
		}else if(player.equals("o")){
			player="O";
		}
		return player;
	}
	public boolean validInput(int input, TTTBoard b){
		try{
			if((input)<=9&&(input)>=1&&b.board[(input)-1].equals("_")){
				return true;
			}
			else{
				return false;
			}
		}catch(NumberFormatException e){
			return false;
		}
	}
	public static void main(String[] args){
		
		Scanner scanner=new Scanner(System.in);
		System.err.println("Which player do you want to play? X or O ? please type in capital letter");
		String humanplayer=scanner.nextLine();	
		humanplayer=caseInsensitive(humanplayer);
		while(!humanplayer.equals("X")&&!humanplayer.equals("O")){
			System.err.println(humanplayer+" please enter the right input");
			humanplayer=scanner.nextLine();
			humanplayer=caseInsensitive(humanplayer);
		}
		TTT9Board board9=new TTT9Board("X");
		board9.TTT9PrintBoard();
		Game9board game=new Game9board(board9);
		String computer=game.computerPlayer(humanplayer);
		String currentPlayer;
		int boardNum=-1;
		if(humanplayer.equals("X")){
			currentPlayer=humanplayer;
		}else{
			currentPlayer=computer;
			Random rand=new Random();
			boardNum=rand.nextInt(9)+1;
		}
		
		while(game.board9.terminalStateTTT9()==false){
			if(currentPlayer.equals(humanplayer)){
				System.err.println("your move");
				System.err.println("enter the board number ");
				int entervalue=scanner.nextInt();
				if(boardNum==-1){
					boardNum=entervalue;
				}
				while(entervalue!=boardNum||entervalue<1||entervalue>9){
					System.err.println("please enter the right board number");
					entervalue=scanner.nextInt();
					boardNum=entervalue;
				}
				System.err.println("index num");
				int index=scanner.nextInt();
				while(game.validInput(index, game.board9.TTTMap.get(boardNum))==false){
					System.err.println("please enter the right input");
					index=scanner.nextInt();					
				}
				game.board9.action9(humanplayer, boardNum, index);
				game.board9.TTT9PrintBoard();
				boardNum=index;
				currentPlayer=computer;
			}else if(currentPlayer.equalsIgnoreCase(computer)){
				System.err.println("board num "+boardNum);
				int nextAction=-1;
				TTT9Board nextBoard=new TTT9Board(computer);
				double v=-100000;
				int actualNum=-1;
				for(int a:game.board9.TTTMap.get(boardNum).actions(game.board9.TTTMap.get(boardNum))){
					TTT9Board nextState=new TTT9Board(computer);
					nextState.copy9board(game.board9);
					nextState.TTTMap.get(boardNum).board[a]=computer;
					double value=game.alphabeta(nextState, 6, a+1, Integer.MIN_VALUE, Integer.MAX_VALUE, computer, humanplayer,humanplayer);
					if(value>v){
						v=value;
						actualNum=a+1;
						nextBoard.copy9board(nextState);
						for(int i:nextBoard.TTTMap.keySet()){
							for(int j=0;j<nextBoard.TTTMap.get(i).board.length;j++){
								if(!game.board9.TTTMap.get(i).board[j].equals(nextBoard.TTTMap.get(i).board[j])){
									nextAction=j+1;
									//System.err.println("next action: "+nextAction);
								}
							
							}
						}
					}
				}
				System.err.println("this is what the computer wants to move");
				System.out.println(boardNum+" "+nextAction);
				boardNum=nextAction;
				
				nextBoard.TTT9PrintBoard();
				game.board9.copy9board(nextBoard);			
			currentPlayer=humanplayer;
		}
		}
		for(int i:board9.TTTMap.keySet()){
			if(board9.TTTMap.get(i).goalState(computer)){
				System.err.println("Ooooooopse.......computer win!");
				break;
			}else if(board9.TTTMap.get(i).goalState(humanplayer)){
				System.err.println("you win!");
				break;
			}else if(board9.TTTMap.get(i).finalState()){
				System.err.println("wow..its a tie");
				break;
			}
		}
	}

}
