package controller;

import net.sppan.base.Application;
import net.sppan.base.common.utils.CryptoUtil;
import net.sppan.base.config.consts.LabConsts;
import net.sppan.base.controller.API;
import net.sppan.base.entity.ParamModel;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * API Tester.
 *
 * @author <Ada >
 * @version 1.0
 * @since <pre>11/01/2018</pre>
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APITest {
    @Autowired
    private API api;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: login(@ModelAttribute("param") ParamModel param, @RequestParam String userId)
     */
    @Test
    public void testLogin() throws Exception {
//TODO: Test goes here..
        ParamModel param = new ParamModel();
        Date date = new Date();
        param.setTime(date.getTime() + "");
        String userId = "admin";
        param.setBookcaseSN("10000003");
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        System.out.println(serverAPIKey);
        api.login(param, userId);


    }

    /**
     * Method: reportDoorCloseStatus(@ModelAttribute("param") ParamModel param, @RequestParam String userId, @RequestParam String jsonBooksRfid)
     */
    @Test
    public void testReportDoorCloseStatus() throws Exception {
//TODO: Test goes here...
        String jsonListStr = "{\"20000007\":\"00000002\",\"20000008\":\"00000005,00000008\",\"20000009\":\"00000007,00000006\"}";
        ParamModel param = new ParamModel();
        Date date = new Date();
        param.setTime(date.getTime() + "");
        String userId = "admin";
        param.setBookcaseSN("10000003");
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        System.out.println(serverAPIKey);
        api.reportDoorCloseStatus(param, "admin", jsonListStr);
    }

    /**
     * Method: checkDoorCloseStatus(@ModelAttribute("param") ParamModel param, @RequestParam String userId, @RequestParam String borrow_books_data, @RequestParam String return_books_data)
     */
    @Test
    public void testCheckDoorCloseStatus() throws Exception {
//TODO: Test goes here...
        String testBorrow = "[]";
        String testReturn = "[{\"amount\":999,\"authors\":\"02348\",\"bookName\":\"springd\",\"bookRfid\":\"00000006\",\"bookTime\":\"2018-08-03 10:01:23\",\"bookcaseId\":20,\"booksId\":11,\"booksPosition\":\"图书馆还书地\",\"booksStatus\":1,\"boxId\":3,\"checkDate\":\"2018-08-21 10:02:18\",\"checkStatus\":0,\"classification\":\"军事\",\"description\":\"6879789\",\"document\":\"3289749827\",\"imgurl\":\"234\",\"inBox\":0,\"isbn\":\"3294239847\",\"language\":\"spring\",\"publication\":\"302948\",\"publicationTime\":\"2018-08-12 00:00:00\",\"repayTime\":\"2018-11-30 00:00:00\",\"updateTime\":\"2018-10-31 09:00:49\"}]";
        ParamModel param = new ParamModel();
        Date date = new Date();
        param.setTime(date.getTime() + "");
        String userId = "admin";
        param.setBookcaseSN("10000003");
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        System.out.println(serverAPIKey);
        api.checkDoorCloseStatus(param, "admin", testBorrow, testReturn);

    }

    /**
     * Method: bookCheck(@ModelAttribute("param") ParamModel param, @RequestParam  String jsonBooksRfid)
     */
    @Test
    public void testBookCheck() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: doorOverTime(@ModelAttribute("param") ParamModel param)
     */
    @Test
    public void testDoorOverTime() throws Exception {
//TODO: Test goes here...
        ParamModel param = new ParamModel();
        Date date = new Date();
        param.setTime(date.getTime() + "");
        param.setBookcaseSN("10000003");
        String userId = "admin";
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        api.doorOverTime(param);
    }

    /**
     * Method: malfunction(@ModelAttribute("param") ParamModel param)
     */
    @Test
    public void testMalfunction() throws Exception {
//TODO: Test goes here... 
    }


}
