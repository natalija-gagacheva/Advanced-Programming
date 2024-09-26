package labs.lab7.z1;

import javax.print.attribute.standard.MediaName;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String message) {
        super(message);
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String message) {
        super(message);
    }
}

//sistemot se sostoi od povekje Chat rooms
class ChatRoom {
    String imeNaSoba;
    //ime na korisnici koi momentalno se naogjaat vo chatot
    Set<String> setOdKorisnici;

    public ChatRoom(String imeNaSoba) {
        this.imeNaSoba = imeNaSoba;
        setOdKorisnici = new TreeSet<>();
    }

    public void addUser(String korisnickoIme) {
        setOdKorisnici.add(korisnickoIme);
    }


    public void removeUser(String korisnickoIme) {
        setOdKorisnici.removeIf(edenKorisnik -> edenKorisnik.equals(korisnickoIme));
    }

    /* toString():String - враќа стринг кои ги содржи името на собата
    и сите корисници кои се во собата секој одделен со нов ред.
    Корисниците се подредени алфабетски. Ако собата е празна се враќа името на собата
    во еден ред, а во вториот ред стрингот "EMPTY" (наводници само за појаснување). */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        /* ChatRoom1
           user1
           user3
           user4
        */

        sb.append(imeNaSoba).append("\n");

        //append each user in the set; for each 'korisnik' the user's name is appended to 'sb' followed by a newline
        setOdKorisnici.forEach(korisnik -> sb.append(korisnik).append("\n"));

        if (setOdKorisnici.isEmpty()) {
            sb.append("EMPTY").append("\n");
        }
            return sb.toString();

    }

    public boolean hasUser(String username) {
        return setOdKorisnici.contains(username);

        /* vtoro reshenie:
        return setOdKorisnici.stream().anyMatch(korisnik -> korisnik.equals(username));
        */
    }

    public int numUsers() {
        return setOdKorisnici.size();
    }


}

class ChatSystem {
    Map<String,ChatRoom> mapOdChatRooms;
    Set<String> setOdKorisnici;

    public ChatSystem() {
        mapOdChatRooms = new TreeMap<>();
        setOdKorisnici = new HashSet<>();
    }

    public void addRoom(String roomName) {
        mapOdChatRooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) {
        mapOdChatRooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {

        if (!mapOdChatRooms.containsKey(roomName)) //ako NE postoi klucot, ne postoi ni sobata
            throw new NoSuchRoomException(roomName);

        return mapOdChatRooms.get(roomName);

        /* vtoro moe reshenie (less efficient)
         if (mapOdChatRooms.get(roomName)==null) {
            throw new NoSuchRoomException(roomName);
        }

            return mapOdChatRooms.get(roomName);
         */

    }

    /* register(String userName) - го регистрира корисникот во системот.
    Го додава во собата со најмалку корисници. Доколку има повеќе такви соби тогаш
    го додава во првата соба по лексикоргафско подредување. */
    public void register (String username) {

        //go dodavame ili "registrirame:, a sho se slucva bez nego?
        setOdKorisnici.add(username);

        //[ime, soba]
        mapOdChatRooms
                //returns a set of entries in that Map, t.e mnozestvo od parovi
                .entrySet()
                .stream()
                //sortirame spored komparator t.e soba so najmalku korisnici
                .sorted
                        //na Mapata, go zemame Entry, i od key,value sporedvame po value
                        //vo nashiot sluchaj spored Chat room objektite
                        (Map.Entry.comparingByValue
                                (Comparator.comparing(chatRoom -> chatRoom.numUsers()))) //the sorting should be done based on the num of users in each room
                //najdi ja prvata soba so najmalku korisnici
                .findFirst()
                //zemi ja
                .get()
                //od key,value zemi ja vrednosta
                .getValue()
                //dodaj nov korisnik
                .addUser(username);
    }

    public void registerAndJoin(String username, String roomname) {
        setOdKorisnici.add(username);

        mapOdChatRooms.get(roomname).addUser(username);
    }

    public void joinRoom(String username, String roomname) throws NoSuchRoomException, NoSuchUserException {

        if (!mapOdChatRooms.containsKey(roomname)) {
            throw new NoSuchRoomException(roomname);
        }

        //Ако не постои регистриран корисник со тоа име се фрла исклучок
        if (!setOdKorisnici.contains(username)) {
            throw new NoSuchUserException(username);
        }

        mapOdChatRooms
                .get(roomname).addUser(username);

    }

    //го отстранува корисникот од собата со соодветно  име  доколку  таа  постои.
    public void leaveRoom (String username, String roomname) throws NoSuchRoomException, NoSuchUserException {
        if (!mapOdChatRooms.containsKey(roomname)) {
            throw new NoSuchRoomException(roomname);
        }

        //Ако не постои регистриран корисник со тоа име се фрла исклучок

        /* This method checks if the user exists globally in setOdKorisnici, and if the user is not found, it throws NoSuchUserException.
        However, it does not verify if the user is actually in the room before attempting to remove them.
        if (!setOdKorisnici.contains(username)) {
            throw new NoSuchUserException(username);
        }
        */

        if (!mapOdChatRooms.get(roomname).hasUser(username)) {
            throw new NoSuchUserException(username);
        }

        mapOdChatRooms.get(roomname).removeUser(username);

    }

    public void followFriend(String username, String friendUsername) throws NoSuchUserException {


        if (!setOdKorisnici.contains(friendUsername)) {
            throw new NoSuchUserException(friendUsername);
        }

        mapOdChatRooms
                .values()
                .stream()
                .filter(ednaSoba ->ednaSoba.hasUser(friendUsername))
                .forEach(ednaSoba -> ednaSoba.addUser(username));
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        Object[] params = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        try {
                            m.invoke(cs,params);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {

                        }
                    }
                }
            }
        }
    }

}

