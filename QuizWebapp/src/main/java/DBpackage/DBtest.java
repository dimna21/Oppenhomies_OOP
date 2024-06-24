package DBpackage;

import DBpackage.DatabaseAccess;
import junit.framework.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//first, run metropolisesTest, then run this!!!
public class DBtest extends TestCase{
    DatabaseAccess dbCall;
    protected void setUp() throws Exception {
        super.setUp();
        dbCall = new DatabaseAccess();


    }
    //can only run 1 test because tests do not run sequentially, which messes things up.
    public void test2() throws SQLException {
        boolean ans;
        ans = dbCall.login("john_doe","password123");
        assertEquals(true,ans);
        ans = dbCall.login("john_doe","password13");
        assertEquals(false,ans);
    }
    public void test3() throws SQLException{
        User user;
        user = dbCall.getUserInfo("charlie_black");
        assertEquals(user.getUser_id(),5);
        assertEquals(user.getQuizzes_taken(),20);
        user = dbCall.getUserInfo("hi");
        assertNull(user);
    }


}
