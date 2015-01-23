package unzip;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by King on 1/20/2015.
 */
public class UnzipContent {

    public  void unzipFile(String args) throws Exception
    {
        byte[] buffer = new byte[1024];
        System.out.print(args);
        GZIPInputStream gzis =
                new GZIPInputStream(new FileInputStream(args));

        FileOutputStream out =
                new FileOutputStream("f:\\test1.log");

        int len;
        while ((len = gzis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        gzis.close();
        out.close();

        System.out.println("Done");

    }
    public String unzipContent(byte[] compressedData) throws Exception
    {

        GZIPInputStream gzis =new GZIPInputStream(new ByteArrayInputStream(compressedData));
        //FileOutputStream out = new FileOutputStream("C:\\MyFile112.log");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;byte[] buffer = new byte[1024];
       // StringBuffer content=new StringBuffer();


        /*String x=new String(gzis.read());*/
        while ((len = gzis.read(buffer)) > 0) {
            out.write(buffer, 0, len);

        }
        InputStream is = new ByteArrayInputStream(out.toByteArray());
        String content=IOUtils.toString(is);

        System.out.print(content);

        gzis.close();
        out.close();

        return content;
    }
    public String unzip(byte[] compressedData) throws Exception
    {

       // byte[] bFile = new byte[(int) compressedData.length];

       /* FileOutputStream fos = new FileOutputStream("C:\\MyFile.log.gz");
        ZipOutputStream zos = new ZipOutputStream(fos);
        ZipEntry ze= new ZipEntry("test.log");
        zos.putNextEntry(ze);
        zos.write(compressedData, 0, compressedData.length);
        zos.closeEntry();*/

        //remember close it
       // zos.close();


        GZIPInputStream gzis =new GZIPInputStream(new ByteArrayInputStream(compressedData));
        FileOutputStream out = new FileOutputStream("C:\\MyFile112.log");

        int len;byte[] buffer = new byte[1024];
        while ((len = gzis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        gzis.close();
        out.close();

        return "done";
    }

    public  byte[] decompress(byte[] contentBytes){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(contentBytes)), out);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }
}
