package zula.common.util;

public class AbstractClient {
    private SQLManager sqlManager;
    private CollectionManager collectionManager;
    private int userId;
    public AbstractClient(SQLManager sqlManager, CollectionManager collectionManager) {
        this.sqlManager = sqlManager;
        this.collectionManager = collectionManager;
    }
    public SQLManager getSqlManager() {
        return sqlManager;
    }
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
