package net.sppan.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.istack.internal.Nullable;
import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.CryptoUtil;
import net.sppan.base.common.utils.EncryUtil;
import net.sppan.base.common.utils.JsonUtil;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.config.consts.LabConsts;
import net.sppan.base.dao.BookDao;
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
import java.util.*;

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

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BorrowCtrl borrowCtrl;
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

    private String checkParam(ParamModel param, String userId) throws Exception {
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
            int difference=checkBorrowOrReturn(param.getBookcaseSN(),bookModels.size());//书柜现有书籍数量减去数据库数量
            List<BookModel> dbBookModels=bookDao.findAllByInBoxAndCheckStatus(0,0);

            List<BookModel> diff=new ArrayList<BookModel>();
            if(diff.size()>0){
                jsonResult.setData(JsonUtil.list2json(diff));
            }else{
                //差异为0代表没有书籍变动，直接返回，默认借书状态
                jsonResult.setBorrowStatus(0);
                jsonResult.setStatus(1);
                jsonResult.setResult(true);
                return jsonResult;
            }
            BorrowModel borrowModel=new BorrowModel();
            borrowModel.setUserId(userId);
            // todo 4. 更新用户借阅记录--借书/还书表
            if(difference>0){//现有书籍比数据库里的多，代表还书
                //还书
                jsonResult.setBorrowStatus(1);
                diff=getDifference(bookModels,dbBookModels);//第一个参数数量比第二个要大
                for(BookModel bookModel:diff){
                    borrowModel.setBookRfid(bookModel.getBookRfid());
                    borrowCtrl.returnBorrow(borrowModel,0,"应有地点！");
                }
            }else{
                //借书
                jsonResult.setBorrowStatus(0);
                diff=getDifference(dbBookModels,bookModels);
                for(BookModel bookModel:diff){
                    borrowModel.setBookRfid(bookModel.getBookRfid());
                    borrowCtrl.addBorrow(borrowModel);
                }
            }

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

    /*
    用一个map存放list1的所有元素，其中的key为list1的各个元素，
    value为该元素出现的次数,接着把list2的所有元素也放到map里，如果已经存在则value加1，
    最后只要取出map里value为1的元素即可，
     */
    private List<BookModel> getDifference(List<BookModel> bigList, List<BookModel> smallList) {
        Map<BookModel,Integer> map = new HashMap<BookModel,Integer>(bigList.size()+smallList.size());
        List<BookModel> diff = new ArrayList<BookModel>();

        for (BookModel bookModel : bigList) {
            map.put(bookModel, 1);
        }
        for (BookModel bookModel : smallList) {
            Integer cc = map.get(bookModel);
            if(cc!=null) {
                map.put(bookModel, ++cc);
                continue;
            }
            map.put(bookModel, 1);
        }
        for(Map.Entry<BookModel, Integer> entry:map.entrySet()) {
            if(entry.getValue()==1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    private int checkBorrowOrReturn(String bookcaseSN,int existingBookNum) {
        List<BookModel> dbBookModels=bookDao.findAllByInBoxAndCheckStatus(0,0);
        return existingBookNum-dbBookModels.size();
    }


    /**
     * @方法名: bookCheck
     * @功能描述: 图书盘点
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/door/reportGridInventoryData"})
    public JsonResult bookCheck(ParamModel param, String  gridsn, String rfids) {
        Date date=new Date();
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(date.getTime());
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime());
            jsonResult.setToken(serverAPIKey);
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
    public JsonResult doorOverTime(ParamModel param) {
        Date date=new Date();
        JsonResult jsonResult=new JsonResult();
        jsonResult.setApi_flag("summerdeer");
        jsonResult.setServer_time(new Date().getTime());
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime());
            jsonResult.setToken(serverAPIKey);

            //todo 1. 校验参数
            String serverAPIKey1 = checkParam(param,"");
            //todo 2.记录到database里
            BookcaseModel bookcaseModel = bookcaseService.findByBookcaseRfid(param.getBookcaseSN());
            if (bookcaseModel == null) {
                throw new Exception("未找到未关门书柜");
            }

            //todo 3.等待微信接口



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
