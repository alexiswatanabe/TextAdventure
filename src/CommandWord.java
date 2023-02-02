public enum CommandWord {
    GO( "go"), Quit( "quit"), Help("help"), Unknown("?"), Look("look"), Grab("grab"), Drop("drop"), Drive("drive"), Swipe("swipe");
    private String commandString;
     CommandWord(String commandString){
        this.commandString = commandString;
    }
    public String toString(){
         return commandString;
    }
}
