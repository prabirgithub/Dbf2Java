package org.flametech.dbf2java;

import org.flametech.dbf2java.core.DBFRow;
import org.flametech.dbf2java.core.DBFFieldTypeEnum;
import org.flametech.dbf2java.core.DBFMetadata;
import org.flametech.dbf2java.reader.DBFResultSet;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class TestMemo {

    //@Test
    public void test1() {
        /*Charset stringCharset = Charset.forName("cp1252");

        InputStream dbf = getClass().getClassLoader().getResourceAsStream("memo1/texto.dbf");
        InputStream memo = getClass().getClassLoader().getResourceAsStream("memo1/texto.fpt");

        try (DBFResultSet reader = new DBFResultSet(dbf, memo)) {
            DBFMetadata meta = reader.getMetadata();
            System.out.println("Read DBF Metadata: " + meta);

            assertEquals(5, meta.getField("TEXVER").getLength());
            assertEquals(DBFFieldTypeEnum.Character, meta.getField("TEXVER").getType());

            assertEquals(4, meta.getField("TEXTEX").getLength());
            assertEquals(DBFFieldTypeEnum.Memo, meta.getField("TEXTEX").getType());

            assertEquals(8, meta.getField("TEXDAT").getLength());
            assertEquals(DBFFieldTypeEnum.Date, meta.getField("TEXDAT").getType());

            assertEquals(1, meta.getField("TEXSTA").getLength());
            assertEquals(DBFFieldTypeEnum.Character, meta.getField("TEXSTA").getType());

            assertEquals(254, meta.getField("TEXCAM").getLength());
            assertEquals(DBFFieldTypeEnum.Character, meta.getField("TEXCAM").getType());

            DBFRow rec;
            while ((rec = reader.nextRow()) != null) {
                rec.setStringCharset(stringCharset);

                System.out.println("Record is DELETED: " + rec.isDeleted());
                System.out.println("TEXVER: " + rec.getString("TEXVER"));
                System.out.println("TEXTEX: " + rec.getMemoAsString("TEXTEX"));
                System.out.println("TEXDAT: " + rec.getDate("TEXDAT"));
                System.out.println("TEXSTA: " + rec.getString("TEXSTA"));
                System.out.println("TEXCAM: " + rec.getString("TEXCAM"));
                System.out.println("++++++++++++++++++++++++++++++++++");
            }

        } catch (IOException e) {
            //e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}
