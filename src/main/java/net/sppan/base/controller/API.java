package net.sppan.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.CryptoUtil;
import net.sppan.base.common.utils.EncryUtil;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.config.consts.LabConsts;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.ParamModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.BookcaseService;
import net.sppan.base.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/API")
public class API extends BaseController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private BookcaseService bookcaseService;

    /**
     * @方法名: login
     * @功能描述: 用户刷卡开门状态
     * @创建人: 黄梓莘
     * @创建时间： 2018-10-11
     */
    @RequestMapping(value = {"/door/getDoorOpenStatus"})
    public JsonResult login(ParamModel param,String userId) {
        Date date=new Date();
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(date.getTime());
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime()).concat(CryptoUtil.md5(userId));
            jsonResult.setToken(serverAPIKey);

            // todo 参数校验,所有接口都要用
            String serverAPIKey1 = checkParam(param,userId);

            /// todo 查询用户是否有权限借书
            BorrowModel borrowModel = new BorrowModel();
            borrowModel.setUserId(userId);
            borrowService.checkUserBorrowable(borrowModel, 0);
        } catch (Exception e) {
            jsonResult.setStatus(0);
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        jsonResult.setStatus(1);
        jsonResult.setResult(true);
        return jsonResult;
    }

    private String checkParam(ParamModel param,String userId) throws Exception {
        //todo 超时校验
        if (!verifyTimestamp(60, param.getTime())) {
            throw new Exception("操作超时！");
        }
        //todo 加密校驗
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        if (param.getToken() != CryptoUtil.md5(serverAPIKey)){
            throw new Exception("校验错误！");
        }
        return serverAPIKey;
    }


    /**
     * @方法名: borrowBook
     * @功能描述: 书柜关门-获取借书或者还书状态
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/door/reportDoorCloseStatus"})
    public JsonResult borrowBook(ParamModel param, String userId, String jsonBookListStr) {
        Date date=new Date();
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(date.getTime());
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime()).concat(CryptoUtil.md5(userId));
            jsonResult.setToken(serverAPIKey);

            //todo 1. 校验 ，和开门登录的方法一样
            String serverAPIKey1 = checkParam(param,userId);

            //todo 2. 将json格式的booklist转化为booklist
            List<BookModel> bookModels = new ArrayList<BookModel>();
            bookModels = JSON.parseArray(jsonBookListStr, BookModel.class);

            //todo 3. 判断多出书还是少了书籍（单独写一个方法，盘点的时候可以直接调用）

            // todo 4. 更新用户借阅记录--借书/还书表

            //todo 4.返回APP：token、status、少的书籍list，或者多的list


        } catch (Exception e) {
            jsonResult.setStatus(0);
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        jsonResult.setStatus(1);
        jsonResult.setResult(true);
        return jsonResult;
    }



    /**
     * @方法名: bookCheck
     * @功能描述: 图书盘点
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/door/reportGridInventoryData"})
    public JsonResult bookCheck(ParamModel param, String  gridsn, String rfids) {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(new Date().getTime());
        jsonResult.setRetry_after_seconds(0);
        try {

            //todo 1. 校验 param，没有userId 用空字符串
            String serverAPIKey1 = checkParam(param,"");


            // todo 2.rfids是图书的rfid列表

            //todo 3.判断书柜的书籍和数据库是否一致 ，判断多出书还是少了书籍（单独写一个方法，可以直接调用borrowBook 方法）

            //todo 4,不一致的记录到书籍表里

            //todo 5.返回给书柜盘点是否成功执行


            /*
            String params[] = checkAndDecryptParam(param, 3);
            String secretKey = params[0];
            String bookRfid = params[1];
            String timestamp = params[2];
            if (!secretKey.equals("bookCheck")) {
                throw new Exception("秘钥不匹配！");
            }
            if (!verifyTimestamp(60, timestamp)) {
                throw new Exception("操作超时！");
            }
            String bookRfids[] = bookRfid.split("\\*");
            Date date = new Date();
            for (int i = 0; i < bookRfids.length; i++) {
                BookModel bookModel = bookService.findByBookRfid(bookRfids[i]);
                if (bookModel == null) {
                    continue;
                }
                bookModel.setCheckStatus(1);
                bookModel.setCheckDate(date);
                bookService.saveOrUpdate(bookModel);
            }*/
        }catch (Exception e) {
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        jsonResult.setResult(true);
        return jsonResult;
    }



    /**
     * @方法名: doorOverTime
     * @功能描述: 自动报警：柜门超过5分钟未关报警
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/door/sendAlarmData"})
    public JsonResult doorOverTime(String param) {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(new Date().getTime());
        jsonResult.setRetry_after_seconds(0);
        try {

            //todo 1. 校验参数

            //todo 2.记录到database里

            //todo 3.等待微信接口
            /*String params[] = checkAndDecryptParam(param, 3);
            String secretKey = params[0];
            String bookcaseId = params[1];
            String timestamp = params[2];
            if (!secretKey.equals("doorOverTime")) {
                throw new Exception("秘钥不匹配！");
            }
            if (!verifyTimestamp(20, timestamp)) {
                throw new Exception("操作超时！");
            }
            BookcaseModel bookcaseModel = bookcaseService.find(Integer.valueOf(bookcaseId));
            if (bookcaseModel == null) {
                throw new Exception("未找到未关门书柜");
            }*/
            //以下等待微信通知接口

        } catch (Exception e) {
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        jsonResult.setResult(true);
        return jsonResult;
    }

    /**
     * @方法名: malfunction
     * @功能描述: 故障
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/malfunction"})
    public JsonResult malfunction(String param) {
        // todo 先放着，需求待确认
        try {
            String params[] = checkAndDecryptParam(param, 3);
            String secretKey = params[0];
            String bookcaseId = params[1];
            String timestamp = params[2];
            if (!secretKey.equals("malfunction")) {
                throw new Exception("秘钥不匹配！");
            }
            if (!verifyTimestamp(20, timestamp)) {
                throw new Exception("操作超时！");
            }
            //设置书柜故障
            BookcaseModel bookcaseModel = bookcaseService.find(Integer.valueOf(bookcaseId));
            if (bookcaseModel == null) {
                throw new Exception("未找到故障书柜");
            }
            bookcaseModel.setBrokenTime(new Date());
            bookcaseModel.setIsBroken(1);
            bookcaseService.saveOrUpdate(bookcaseModel);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }


    private boolean verifyTimestamp(int seconds, String timestamp) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTimeStr = formatter.format(currentTime);
        Integer currentTimestamp = Integer.valueOf(currentTimeStr);
        Integer oldTimestamp = Integer.valueOf(timestamp);
        if (currentTimestamp - oldTimestamp > seconds) {
            return false;
        }
        return true;
    }

    private String[] checkAndDecryptParam(String param, int paramNum) throws Exception {
        if (param == null || param.equals("")) {
            throw new Exception("未获得正确的参数！");
        }
        String decryptParam = EncryUtil.decrypt(param);
        String params[] = decryptParam.split(",");
        if (params.length != paramNum) {
            throw new Exception("未获得正确的参数！");
        }
        return params;
    }

    private String getParamMD5(List paramList) {
        String tempStr = "";
        for (int i = 0; i < paramList.size(); i++) {
            tempStr += paramList.get(i);
        }
        return MD5Utils.md5(tempStr);
    }
}
