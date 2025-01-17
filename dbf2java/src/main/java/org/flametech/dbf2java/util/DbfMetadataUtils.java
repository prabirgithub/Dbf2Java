package org.flametech.dbf2java.util;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.flametech.dbf2java.core.DBFField;
import org.flametech.dbf2java.core.DBFFieldTypeEnum;
import org.flametech.dbf2java.core.DBFFileTypeEnum;
import org.flametech.dbf2java.core.DBFMetadata;

public class DbfMetadataUtils {

    public static DBFMetadata fromFieldsString(String s) throws IOException {
        List<DBFField> fields = Dbf2JavaUtils.createFieldsFromString(s);

        DBFMetadata metadata = new DBFMetadata();

        metadata.setType(DBFFileTypeEnum.FoxBASEPlus1);
        metadata.setUpdateDate(new Date());
        //metadata.setRecordsQty(recordsQty);
        int fullHeaderLength = calculateFullHeaderLength(fields);
        metadata.setFullHeaderLength(fullHeaderLength);
        int oneRecordLength = calculateOneRecordLength(fields);
        metadata.setOneRecordLength(oneRecordLength);

        metadata.setFields(fields);

        return metadata;
    }

    public static DBFMetadata fromFields(List<DBFField> fields, DBFFileTypeEnum fileType) throws IOException {
        DBFMetadata metadata = new DBFMetadata();

        metadata.setType(fileType);
        metadata.setUpdateDate(new Date());
        int fullHeaderLength = calculateFullHeaderLength(fields);
        metadata.setFullHeaderLength(fullHeaderLength);
        int oneRecordLength = calculateOneRecordLength(fields);
        metadata.setOneRecordLength(oneRecordLength);

        metadata.setFields(fields);

        return metadata;
    }

    public static int calculateOneRecordLength(List<DBFField> fields) {
        int result = 0;
        for (DBFField field : fields) {
            result += field.getLength();
        }
        result++;
        return result;
    }

    private static int calculateFullHeaderLength(List<DBFField> fields) {
        int result = 32;
        result += 32 * fields.size();
        result++;
        return result;
    }

//	public static byte[] toByteArray(DbfMetadata metadata) {
//		
//	}

    public static void fillHeaderFields(DBFMetadata metadata, byte[] headerBytes) throws IOException {
        metadata.setType(DBFFileTypeEnum.fromInt(headerBytes[0]));
        metadata.setUpdateDate(parseHeaderUpdateDate(headerBytes[1], headerBytes[2], headerBytes[3], metadata.getType()));
        metadata.setRecordsCount(BitUtils.makeInt(headerBytes[4], headerBytes[5], headerBytes[6], headerBytes[7]));
        metadata.setFullHeaderLength(BitUtils.makeInt(headerBytes[8], headerBytes[9]));
        metadata.setOneRecordLength(BitUtils.makeInt(headerBytes[10], headerBytes[11]));
        metadata.setUncompletedTxFlag(headerBytes[14]);
        metadata.setEcnryptionFlag(headerBytes[15]);
    }

    @SuppressWarnings("deprecation")
    public static Date parseHeaderUpdateDate(byte yearByte, byte monthByte, byte dayByte, DBFFileTypeEnum fileType) {
        int year = yearByte + 2000 - 1900;
        switch (fileType) {
            case FoxBASEPlus1:
                year = yearByte;
        }
        int month = monthByte - 1;
        int day = dayByte;
        return new Date(year,month,day);

    }

    public static void readFields(DBFMetadata metadata, InputStream inputStream) throws IOException {
        List<DBFField> fields = new ArrayList<DBFField>();
        byte[] fieldBytes = new byte[Dbf2JavaUtils.FIELD_RECORD_LENGTH];
        int headerLength = 0;
        int fieldLength = 0;
        while (true) {
            if (inputStream.read(fieldBytes) != Dbf2JavaUtils.FIELD_RECORD_LENGTH)
            	throw new IOException("The file is corrupted or is not a dbf file");
            	
            DBFField field = createDbfField(fieldBytes);
            fields.add(field);

            fieldLength += field.getLength();
            headerLength += fieldBytes.length;

            long oldAvailable = inputStream.available();
            int terminator = inputStream.read();
            if (terminator == -1) {
            	throw new IOException("The file is corrupted or is not a dbf file");
            } else if (terminator == Dbf2JavaUtils.HEADER_TERMINATOR) {
                break;
            } else {
                inputStream.reset();
                inputStream.skip(inputStream.available() - oldAvailable);
            }
        }
        fieldLength += 1;
        headerLength += 32;
        headerLength += 1;

        if (headerLength != metadata.getFullHeaderLength()) {
            // TODO: handle this anyway!
        }
        if (fieldLength != metadata.getOneRecordLength()) {
            // TODO: handle this anyway!
        }

        metadata.setFields(fields);
    }

    public static DBFField createDbfField(byte[] fieldBytes) {
        DBFField field = new DBFField();
        // 1. Set name
        {
            int i = 0;
            for (i = 0; i < 11 && fieldBytes[i] > 0; i++) ;
            field.setName(new String(fieldBytes, 0, i));
        }
        // 2. Set type
        field.setType(DBFFieldTypeEnum.fromChar((char) fieldBytes[11]));
        // 3. Set length
        {
            int length = fieldBytes[16];
            if (length < 0) {
                length = 256 + length;
            }
            field.setLength(length);
        }
        // 4. Set number of decimal places
        field.setNumberOfDecimalPlaces(fieldBytes[17]);

        return field;
    }

    public static void writeDbfField(DBFField field, byte[] fieldBytes) {
        BitUtils.memset(fieldBytes, 0);
        byte[] nameBytes = field.getName().getBytes();
        int nameLength = nameBytes.length;
        if (nameLength > 11) {
            // throw error here!
        }
        System.arraycopy(nameBytes, 0, fieldBytes, 0, nameBytes.length);
        fieldBytes[11] = field.getType().toByte();
        byte[] b = BitUtils.makeByte4(field.getOffset());
        fieldBytes[12] = b[0];
        fieldBytes[13] = b[1];
        fieldBytes[14] = b[2];
        fieldBytes[15] = b[3];
        int length = field.getLength();
        fieldBytes[16] = (byte) (length & 0xff);
        fieldBytes[17] = (byte) (field.getNumberOfDecimalPlaces() & 0xff);
    }

    @SuppressWarnings("deprecation")
    public static byte[] toByteArrayHeader(DBFMetadata metadata) {
        byte[] headerBytes = new byte[16];
        BitUtils.memset(headerBytes, 0);


        headerBytes[0] = metadata.getType().toByte();

        Date updateDate = metadata.getUpdateDate();
        // date
        if (updateDate == null) {
            updateDate = new Date();
        }
        // write date bytes
        {
            byte[] dateBytes = Dbf2JavaUtils.writeDateForHeader(updateDate);
            headerBytes[1] = dateBytes[0];
            headerBytes[2] = dateBytes[1];
            headerBytes[3] = dateBytes[2];
        }

        byte[] b = BitUtils.makeByte4(metadata.getRecordsCount());
        headerBytes[4] = b[0];
        headerBytes[5] = b[1];
        headerBytes[6] = b[2];
        headerBytes[7] = b[3];

        b = BitUtils.makeByte2(metadata.getFullHeaderLength());
        headerBytes[8] = b[0];
        headerBytes[9] = b[1];

        b = BitUtils.makeByte2(metadata.getOneRecordLength());
        headerBytes[10] = b[0];
        headerBytes[11] = b[1];

        headerBytes[12] = 0;
        headerBytes[13] = 0;

        headerBytes[14] = metadata.getUncompletedTxFlag();
        headerBytes[15] = metadata.getEcnryptionFlag();

        return headerBytes;
    }
}
