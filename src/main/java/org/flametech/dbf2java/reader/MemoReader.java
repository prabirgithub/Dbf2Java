package org.flametech.dbf2java.reader;

import java.io.*;

import org.flametech.dbf2java.core.MemoFileHeader;
import org.flametech.dbf2java.core.MemoRecord;
import org.flametech.dbf2java.util.BitUtils;
import org.flametech.dbf2java.util.IOUtils;
import org.flametech.dbf2java.util.Dbf2JavaUtils;

/**
 * Reader of memo files (tested of *.FPT files - Visual FoxPro)
 * See links: 
 * 
 * Visual FoxPro file formats:
 * http://msdn.microsoft.com/en-us/library/aa977077(v=vs.71).aspx
 * 
 * DBase file formats:
 * http://www.dbase.com/Knowledgebase/INT/db7_file_fmt.htm
 * 
 */
public class MemoReader implements Closeable {

    private static final int BUFFER_SIZE = 8192;
    private InputStream memoInputStream;
    private MemoFileHeader memoHeader;

    public MemoReader(File memoFile) throws IOException {
        this(new FileInputStream(memoFile));
    }

    public MemoReader(InputStream inputStream) throws IOException {
        this.memoInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
        readMetadata();
    }

    private void readMetadata() throws IOException {
        byte[] headerBytes = new byte[Dbf2JavaUtils.MEMO_HEADER_LENGTH];
        memoInputStream.mark(8192);

        if (IOUtils.readFully(memoInputStream, headerBytes) != Dbf2JavaUtils.MEMO_HEADER_LENGTH)
            throw new IOException("The file is corrupted or is not a dbf file");

        this.memoHeader = MemoFileHeader.create(headerBytes);
    }

    public void close() throws IOException {
        if (memoInputStream != null) {
            memoInputStream.close();
        }
    }

    public MemoFileHeader getMemoHeader() {
        return memoHeader;
    }

    public MemoRecord read(int offsetInBlocks) throws IOException {
        memoInputStream.reset();
        memoInputStream.skip(memoHeader.getBlockSize()*offsetInBlocks);
        byte[] recordHeader = new byte[Dbf2JavaUtils.RECORD_HEADER_LENGTH];
        if (IOUtils.readFully(memoInputStream, recordHeader) != Dbf2JavaUtils.RECORD_HEADER_LENGTH)
            throw new IOException("The file is corrupted or is not a dbf file");

        int memoRecordLength = BitUtils.makeInt(recordHeader[7], recordHeader[6], recordHeader[5], recordHeader[4]);
        byte[] recordBody = new byte[memoRecordLength];

        if (IOUtils.readFully(memoInputStream, recordBody) != memoRecordLength)
            throw new IOException("The file is corrupted or is not a dbf file");

        return new MemoRecord(recordHeader, recordBody, memoHeader.getBlockSize(), offsetInBlocks);
    }
}
