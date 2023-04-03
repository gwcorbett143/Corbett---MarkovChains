import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MarkovChains {

    public static String getRand(List<String> list){
        return list.get((int) (Math.random()*list.size()));
    }

    public static void main(String[] args) throws IOException {
        //read input file
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input file name: ");
        String inputFile = consoleReader.readLine();

        List<String> sentenceStart = new ArrayList<>();
        HashMap<String, List<String>> after = new HashMap<>();

        //read in sample text
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        //split into sentences
        StringTokenizer st1 = new StringTokenizer(sb.toString(), ".;?!");
        while(st1.hasMoreTokens()){
            //split by words and put into hashmap
            StringTokenizer st2 = new StringTokenizer(st1.nextToken(), " ");
            String prev = st2.nextToken().replaceAll("[^A-Za-z'-]", "");
            while(prev.length() == 0 && st2.hasMoreTokens()) prev = st2.nextToken().replaceAll("[^A-Za-z'-]", "");
            if(prev.length() == 0) continue;
            sentenceStart.add(prev);
            while(st2.hasMoreTokens()){
                String curr = st2.nextToken().replaceAll("[^A-Za-z'-]", "");
                if(curr.length() == 0) continue;
                if(!after.containsKey(prev)) after.put(prev, new ArrayList<>());
                after.get(prev).add(curr);
                prev = curr;
            }
            if(!after.containsKey(prev)) after.put(prev, new ArrayList<>());
            after.get(prev).add(".");
        }

        //request word count and output file
        System.out.print("Number of words: ");
        int N = Integer.parseInt(consoleReader.readLine());
        System.out.print("Output file name: ");
        String outputFile = consoleReader.readLine();

        //generate output and append into string builder
        StringBuilder sol = new StringBuilder();
        String last = ".";
        while(N-- > 0){
            String add;
            if(last.equals(".")){
                add = getRand(sentenceStart);
            }else{
                add = getRand(after.get(last));
            }
            if(add.equals(".")) sol.deleteCharAt(sol.length()-1);
            sol.append(add).append(" ");
            last = add;
        }

        //print out final output
        PrintWriter pw = new PrintWriter(outputFile);
        pw.print(sol);
        pw.close();
    }
}
