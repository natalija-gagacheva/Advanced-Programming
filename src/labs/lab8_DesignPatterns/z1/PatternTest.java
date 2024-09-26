package labs.lab8_DesignPatterns.z1;

import java.util.*;

class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}

//The state pattern is used to manage the state of an object and change its behaviour based on its internal state
interface StateInterface {
    void pressPlay();
    void pressStop();
    void pressFWD();
    void pressREW();
}
class MP3Player implements StateInterface {
    int currentSong;
    List<Song> listaOdPesni;
    StateInterface sostojba;

    public MP3Player(List<Song> listaOdPesni) {
        this.listaOdPesni = listaOdPesni;
        currentSong=0;
    }


    public void pressPlay() {

    }

    public void printCurrentSong() {

    }


    public void pressFWD() {

    }

    public void pressREW() {

    }

    public void pressStop() {
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong = " + currentSong +
                ", songList = " + listaOdPesni +
                '}';
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player mp3player = new MP3Player(listSongs);

        System.out.println(mp3player.toString());

        //------------------------------------------------------------------------------------------------
        System.out.println("First test");


        mp3player.pressPlay(); //se pusta pesnata, song 0
        mp3player.printCurrentSong(); //se pechati pesnata koja momentalno se slusha
        mp3player.pressPlay(); //se pusta pak song 0, ama treba da se kazhi deka veke se slusha
        mp3player.printCurrentSong(); //pecati song0

        mp3player.pressPlay(); //se pusta pak song 0
        mp3player.printCurrentSong(); //pecati song0
        mp3player.pressStop(); //pauziraj song0
        mp3player.printCurrentSong(); //pecati song0

        mp3player.pressPlay(); //se pusta song 0
        mp3player.printCurrentSong(); //pecati song0
        mp3player.pressFWD(); //poteraj kon vtora pesna, song 1
        mp3player.printCurrentSong(); //sega ke se pecati song 1 (vtorata pesna)

        mp3player.pressPlay(); //se pusta song 1 (vtorata pesna)
        mp3player.printCurrentSong(); //pecati song 1
        mp3player.pressREW(); //vrakjame na prvata pesna, song 0
        mp3player.printCurrentSong(); //pecati song0


        System.out.println(mp3player.toString());

        //------------------------------------------------------------------------------------------------
        System.out.println("Second test");


        mp3player.pressStop();
        mp3player.printCurrentSong();
        mp3player.pressStop();
        mp3player.printCurrentSong();

        mp3player.pressStop();
        mp3player.printCurrentSong();
        mp3player.pressPlay();
        mp3player.printCurrentSong();

        mp3player.pressStop();
        mp3player.printCurrentSong();
        mp3player.pressFWD();
        mp3player.printCurrentSong();

        mp3player.pressStop();
        mp3player.printCurrentSong();
        mp3player.pressREW();
        mp3player.printCurrentSong();


        System.out.println(mp3player.toString());

        //------------------------------------------------------------------------------------------------
        System.out.println("Third test");


        mp3player.pressFWD();
        mp3player.printCurrentSong();
        mp3player.pressFWD();
        mp3player.printCurrentSong();

        mp3player.pressFWD();
        mp3player.printCurrentSong();
        mp3player.pressPlay();
        mp3player.printCurrentSong();

        mp3player.pressFWD();
        mp3player.printCurrentSong();
        mp3player.pressStop();
        mp3player.printCurrentSong();

        mp3player.pressFWD();
        mp3player.printCurrentSong();
        mp3player.pressREW();
        mp3player.printCurrentSong();


        System.out.println(mp3player.toString());
    }
}
