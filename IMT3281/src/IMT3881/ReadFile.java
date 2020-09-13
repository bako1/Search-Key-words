package IMT3881;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile {
    private List<String> listOfFile;
    private HashMap<String, ArrayList> mapWordToFile;

    public ReadFile() {
        this.listOfFile = new ArrayList<>();
        this.mapWordToFile = new HashMap<>();
    }

    public HashMap<String, ArrayList> getMapWordToFile() {
        return mapWordToFile;
    }

    public void setWordToFile(HashMap<String, ArrayList> mAPwORDtOfILE) {
        this.mapWordToFile = mAPwORDtOfILE;
    }

    public List<String> getListOfFile() {
        return listOfFile;
    }

    public void setListOfFile(List<String> listOfFile) {
        this.listOfFile = listOfFile;
    }

    public void fileFounder(String folderName, String fileExtension) {
        List<String> arrayList;
        try {
            Stream<Path> walk = Files.walk(Paths.get(folderName), 1);//only one depth level
            arrayList = walk.filter(Files::isRegularFile)//checks whether its regular file or not
                    .map(Path::toString)              //convert to string
                    .filter(a -> a.endsWith(fileExtension)) // filters only those match file extension
                    .collect(Collectors.toList());  //collect to List (for lateral use)
            setListOfFile(arrayList);
            writeOutput("The folder "+folderName + " has "  +
                    getListOfFile().size() + " " + " " + fileExtension + " files\n");
            System.out.println(getListOfFile());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void readTxt(String fileName) {

        ArrayList<String> fromFile = new ArrayList<>();
        HashMap<String,ArrayList> mapFileToWords = new HashMap<>();
        String words;
        String[] afterSplit;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((words = bufferedReader.readLine()) != null) {
                words = words.replaceAll("[^a-zA-Z0-9]", " ");
                afterSplit = words.split(" ");


                for (String split : afterSplit) {
                    fromFile.add(split.toLowerCase());
                    mapFileToWords.put(fileName, fromFile);
                    setWordToFile(mapFileToWords);

                }
            }


        } catch (IOException ioe) {
            System.out.println(ioe+ "\n" +"cant find "+fileName);
        }

    }

   public void readDocs(String fileName){
       ArrayList<String> fromFile = new ArrayList<>();//ArrayList to store strings from file
       HashMap<String,ArrayList> mapFileToWords = new HashMap<>();// hash map key->file name & value arrayList of words
        try {
            FileInputStream fis = new FileInputStream(fileName);
            //used to extract the content
            HWPFDocument docs = new HWPFDocument(fis);
            //fetch paragraph text using getParagraphText() method

            WordExtractor extractor = new WordExtractor(docs);
            String [] fileData = extractor.getParagraphText();
            String [] afterSplit;

            //iterate over the paragraph list
            for(String paragraph: fileData){

                paragraph = paragraph.replace("[^a-zA-Z0-9]"," ");
                afterSplit =  paragraph.split(" ");
                for(String split:afterSplit){
                    fromFile.add(split);
                    mapFileToWords.put(fileName,fromFile);
                    setWordToFile(mapWordToFile);
                    System.out.println(getMapWordToFile());
                }

            }


        } catch (FileNotFoundException fne) {
         System.out.println(fne.getMessage());
} catch (IOException ioException) {
             ioException.printStackTrace();
        }


   }
    public void readDocx(String fileName){

        ArrayList<String> fromFile = new ArrayList<>();//ArrayList to store strings from file
        HashMap<String,ArrayList> mapFileToWords = new HashMap<>();// hash map key->file name & value arrayList of words
try {
    String readWords;
    String [] afterSplit;
    FileInputStream fis = new FileInputStream(fileName);
    List<XWPFParagraph> xwpfParagraphList;
    try (XWPFDocument dox = new XWPFDocument(fis)) {
        xwpfParagraphList = dox.getParagraphs();
    }
    for (XWPFParagraph paragraph : xwpfParagraphList) {
            readWords =  paragraph.getText().replace("[^a-zA-z0-9]"," ");
            afterSplit =  readWords.split(" ");

        for(String split:afterSplit){
            fromFile.add(split);
            mapFileToWords.put(fileName,fromFile);
            setWordToFile(mapFileToWords);


        }

    }

}catch (IOException ioe){
    System.out.println(ioe.getMessage());
}
    }
public void readFromFile(String fileExtension,String fileName){
        switch (fileExtension){
            case ".docx":
                readDocx(fileName);
                break;
            case ".docs":
                readDocs(fileName);
                break;
            case ".txt":
                readTxt(fileName);
                break;
            default:
                break;

        }




}


    public void writeOutput(String text) {
        try {
            FileWriter outputWriter = new FileWriter("output.txt", true);
            outputWriter.write(text);
            outputWriter.close();
        } catch (Exception e) {
            System.out.println("Could not write to file output.txt");
        }
    }
}
