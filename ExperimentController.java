import java.util.*;
import java.io.*;
public class ExperimentController
{
    ArrayList<String> firstWords = new ArrayList<String>();
    ArrayList<String> allWords = new ArrayList<String>();
    
    /**
     * Reads lyrics from txt files
     * 
     * @param pathToFile path to txt file to import words
     */
    public void readLyrics(String pathToFile) {
        try {
            Scanner sc1 = new Scanner(new FileReader(pathToFile));
            while(sc1.hasNextLine()) {
                Scanner sc2 = new Scanner(sc1.nextLine());
                while(sc2.hasNext()) {
                    String[] line = sc2.next().split(" ");
                    for(int i = 0; i < line.length; i++) {
                        line[i] = line[i].toLowerCase();
                    }
                    firstWords.add(line[0]);
                    for(int i = 0; i < line.length; i++) {
                        String w = line[i];
                        String[] letters = w.split("");
                        String ans = "";
                        for(String s : letters) {
                            if(s.charAt(0) <= 122 && s.charAt(0) >= 97) {
                                ans += s;
                            }
                        }
                        allWords.add(ans);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Runs program by making new Markov Model, using randomWalk and outputting 5 txt files with different songs
     */
    public void run() {
        MarkovModel mm = new MarkovModel();
        String dir = "/Users/sethh/OneDrive - lafayette.edu/CS 150L/Lab10/lyrics_data";
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                readLyrics(dir + "/"+ file.getName());
            }
        }
        mm.addToInitDist(firstWords);
        mm.addDirectedEdges(allWords);
        for(int i = 1; i < 6; i++) {
            Random ran = new Random();
            ArrayList<String> randomS = mm.randomWalk(50, ran.nextInt());
            System.out.println("New Song: " + randomS);
            try {
                Writer writer = null;
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("newSong" + i + ".txt"), "utf-8"));
                String s = "";
                for(int j = 0; j < randomS.size(); j++) {
                    s += randomS.get(j) + " ";
                }
                writer.write(s);
                writer.close();
            } catch (Exception e) {
                System.out.println(e);
            } 
        }

    }


    public static void main(String[] args) {
        ExperimentController ec = new ExperimentController();
        ec.run();
    }
}
