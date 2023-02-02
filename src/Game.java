public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    private Room movieTheatre;
    private Room mall;
    private Room inNOut;
    private Room handels;
    private Room bookstore;
    boolean wantToQuit = false;
    boolean finished = false;
    Item libraryCard = new Item();
    public Game(){
        parser = new Parser();
        player = new Player();
    }
    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        movieTheatre = new Room("The movie theatre is an old building that is west of In n Out and north of the Mall.", "The movie theatre has one movie showing at a time. There is a box of milk duds that have fallen on the ground.");
        mall = new Room("This is an abandoned mall. The mall is south of the movie theatre and west of Handel’s.", "The doors to the mall are boarded up, but there is a hole in the building. Once you enter, there are a pair of car keys on the floor, which will let you go to In-N-Out.");
        inNOut = new Room ("A fast food restaurant where you can get a chocolate milkshake. It is east of the Movie Theatre, west of the book store, and north of Handel’s.", "In-N-Out sells chocolate shakes, but they aren't always good. You can only enter when you drive.  They will not let you walk through the drive thru. You need some car keys.");
        handels = new Room("Handel’s sells all types of ice cream and sundaes. It is south of In n Out and east of the Mall.", "Handel’s has a very long line. If you wait in that long line you can grab the library card which you need to escape.");
        bookstore = new Room("The bookstore is huge. It is east of In-N-Out.", "You are in the bookstore. There is a long wall of bookshelves. You see a machine where you have to swipe your card in order to escape.");


        movieTheatre.setExit("south", mall);
        mall.setExit("north", movieTheatre);
        mall.setExit("east", handels);
        inNOut.setExit("west", movieTheatre);
        inNOut.setExit("east", bookstore);
        inNOut.setExit("south", handels);
        handels.setExit("west", mall);

        currentRoom = movieTheatre;


        Item milkDuds = new Item();
        Item carKeys = new Item();
        Item milkshake = new Item();


        int health = 3;

        movieTheatre.setItem("milkDuds", milkDuds);
        mall.setItem("carKeys", carKeys);
        inNOut.setItem("milkshake", milkshake);
        handels.setItem("libraryCard", libraryCard);
    }
    public void play(){
        printWelcome();

        while(!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println("Thanks for playing!");
    }
    private boolean processCommand(Command command){

        CommandWord commandWord = command.getCommandWord();

        switch(commandWord){
            case Unknown:
                System.out.print("I don't know what you mean.");
                break;

            case Help:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case Quit:
                wantToQuit = true;
                break;

            case Look:
                look(command);
                break;

            case Grab:
                grab(command);
                break;

            case Drop:
                drop(command);
                break;

            case Drive:
                drive(command);
                break;

            case Swipe:
                swipe(command);
                break;

        }
        return wantToQuit;
    }


    public void printHelp(){
        System.out.println("You are lost.");
        System.out.println("You are in a city.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    private void look(Command command){
        if(command.hasSecondWord()){
            System.out.print("You can't look at " + command.getSecondWord());
            return;
        }

        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }
    private void goRoom(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom ==null){
            System.out.println("You can't go there!");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }
    private void drive(Command command){
        if(!player.getInv().containsKey("carKeys")) {
            System.out.println("You need car keys to drive to In-N-Out.");
            currentRoom = movieTheatre;
            System.out.println("You are back in the movie theatre.");

        }
        else {

            handels.setExit("north", inNOut);
            bookstore.setExit("west", inNOut);
            movieTheatre.setExit("east", inNOut);
            if (!command.hasSecondWord()) {
                System.out.println("Drive where?");
                return;
            }
            String direction = command.getSecondWord();
            Room nextRoom = currentRoom.getExit(direction);
            if (nextRoom == null) {
                System.out.println("You can't drive there!");
            }
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getShortDescription());
            }
        }
    }
    public void drop(Command command){
            if (!command.hasSecondWord()) {
                System.out.println("Drop what?");
                return;
            }
            String key = command.getSecondWord();
            Item dropItem = player.getItem(key);
            if (dropItem == null) {
                System.out.println("You can't drop that!");
            }
            else {
                currentRoom.setItem(key, dropItem);
                System.out.println("You dropped " + key);
            }
    }
    private void swipe(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Swipe what?");
            return;
        }
        String key = command.getSecondWord();
        Item swipeItem = player.getItem(key);
        if (swipeItem != libraryCard){
            System.out.println("You can't swipe that!");
        }
        else{
            currentRoom.setItem(key, swipeItem);
            System.out.println("You swiped " + key);
            System.out.println("A hidden door opened and now you can escape this city. Good job!");
            wantToQuit = true;
        }
    }

    private void grab(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Grab what?");
            return;
        }
        String key = command.getSecondWord();
        Item grabItem = currentRoom.getItem(key);
        if (grabItem == null){
            System.out.println("You can't grab that!");
        }
        else{
            player.setItem(key, grabItem);
            System.out.println("You grabbed " + key);
            if(player.getInv().containsKey("milkshake")){
                System.out.println("The milkshake went bad. You died.");
                wantToQuit = true;
            }

        }
    }
    private boolean quit(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't quit " + command.getSecondWord());
            return false;
        }
        else{
            return true;
        }
    }
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a big city, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance.");
        System.out.println();
        System.out.println("Go through the city and swipe your library card at the book store to escape. (You need car keys to drive through In-N-Out.)");
    }
}
