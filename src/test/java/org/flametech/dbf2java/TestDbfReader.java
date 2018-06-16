package org.flametech.dbf2java;

import org.flametech.dbf2java.core.DBFRow;
import org.flametech.dbf2java.core.DBFile;
import org.flametech.dbf2java.core.DBFField;
import org.flametech.dbf2java.core.DBFMetadata;
import org.flametech.dbf2java.reader.DBFResultSet;
import org.flametech.dbf2java.util.Dbf2JavaUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestDbfReader {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
    //@Test
    public void test1() throws IOException, ParseException {
        //Charset stringCharset = Charset.forName("Cp866");

        //InputStream dbf = getClass().getClassLoader().getResourceAsStream("data1/gds_im.dbf");
        DBFile dbfFile = new DBFile("E:\\Prabir\\Projects\\dpl_docs\\M_ITEM.dbf");
        dbfFile.setCharset("Cp866");
        DBFRow rec;
        try (DBFResultSet reader = new DBFResultSet(dbfFile)) {
            DBFMetadata meta = reader.getMetadata();

            assertEquals(5, meta.getRecordsCount());
            assertEquals(28, meta.getFields().size());

            System.out.println("Read DBF Metadata: " + meta);
            int recCounter = 0;
            while ((rec = reader.nextRow()) != null) {
                //rec.setStringCharset(stringCharset);
                System.out.println("Record is DELETED: " + rec.isDeleted());
                System.out.println(rec.getRecordNumber());
                System.out.println(rec.toMap());

                recCounter++;
                assertEquals(recCounter, rec.getRecordNumber());
            }
        }
    }

    //@Test
    public void test2() throws IOException, ParseException {
        //Charset stringCharset = Charset.forName("Cp866");

        //InputStream dbf = getClass().getClassLoader().getResourceAsStream("data1/tir_im.dbf");
        DBFile dbfFile = new DBFile("E:\\Prabir\\Projects\\dpl_docs\\M_ITEM.dbf");
        dbfFile.setCharset("Cp866");
        DBFRow rec;
        try (DBFResultSet reader = new DBFResultSet(dbfFile)) {
            DBFMetadata meta = reader.getMetadata();

            assertEquals(1, meta.getRecordsCount());
            assertEquals(117, meta.getFields().size());

            System.out.println("Read DBF Metadata: " + meta);
            int recCounter = 0;
            while ((rec = reader.nextRow()) != null) {
                //rec.setStringCharset(stringCharset);
                System.out.println("Record is DELETED: " + rec.isDeleted());
                System.out.println(rec.getRecordNumber());
                System.out.println(rec.toMap());

                recCounter++;
                assertEquals(recCounter, rec.getRecordNumber());
            }
        }
    }
    
    //@Test
    public void testEmptyStream() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {});
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testOneByteStreamWithGoodFileType() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {0x02});
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testOneByteStreamWithBadFileType() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {0x02});
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testSixteenByteStreamWithGoodFileType() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2});
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testThirtyTwoByteStreamWithGoodFileType() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2 });
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testSixtyFourByteStreamWithGoodFileType() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2 });
    	exception.expect(IOException.class);
    	exception.expectMessage("The file is corrupted or is not a dbf file");
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        }*/
    }
    
    //@Test
    public void testSixtyFourByteStreamWithGoodFileTypeAndCloseHeader() throws IOException {
    	InputStream dbf = new ByteArrayInputStream(new byte[] {
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 0x2, 
    			Dbf2JavaUtils.HEADER_TERMINATOR });
        /*try (DBFResultSet reader = new DBFResultSet(dbf)) {
        	assertNull(reader.nextRow());
        }*/
    }
    
    //@Test
    public void readData() throws IOException, ParseException
    {
    	//Charset stringCharset = Charset.forName("Cp866");

        //InputStream dbf = getClass().getClassLoader().getResourceAsStream("data/ITEM.dbf");
        DBFile dbfFile = new DBFile("E:\\Prabir\\Projects\\dpl_docs\\M_ITEM.dbf");
        dbfFile.setCharset("Cp866");
        try (DBFResultSet reader = new DBFResultSet(dbfFile)) {
            DBFMetadata meta = reader.getMetadata();
           
            System.out.println("Read DBF Metadata: " + meta);
            System.out.println(meta.getRecordsCount());
            int recCounter = 0;
            DBFRow rec;
            while ((rec = reader.nextRow()) != null) {
                //rec.setStringCharset(stringCharset);
                System.out.println(rec.getRecordNumber() + "- " + rec.getString("ITEM_CODE") + "/" + rec.getString("ITEM_NAME"));
                //System.out.println("Record is DELETED: " + rec.isDeleted());                
               // System.out.println(rec.toMap());
				
                recCounter++;
                
                
            }
            assertEquals(meta.getRecordsCount(), recCounter);
        }
    }
    
    @Test
    public void readData2() throws IOException, ParseException
    {
    	//Charset stringCharset = Charset.forName("Cp866");

        DBFile dbfFile = new DBFile("E:\\Prabir\\Projects\\dpl_docs\\M_ITEM.dbf");
        dbfFile.setCharset("Cp866");
        try (DBFResultSet reader = new DBFResultSet(dbfFile)) {
            DBFMetadata meta = reader.getMetadata();
           
            System.out.println("Read DBF Metadata: " + meta);
            System.out.println(meta.getRecordsCount());
            int recCounter = 0;
            DBFRow rec;
            while ((rec = reader.nextRow()) != null) {
                //rec.setStringCharset(stringCharset);
                System.out.println(rec.getRecordNumber() + "- " + rec.getString("ITEM_CODE") + "/" + rec.getString("ITEM_NAME"));
                recCounter++;
                
                
            }
            assertEquals(meta.getRecordsCount(), recCounter);
        }
    }
}
