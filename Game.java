import java.util.List;
import java.util.Scanner;

public class Game {
	TTTBoard board;
	String player1,player2;
	public Game(TTTBoard board){
		this.board=board;
	}
	public TTTBoard nextState(TTTBoard state,int nextMove,String player){		
		TTTBoard newState=new TTTBoard(player);	
		newState.copy(state);
		newState.board[nextMove]=player;
		return newState;
	}
	public String oponent(String player){
		String oponent;
		if(player.equals("X")){
			oponent="O";
		}else{
			oponent="X";
		}
		return oponent;
	}
	public int max_value(TTTBoard state,String player,String oponent){
		if(state.terminalState()){
			return state.utility(player);
		}
		int v=Integer.MIN_VALUE;
		for(int a:state.actions(state) ){
			TTTBoard s=nextState(state, a,player);
			v=Math.max(v, min_value(s,player,oponent));
		}
		return v; 
	}
	public int min_value(TTTBoard state,String player,String oponent){
		if(state.terminalState()){
			return state.utility(player);
		}
		int v=Integer.MAX_VALUE;
		for(int a:state.actions(state)){
			TTTBoard s=nextState(state, a,oponent);			
			v=Math.min(v, max_value(s,player,oponent));
		}
		return v;
	}
	public int minimax(TTTBoard state,String player,String oponent){
		int v=min_value(state,player,oponent);
		return v;
	}
	public boolean validInput(String input, TTTBoard b){
		try{
			if(Integer.parseInt(input)<=9&&Integer.parseInt(input)>=1&&b.board[Integer.parseInt(input)-1].equals("_")){
				return true;
			}
			else{
				return false;
			}
		}catch(NumberFormatException e){
			return false;
		}
	}
	public static String caseInsensitive(String player){
		if(player.equals("x")){
			player="X";
		}else if(player.equals("o")){
			player="O";
		}
		return player;
	}
	public static void main(String[] args){
		while(true){
		Scanner scanner=new Scanner(System.in);
		System.err.println("The index of the board is showed as below: ");
		System.err.println("----------");
		System.err.println("| 1 2 3 |");
		System.err.println("| 4 5 6 |");
		System.err.println("| 7 8 9 |");
		System.err.println("----------");
		System.err.println("Do you want to play X or O?");
		System.err.println("X will play first");
		System.err.println("Type 'X' or 'O'");
		String humanPlayer=scanner.nextLine();
		humanPlayer=caseInsensitive(humanPlayer);
		if(!humanPlayer.equals("X")&&!humanPlayer.equals("O")){
			System.err.println("Please enter a correct output. Input 'X' or 'O'");
			humanPlayer=scanner.nextLine();
			humanPlayer=caseInsensitive(humanPlayer);
		}
		TTTBoard b=new TTTBoard(humanPlayer);
		String comPlayer;
		Game game=new Game(b);
		String currentPlayer;
		if(humanPlayer.equals("X")){
			comPlayer="O";
		}else{
			comPlayer="X";
		}
		if(humanPlayer.equals("X")){
			currentPlayer=humanPlayer;
		}else{
			currentPlayer=comPlayer;
		}
		while(!b.terminalState()){
			if(currentPlayer.equals(humanPlayer)){
				System.err.println("===================================");
				System.err.println("Your turn now!");
				System.err.println("Current board: ");
				b.printBoard();
				System.err.println("Please choose an index from 1 to 9");
				String input=scanner.nextLine();
				while(!game.validInput(input,b)){
					System.err.println("Please put a valid input. Input integer 1-9");
					input=scanner.nextLine();
				}
				b.action(humanPlayer, Integer.parseInt(input));
				b.printBoard();
				currentPlayer=comPlayer;
			}else if(currentPlayer.equals(comPlayer)){
				System.err.println("====================================");
				System.err.println("Computer turn now!");
				System.err.println("Current board: ");
				b.printBoard();
				List<Integer> actionLists=b.actions(b);
				int max=Integer.MIN_VALUE;
				TTTBoard nextState=new TTTBoard(comPlayer);
				int nextAction=-1;
				for(int a:actionLists){
					TTTBoard aBoard=new TTTBoard(comPlayer);
					aBoard.copy(b);
					aBoard.board[a]=comPlayer;
					String oponent=humanPlayer;
					String player=comPlayer;
					int v=game.minimax(aBoard,player,oponent);
					if(v>max){
						max=v;
						nextState.copy(aBoard);
						for(int i=0;i<nextState.board.length;i++){
							if(!nextState.board[i].equals(b.board[i])){
								nextAction=i+1;
							}
						}
					}
				}
				b.copy(nextState);
				System.err.println("this is what computer want to move!");
				System.out.println(nextAction);
				System.err.println();
				b.printBoard();
				currentPlayer=humanPlayer;
			}
		}
			if(b.utility(humanPlayer)==1){
				System.err.println("WOW, YOU WIN!!!");
			}else if(b.utility(humanPlayer)==0){
				System.err.println("This is a fair game");
			}else{
				System.err.println("Ooooops...computer wins!");
			}
	}
	}
	}
		



