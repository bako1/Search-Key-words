package IMT3881;

import java.util.*;
public class WordCounter {
    private final ReadFile readFile;

    public WordCounter() {
        this.readFile = new ReadFile();
    }

    public void countWord(String folder, String fileExtension, String wordToSearch) {
        int count = 0;
        Map<String, Integer> map = new HashMap<>();
        readFile.fileFounder(folder, fileExtension);
        for (String files : readFile.getListOfFile()) {
            readFile.readFromFile(fileExtension,files);
            for (Map.Entry<String, ArrayList<String>> str : readFile.getMapWordToFile().entrySet()) {
                for (Object words : str.getValue()) {
                    if (words.toString().equalsIgnoreCase(wordToSearch)) {
                        count++;
                        {
                            if (map.containsKey(files)) {
                                map.put(files, map.get(files) + 1);
                            } else {
                                map.put(files, 1);
                            }
                        }
                    }
                }
            }
        }
        if (!map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry obj = (Map.Entry) it.next();

                readFile.writeOutput("Th word "+wordToSearch +" appeared "+obj.getValue() +
                        " times in file "+obj.getKey()+"\n");
                readFile.writeOutput("\n------------------------------------------------------------------------\n");
            }
        }
        readFile.writeOutput("\n The word "+wordToSearch+ ": appeared "+ count+ " times in the folder "+ folder + " \n");
        readFile.writeOutput("\n-------------------------------------------------------------------------------\n");

    }

    public void countWord(String folder, String fileExtension, String wordToSearch, String wordToSearch1){
        countWord(folder,fileExtension,wordToSearch);//the first word
        countWord(folder,fileExtension,wordToSearch1);//the second word
    }
    public void countWord(String folder, String fileExtension, String wordToSearch, String wordToSearch1,String wordToSearch2){

        countWord(folder,fileExtension,wordToSearch); //the first word
        countWord(folder,fileExtension,wordToSearch1);//the second word
        countWord(folder,fileExtension,wordToSearch2);//the third word

    }
}

