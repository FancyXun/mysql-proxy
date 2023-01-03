package dao;

public class SQLResponse extends Response {
    public SQLResultData getData() {
        return data;
    }

    public void setData(SQLResultData data) {
        this.data = data;
    }

    public SQLResultData data;
}
