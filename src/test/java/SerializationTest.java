import com.alibaba.fastjson.JSON;
import dao.QueryRequest;

public class SerializationTest {

    public static void main(String[] args){
        String jsonStr = "{\"data\":{\"columns\":[\"Gba17a101494ddc7db1a97fbe15df4323\",\"Cfca5348a1d49889042931eec7a16fdb9\",\"o9222b45ef0957e70e619ea38aff6d5ce\"],\"rows\":[[\"4rfM+O7xkjZcP1vrC2z6JQ==\",\"LUEO+Tu6e43qbc3aFlhPEeuLnUdhLarAVNBgguMZ0Lw=\",\"Zx2O2tLmdnxXcDuq2g3vEw==\"]]},\"queryId\":\"1674011745351\"}\n";
        QueryRequest queryRequest = JSON.parseObject(jsonStr,QueryRequest.class);
        System.out.println(queryRequest.queryId);
    }
}
