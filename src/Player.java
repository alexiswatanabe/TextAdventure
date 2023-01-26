import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
public class Player {
int health;
    private HashMap<String, Item>inventory;

    Player() {
        inventory = new HashMap<>();
    }
    public String getItemString(){
        String returnString = "Player Inv:";
        Set<String> keys = inventory.keySet();
        for (String item: keys){
            returnString += " " + item;
        }
        return returnString;
    }
    public void setItem(String name, Item item){
        inventory.put(name, item);
    }

    public Item getItem(String name){
        return inventory.remove(name);
    }
    public void setHealth(){
        int health = 3;
    }
    public class getHealth(){
        return health;
    }

}
