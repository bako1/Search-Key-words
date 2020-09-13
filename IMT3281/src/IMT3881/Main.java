package IMT3881;

public class Main {

    public static void main(String[] args) {
        try {

            WordCounter wordCounter = new WordCounter();
            if (args.length > 5 || args.length < 2) {
                System.out.println("Usage: SearchWord <folderName> <fileExtension> <keyword1> (optional): <keyword2> <keyword3>");

            } else if (args.length == 3) {
                wordCounter.countWord(args[0], args[1], args[2]);
            } else if (args.length == 4) {
                wordCounter.countWord(args[0], args[1], args[2], args[3]);


            } else {
                wordCounter.countWord(args[0], args[1], args[2], args[3], args[4]);

            }
        }catch (IndexOutOfBoundsException ibe){
            System.out.println(ibe.getMessage());
            System.out.println("Usage: SearchWord <folderName> <fileExtension> <keyword1> (optional): <keyword2> <keyword3>");
        }



    }
}