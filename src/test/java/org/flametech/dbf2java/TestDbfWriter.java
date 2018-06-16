package org.flametech.dbf2java;

import org.flametech.dbf2java.core.DBFField;
import org.flametech.dbf2java.core.DBFFieldTypeEnum;
import org.flametech.dbf2java.core.DBFFileTypeEnum;
import org.flametech.dbf2java.core.DBFMetadata;
import org.flametech.dbf2java.util.DbfMetadataUtils;
import org.flametech.dbf2java.writer.DbfWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class TestDbfWriter {
    private final Map<String, Object> valueMap = new HashMap<>();
    private String filePath;
    private List<DBFField> fields = new ArrayList<>();

    public DBFField addCharDBFField(String name, int length) {
        final DBFField fld = new DBFField();
        fld.setName(name);
        fld.setType(DBFFieldTypeEnum.Character);
        fld.setLength(length);
        fields.add(fld);
        return fld;
    }

    public DBFField addNumDBFField(String name, int length, int decimal) {
        final DBFField fld = new DBFField();
        fld.setName(name);
        fld.setType(DBFFieldTypeEnum.Numeric);
        fld.setLength(length);
        fld.setNumberOfDecimalPlaces(decimal);
        fields.add(fld);
        return fld;
    }

    public DBFField addDateDBFField(String name) {
        final DBFField fld = new DBFField();
        fld.setName(name);
        fld.setType(DBFFieldTypeEnum.Date);
        fields.add(fld);
        return fld;
    }

    //@Before
    public void prepareData() {
        valueMap.put("FIOISP", "Виноградова Ольга Евгеньевна");
        valueMap.put("NAME", "Вячеслав");
        valueMap.put("SURNAME", "Егоров");
        valueMap.put("DATER", "30.06.1971");
        valueMap.put("SECONDNAME", "Иванович");
        valueMap.put("UNICODE", new BigDecimal(1001731864));
        valueMap.put("NUMID", "6/14/19/69");
        filePath = "G:\\test\\" + new Date().getTime() + ".dbf";
        fields.add(addCharDBFField("FIOISP", 100));
        fields.add(addCharDBFField("NAME", 250));
        fields.add(addCharDBFField("SURNAME", 250));
        fields.add(addCharDBFField("DATER", 10));
        fields.add(addCharDBFField("SECONDNAME", 250));
        fields.add(addNumDBFField("UNICODE", 10, 10));
        fields.add(addCharDBFField("NUMID", 100));
    }

    //@Test
    public void test() throws IOException {
        DBFMetadata dbfMetadata = new DBFMetadata();
        dbfMetadata.setFields(fields);
        dbfMetadata.setOneRecordLength(DbfMetadataUtils.calculateOneRecordLength(fields));
        dbfMetadata.setType(DBFFileTypeEnum.FoxBASE2);
        FileOutputStream fos = null;
        DbfWriter dbfWriter = null;
        try {
            fos = new FileOutputStream("111.dbf");
            dbfWriter = new DbfWriter(dbfMetadata, fos);
            final String encoding = "CP866";
            dbfWriter.setStringCharset(encoding);
            dbfWriter.write(valueMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dbfWriter != null) {
                dbfWriter.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
