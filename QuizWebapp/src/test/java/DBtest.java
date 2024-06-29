import DBpackage.*;
import DBpackage.Quiz;
import DBpackage.User;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;


public class DBtest extends TestCase{
    DatabaseAccess dbCall;
    protected void setUp() throws Exception {
        super.setUp();
        dbCall = new DatabaseAccess();


    }
    public void test1() throws SQLException {
        boolean ans;
        ans = dbCall.login("john_doe","password123");
        assertTrue(ans);
        ans = dbCall.login("john_doe","password13");
        assertFalse(ans);
    }
    public void test2() throws SQLException{
        User user;
        user = dbCall.getUserInfo("charlie_black");
        assertEquals(user.getUser_id(),5);
        assertEquals(user.getQuizzes_taken(),20);
        user = dbCall.getUserInfo("hi");
        assertNull(user);
    }
    public void test3() throws SQLException{
        boolean added;
        added = dbCall.addUser("a","b");
        assertTrue(added);
        added = dbCall.addUser("c","d");
        assertTrue(added);
        added = dbCall.addUser("a","d");
        assertFalse(added);

    }
    public void test4() throws SQLException{
        boolean added;
        added = dbCall.updatePicture("asddf","picture.jpg");
        assertFalse(added);
        added = dbCall.updatePicture("john_doe","picture.jpg");
        assertTrue(added);
    }
    public void testGetNewestQuiz() throws SQLException{
        ArrayList<Quiz> ls;
        ls=dbCall.getNewestQuiz(0);
        assertEquals(ls.size(),5);
        ls=dbCall.getNewestQuiz(3);
        assertEquals(ls.size(),3);
        assertEquals(ls.get(0).getQuiz_id(),1);
        assertEquals(ls.get(1).getQuiz_id(),5);
        assertEquals(ls.get(2).getQuiz_id(),4);
    }
    public void testGetQuizInfo()throws SQLException{
        Quiz qz;
        qz= dbCall.getQuizInfo(1);
        assertEquals(qz.getName(),"General Knowledge");
        qz= dbCall.getQuizInfo(6);
        assertNull(qz);
        qz= dbCall.getQuizInfo(5);
        assertEquals(qz.getTimesTaken(),3);

    }


}
