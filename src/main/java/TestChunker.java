import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by King on 1/23/2015.
 */
public class TestChunker {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;

        br = new BufferedReader(new FileReader("C:\\Users\\King\\Downloads\\test_10.log"));
        String sCurrentLine;
        String content = "";
        int count = 0;
        while ((sCurrentLine = br.readLine()) != null) {
            System.out.println(sCurrentLine);
            if (count == 0) {
                content = content.concat(sCurrentLine);
            } else {
                content = content.concat("\n").concat(sCurrentLine);
            }

            count++;
        }


        /*String x=new String(gzis.read());*/


        //String content= IOUtils.toString(is);

        String[] contentArray = content.split("\n");

        int i = 1;
        String message = "";
        int chunkSize = 50;
        int contentLength = contentArray.length;
        System.out.println("$$$$$$$$$$$$$$  " + contentLength);
        if (contentArray.length > chunkSize) {
            for (String tuple : contentArray) {

                message = message.concat("\n").concat(tuple);
                contentLength = contentLength - 1;
                if (i == chunkSize) {
                    System.out.print(message);
                            /*keymesaage = new KeyedMessage("logs12",message);
                            producer.send(keymesaage);*/
                    System.out.print("\n\n-----------------------\n\n");
                    message = "";
                    i = 0;
                } else if (contentLength == 0) {

                    System.out.println("**********WITHIN CONTENT ");
                    System.out.print(message);
                }
                i++;


                // if(contentArray.length)

            }
        }else{
            System.out.print(content);
        }
    }
}
