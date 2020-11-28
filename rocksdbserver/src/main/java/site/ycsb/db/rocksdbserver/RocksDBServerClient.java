package site.ycsb.db.rocksdbserver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.ycsb.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * RocksDB binding for <a href="http://rocksdb.org/">RocksDB</a>.
 *
 * See {@code rocksdb/README.md} for details.
 */
public class RocksDBServerClient extends DB {
  private RocksDB rocksDB = null;
  private String db = "test";

  private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBServerClient.class);

  @Override
  public void init() throws DBException {
    synchronized (RocksDBServerClient.class){



      try {
        rocksDB = new RocksDB("localhost", 8516, "username", "password");
        rocksDB.createDatabase(db);
      }catch (IOException exception){
        System.out.println(exception.getMessage());
      }
    }

  }

  @Override
  public Status read(String table, String key, Set<String> fields, Map<String, ByteIterator> result){
    try{
      byte[] value =rocksDB.get(db, key.getBytes());
      if (value == null){
        return Status.NOT_FOUND;
      }
      return Status.OK;
    } catch (IOException exception){
      LOGGER.error(exception.getMessage(), exception);
      return Status.ERROR;
    }

  }

  public  Status scan(String table, String startkey, int recordcount, Set<String> fields,
                              Vector<HashMap<String, ByteIterator>> result){
    return Status.ERROR;
  }

  public Status update(String table, String key, Map<String, ByteIterator> values){
    try {
      final byte[] currentValues = rocksDB.get(db, key.getBytes(UTF_8));
      if (currentValues == null){
        return Status.NOT_FOUND;
      }
      final Map<String, ByteIterator> result = new HashMap<>();
      deserializeValues(currentValues, null, result);
      result.putAll(values);
      rocksDB.put(db, key.getBytes(), serializeValues(result));
      return Status.OK;
    }catch (IOException exception){
      return Status.ERROR;
    }


  }

  public  Status insert(String table, String key, Map<String, ByteIterator> values){
    try{

      rocksDB.put(db, key.getBytes(), serializeValues(values));
      return Status.OK;
    } catch (IOException exception){
      LOGGER.error("insert Error"+exception.getMessage(), exception);
      return Status.ERROR;
    }
  }

  public  Status delete(String table, String key){
    return Status.ERROR;
  }

  private byte[] serializeValues(final Map<String, ByteIterator> values) throws IOException {
    try(final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      final ByteBuffer buf = ByteBuffer.allocate(4);

      for(final Map.Entry<String, ByteIterator> value : values.entrySet()) {
        final byte[] keyBytes = value.getKey().getBytes(UTF_8);
        final byte[] valueBytes = value.getValue().toArray();

        buf.putInt(keyBytes.length);
        baos.write(buf.array());
        baos.write(keyBytes);

        buf.clear();

        buf.putInt(valueBytes.length);
        baos.write(buf.array());
        baos.write(valueBytes);

        buf.clear();
      }
      return baos.toByteArray();
    }
  }

  private Map<String, ByteIterator> deserializeValues(final byte[] values, final Set<String> fields,
                                                      final Map<String, ByteIterator> result) {
    final ByteBuffer buf = ByteBuffer.allocate(4);

    int offset = 0;
    while(offset < values.length) {
      buf.put(values, offset, 4);
      buf.flip();
      final int keyLen = buf.getInt();
      buf.clear();
      offset += 4;

      final String key = new String(values, offset, keyLen);
      offset += keyLen;

      buf.put(values, offset, 4);
      buf.flip();
      final int valueLen = buf.getInt();
      buf.clear();
      offset += 4;

      if(fields == null || fields.contains(key)) {
        result.put(key, new ByteArrayByteIterator(values, offset, valueLen));
      }

      offset += valueLen;
    }

    return result;
  }
}
