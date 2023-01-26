public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
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
        Room movieTheatre = new Room("The movie theatre is an old building with only one movie showing. It’s west of In n Out and north of the Mall.", "The movie theatre has one glass door entrance into the concession area. There is a box of milk duds that have fallen on the ground.");
        Room mall = new Room("This is an abandoned mall. The mall is south of the movie theatre and west of Handel’s.", "The doors to the mall are boarded up. The south side of the building has already been broken into. Once you enter, there are stairs where you entered. There are a pair of car keys. There is an old car.");
        Room inNOut = new Room ("A fast food restaurant where you can get a chocolate milkshake. It is east of the Movie Theatre, west of the book store, and north of Handel’s.", "In-N-Out sells chocolate shakes that will give you stamina, but you can only use the drive thru.  They will not let you walk through the drive through. You need a car. The drive thru is one the east side of the building.");
        Room handels = new Room("Handel’s sells all types of ice cream and milkshakes. It is south of In n Out and east of the Mall.", "Handel’s has a very long line. If you give the worker a pair of car keys you can get a library card.  You can get the keys back if you give them milk duds.");
        Room bookstore = new Room("The bookstore is huge. It is east of In-N-Out.", "There is a long wall of bookshelves. You see a machine where you have to swipe your card.");

        movieTheatre.setExit("east", inNOut);
        movieTheatre.setExit("south", mall);
        mall.setExit("north", movieTheatre);
        mall.setExit("east", handels);
        inNOut.setExit("west", movieTheatre);
        inNOut.setExit("east", bookstore);
        inNOut.setExit("south", handels);
        handels.setExit("west", mall);
        handels.setExit("north", inNOut);
        bookstore.setExit("west", inNOut);

        currentRoom = movieTheatre;


        Item milkDuds = new Item();
        Item carKeys = new Item();
        Item milkshake = new Item();
        Item libraryCard = new Item();

        int health = 3;

        movieTheatre.setItem("Milk Duds", milkDuds);
        mall.setItem("car keys", carKeys);
        inNOut.setItem("milkshake", milkshake);
        handels.setItem("library card", libraryCard);
    }
    public void play(){
        printWelcome();
        boolean finished = false;
        while(!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println("Thanks for playing!");
    }
    private boolean processCommand(Command command){
        boolean wantToQuit = false;
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

            case Health:
                health(command);
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
            System.out.println("There is no door!");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }
    private void drive(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drive where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom ==null){
            System.out.println("You can't drive there!");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }
    private void drop(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }
        String key = command.getSecondWord();
        Item dropItem = player.getItem(key);
        if (dropItem ==null){
            System.out.println("You can't drop that!");
        }
        else{
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
        Item dropItem = player.getItem(key);
        if (dropItem ==null){
            System.out.println("You can't swipe that!");
        }
        else{
            currentRoom.setItem(key, dropItem);
            System.out.println("You swiped " + key);
        }
    }
    public void health(Command command){
        System.out.println("Your health is " + player.getHealth());
        player.setHealth(player.getHealth()) + health.getHealth();

    }
    private void grab(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Grab what?");
            return;
        }
        String key = command.getSecondWord();
        Item grabItem = currentRoom.getItem(key);
        if (grabItem ==null){
            System.out.println("You can't grab that!");
        }
        else{
            player.setItem(key, grabItem);
            System.out.println("You grabbed " + key);

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
        System.out.println("You will find yourself in a garden maze, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance.");
        System.out.println();
        System.out.println("We will print a long room description here.");
    }
}
