package dao;

import com.alibaba.fastjson.annotation.JSONField;

public class SQLResponse {
    public String encryptSql;
    public String table;
    @JSONField(name = "id")
    public String queryId;
}
