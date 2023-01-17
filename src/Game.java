public class Game {
    private Room currentRoom;
    public Game(){

    }
    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        Room movieTheatre = new Room("The movie theatre is an old building with only one movie showing. It’s west of In n Out and north of the Mall.");
        Room mall = new Room("This is an abandoned mall with two floors. The mall is south of the movie theatre and west of Handel’s.");
        Room inNOut = new Room ("A fast food restaurant where you can get a chocolate milkshake. It is east of the Movie Theatre, west of the book store, and north of Handel’s.");
        Room handels = new Room("Handel’s sells all types of ice cream and milkshakes. It is south of In n Out and east of the Mall.");
        Room bookstore = new Room("The bookstore has three levels. It is east of In-N-Out.");
    }
    public void play(){
        printWelcome();
        boolean finished = false;
        while(!finished) {

        }
        System.out.println("Thanks for playing!");
    }
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a garden maze, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance.");
        System.out.println();
        System.out.println("We will print a long room description here.");
    }
}
