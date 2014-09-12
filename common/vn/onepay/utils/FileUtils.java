package vn.onepay.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class FileUtils {
	/**
	 * 
	 * @param aFile
	 * @return
	 */
	static public String readTextFile(File aFile) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(aFile);
			ByteArrayOutputStream out=new ByteArrayOutputStream((int)aFile.length());
			byte[] buff=new byte[1024];
			for(int i=fis.read(buff);i!=-1;i=fis.read(buff)){
				out.write(buff,0,i);
			}
			return out.toString("UTF8");				
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void copyFile(File inputFile, File outputFile) {
		try {
			byte[] buff = new byte[4096];
			InputStream in = new FileInputStream(inputFile);
			OutputStream out = new FileOutputStream(outputFile);
			for (int r = in.read(buff); r != -1; r = in.read(buff)) {
				out.write(buff, 0, r);
			}
			in.close();
			out.close();
		} catch (java.io.FileNotFoundException exp1) {
			exp1.printStackTrace();
		} catch (IOException ioE) {
			ioE.printStackTrace();
		}
	}

	/**
	 * 
	 * @param document
	 * @param outFile
	 */
	public static void writeXMLToFile(Document document, File outFile) {
		try {
			// create DOMSource for source XML document
			Source xmlSource = new DOMSource(document);
			// create StreamResult for transformation result
			Result result = new StreamResult(new FileOutputStream(outFile));
			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty("indent", "yes"); // Java XML Indent
			// transform and deliver content to client
			transformer.transform(xmlSource, result);
			// handle exception creating TransformerFactory
		} catch (TransformerFactoryConfigurationError factoryError) {
			System.err.println("Error creating " + "TransformerFactory");
			factoryError.printStackTrace();
		} catch (TransformerException transformerError) {
			System.err.println("Error transforming document");
			transformerError.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception{
//		String content =readTextFile(new File("/home/hahm/Desktop/contract.tmp"));
//		String output=new String(content.getBytes(),"UTF-8");
//		System.out.println(output);
//		new FileOutputStream("/home/hahm/Desktop/contract.html").write(output.getBytes("UTF-8")); 
		InputStream in = FileUtils.class.getResourceAsStream("contract.tmp");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];

		while ((nRead = in.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}
		buffer.flush();
		String output=buffer.toString("UTF8");
		System.out.println(output);
	}
	
	public final static byte[] load(String fileName) {
		try {
			FileInputStream fin = new FileInputStream(fileName);
			return load(fin);
		} catch (Exception e) {

			return new byte[0];
		}
	}

	public final static byte[] load(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			return load(fin);
		} catch (Exception e) {

			return new byte[0];
		}
	}

	public final static byte[] load(FileInputStream fin) {
		byte readBuf[] = new byte[512 * 1024];

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			int readCnt = fin.read(readBuf);
			while (0 < readCnt) {
				bout.write(readBuf, 0, readCnt);
				readCnt = fin.read(readBuf);
			}

			fin.close();

			return bout.toByteArray();
		} catch (Exception e) {

			return new byte[0];
		}
	}
}
