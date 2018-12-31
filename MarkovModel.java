import java.util.*;
public class MarkovModel
{
    private ArrayList<String> initDist;
    private Graph transMtx;

    /**
     * Creates new MarkovModel
     */
    public MarkovModel() {
        initDist = new ArrayList<String>();
        transMtx = new Graph();
    }

    /**
     * Adds all first words to initDist
     * 
     * @param firstWords ArrayList of first words of each line of the lyrics
     */
    public void addToInitDist(ArrayList<String> firstWords) {
        initDist.addAll(firstWords);
    }

    /**
     * Adds all words to the graph
     * 
     * @param allWords ArrayList of all words from the songs
     */
    public void addDirectedEdges(ArrayList<String> allWords) {
        for(int i = 1; i < allWords.size(); i++) {
            transMtx.addDirectedEdge(allWords.get(i - 1), allWords.get(i), 0);
        }
    }
    
    /**
     * Generates a randomWalk from the graph
     * 
     * @param lgth length of song
     * @param seed seed for random
     */
    public ArrayList<String> randomWalk(int lgth, long seed) {
        Random random = new Random(seed);
        ArrayList<String> rw = new ArrayList<String>();
        String s = initDist.get(random.nextInt(initDist.size() - 1));
        Graph.Vertex ve = transMtx.getVertex(s);    
        rw.add(s);
        for(int i = 1; i < lgth - 1; i++) {
            int numNBS = ve.nbs.size();
            if(numNBS == 0) {
                break;
            }
            int n = random.nextInt(numNBS);
            ve = ve.nbs.get(n).v;
            rw.add(ve.name);
        }
        return rw;
    }
}