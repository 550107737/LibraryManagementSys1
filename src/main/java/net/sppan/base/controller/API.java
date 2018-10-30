package net.sppan.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.CryptoUtil;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.config.consts.LabConsts;
import net.sppan.base.dao.BookDao;
import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

import static net.sppan.base.config.consts.LabConsts.BORROWABLE_CHECK_FULLY;
import static net.sppan.base.config.consts.LabConsts.BORROWABLE_CHECK_LOGIN;

@Controller
@RequestMapping("/API")
public class API extends BaseController {

    @Autowired
    private UserService userService;
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
    @Autowired
    private RepairBookcaseService repairBookcaseService;

    /**
     * @方法名: login
     * @功能描述: 用户刷卡开门状态
     * @创建人: 黄梓莘
     * @创建时间： 2018-10-11
     */
    @RequestMapping(value = {"/door/getDoorOpenStatus"})
    @ResponseBody
    public JsonResult login(@ModelAttribute("param") ParamModel param, @RequestParam String userId) {
        Date date = new Date();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setApi_flag(LabConsts.API_FLAG);
        jsonResult.setServer_time(date.getTime() + "");
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime() + "").concat(CryptoUtil.md5(userId)));
            jsonResult.setToken(serverAPIKey);

            // todo 参数校验,所有接口都要用
            String serverAPIKey1 = checkParam(param, userId);

            /// todo 查询用户是否有权限借书
            BorrowModel borrowModel = new BorrowModel();
            borrowModel.setUserId(userId);
            borrowService.checkUserBorrowable(borrowModel, BORROWABLE_CHECK_LOGIN);
            //将userModel放在data里供安卓使用
            jsonResult.setData(JSON.toJSONStringWithDateFormat(userService.findByUserId(userId),
                    "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat));
        } catch (Exception e) {
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        jsonResult.setResult(true);
        return jsonResult;
    }

    private String checkParam(ParamModel param, String userId) throws Exception {
        // 超时校验 Add 不校验了，书柜时间不同步是经常发生的。。。
        /*if (!verifyTimestamp(60, param.getTime())) {
            throw new Exception("操作超时！");
        }*/
        String serverAPIKeyContent;
        //todo 加密校驗
        if (userId == null || userId.equals("")) {
            serverAPIKeyContent = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(param.getTime());
        } else {
            serverAPIKeyContent = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(param.getTime()).concat(CryptoUtil.md5(userId));
        }
        String serverAPIKey = CryptoUtil.md5(serverAPIKeyContent);
        System.out.println(serverAPIKey);
        if (!param.getToken().equals(serverAPIKey)) {
            throw new Exception("校验错误！");
        }
        return serverAPIKey;
    }


    /**
     * @方法名: reportDoorCloseStatus
     * @功能描述: 书柜关门-获取借书或者还书状态，仅返回列表
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = {"/door/reportDoorCloseStatus"})
    @ResponseBody
    public JsonResult reportDoorCloseStatus(@ModelAttribute("param") ParamModel param, @RequestParam String userId, @RequestParam String jsonListStr) {
        Date date = new Date();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setApi_flag(LabConsts.API_FLAG);
        jsonResult.setServer_time(date.getTime() + "");
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime() + "").concat(CryptoUtil.md5(userId)));
            jsonResult.setToken(serverAPIKey);
            //todo 1. 校验 ，和开门登录的方法一样
            String serverAPIKey1 = checkParam(param, userId);
            /// todo 查询用户是否有权限借书
            BorrowModel borrowModel1 = new BorrowModel();
            borrowModel1.setUserId(userId);
            borrowService.checkUserBorrowable(borrowModel1, BORROWABLE_CHECK_FULLY);

            //todo 2. 将json格式的booklist转化为booklist
            BookcaseModel bookcaseModel = bookcaseService.findByBookcaseRfid(param.getBookcaseSN());
            List<BookboxModel> bookboxModels = bookboxService.findByBookcaseId(bookcaseModel.getBookcaseId());
            //根据书籍所属书柜ID和未借出状态统计数据库书籍数量
            List<BookModel> dbBookModels = bookDao.findAllByBookcaseIdAndBooksStatus(bookcaseModel.getBookcaseId(), 0);
            if (dbBookModels == null) {
                //一本书都没有
                dbBookModels = new ArrayList<BookModel>();
            }
            //获取书柜内书籍列表
            List<BookModel> bookModels = getBooksInBookcase(jsonListStr, bookboxModels);

            //todo 3. 判断多出书还是少了书籍（单独写一个方法，盘点的时候可以直接调用）

            List<BookModel> diff = new ArrayList<BookModel>();
            diff = getDifference(bookModels, dbBookModels);
            List<BookModel> borrowBooksData = new ArrayList<>();
            List<BookModel> returnBooksData = new ArrayList<>();
            if(diff.size()<=0){
                //差异为0代表没有书籍变动，直接返回
                jsonResult.setResult(true);
                return jsonResult;
            }
            for (BookModel bookModel : diff) {
                //等于0代表原来就在数据库中，所以只能是借走操作0
                if (bookModel.getBooksStatus() == 0) {
                        borrowBooksData.add(bookModel);
                } else {
                        returnBooksData.add(bookModel);
                }
            }
            String a = JSON.toJSONStringWithDateFormat(borrowBooksData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
            String b = JSON.toJSONStringWithDateFormat(returnBooksData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
            //jsonResult.setData(JsonUtil.map2json(map));
            jsonResult.setBorrowBooksData(a);
            jsonResult.setReturnBooksData(b);
            System.out.println(a);
            System.out.println(b);

        } catch (Exception e) {
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
            return jsonResult;
        }
        jsonResult.setResult(true);
        return jsonResult;
    }

    private List<BookModel> getBooksInBookcase(String jsonListStr, List<BookboxModel> bookboxModels) {
        List<BookModel> bookModels = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(jsonListStr);
        String[] jsonStr = new String[bookboxModels.size()];
        for (int i = 0; i < bookboxModels.size(); i++) {
            jsonStr[i] = jsonObject.getString(bookboxModels.get(i).getBoxRfid());
            if (jsonStr[i].equals("")) {
                continue;
            }
            String[] list1 = jsonStr[i].split(",");
            for (int j = 0; j < list1.length; j++) {
                BookModel bookModel = bookService.findByBookRfid(list1[j]);
                bookModels.add(bookModel);
            }
        }
        return bookModels;
    }

    /*
    用一个map存放list1的所有元素，其中的key为list1的各个元素，
    value为该元素出现的次数,接着把list2的所有元素也放到map里，如果已经存在则value加1，
    最后只要取出map里value为1的元素即可，
     */
    private List<BookModel> getDifference(List<BookModel> list1, List<BookModel> list2) {
        Map<BookModel, Integer> map = new HashMap<BookModel, Integer>(list1.size() + list2.size());
        List<BookModel> diff = new ArrayList<BookModel>();
        List<BookModel> bigList = list1;
        List<BookModel> smallList = list2;
        if (list1.size() < list2.size()) {
            bigList = list2;
            smallList = list1;
        }
        for (BookModel bookModel : bigList) {
            map.put(bookModel, 1);
        }
        for (BookModel bookModel : smallList) {
            Integer cc = map.get(bookModel);
            if (cc != null) {
                map.put(bookModel, ++cc);
                continue;
            }
            map.put(bookModel, 1);
        }
        for (Map.Entry<BookModel, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    /**
     * @方法名: checkDoorCloseStatus
     * @功能描述: 书柜关门-操作数据库更新书籍
     * @创建人: 黄梓莘
     * @创建时间： 2018-10-30
     */
    @RequestMapping(value = {"/door/checkDoorCloseStatus"})
    @ResponseBody
    public JsonResult checkDoorCloseStatus(@ModelAttribute("param") ParamModel param, @RequestParam String userId,@RequestParam String jsonListStr) {
        Date date = new Date();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setApi_flag(LabConsts.API_FLAG);
        jsonResult.setServer_time(date.getTime() + "");
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime() + "").concat(CryptoUtil.md5(userId)));
            jsonResult.setToken(serverAPIKey);
            //todo 1. 校验 ，和开门登录的方法一样
            String serverAPIKey1 = checkParam(param, userId);

            //todo 2. 将json格式的booklist转化为booklist
            BookcaseModel bookcaseModel = bookcaseService.findByBookcaseRfid(param.getBookcaseSN());
            List<BookboxModel> bookboxModels = bookboxService.findByBookcaseId(bookcaseModel.getBookcaseId());
            //根据书籍所属书柜ID和未借出状态统计数据库书籍数量
            List<BookModel> dbBookModels = bookDao.findAllByBookcaseIdAndBooksStatus(bookcaseModel.getBookcaseId(), 0);
            if (dbBookModels == null) {
                //一本书都没有
                dbBookModels = new ArrayList<BookModel>();
            }
            //获取书柜内书籍列表
            List<BookModel> bookModels = getBooksInBookcase(jsonListStr, bookboxModels);

            //todo 3. 判断多出书还是少了书籍（单独写一个方法，盘点的时候可以直接调用）

            List<BookModel> diff = new ArrayList<BookModel>();
            diff = getDifference(bookModels, dbBookModels);
            List<BookModel> borrowBooksData = new ArrayList<>();
            List<BookModel> returnBooksData = new ArrayList<>();
            if(diff.size()<=0){
                //差异为0代表没有书籍变动，直接返回
                jsonResult.setResult(true);
                return jsonResult;
            }
            // todo 4. 更新用户借阅记录--借书/还书表
            JSONObject jsonObject = JSON.parseObject(jsonListStr);
            String[] jsonStr = new String[bookboxModels.size()];
            for (int i = 0; i < bookboxModels.size(); i++) {
                jsonStr[i] = jsonObject.getString(bookboxModels.get(i).getBoxRfid());
            }

            for (BookModel bookModel : diff) {
                //等于0代表原来就在数据库中，所以只能是借走操作0
                if (bookModel.getBooksStatus() == 0) {
                    try {
                        BorrowModel borrowModel = new BorrowModel();
                        borrowModel.setUserId(userId);
                        borrowModel.setBookRfid(bookModel.getBookRfid());
                        borrowCtrl.addBorrowForAPI(borrowModel);
                        bookModel.setTradeSuccess(1);
                    } catch (Exception e) {
                        bookModel.setTradeSuccess(0);
                    } finally {
                        borrowBooksData.add(bookModel);
                    }
                } else {
                    try {
                        BorrowModel borrowModel = new BorrowModel();
                        borrowModel.setUserId(userId);
                        borrowModel.setBookRfid(bookModel.getBookRfid());
                        borrowModel.setActualBookcaseId(bookcaseModel.getBookcaseId());
                        //todo 获取该书还入的书箱信息
                        for (int i = 0; i < bookboxModels.size(); i++) {
                            if (jsonStr[i].contains(bookModel.getBookRfid())) {
                                borrowModel.setActualBoxId(bookboxModels.get(i).getBoxSid());
                                break;
                            }
                        }
                        borrowCtrl.returnBorrowForAPI(borrowModel, 0, "");
                        bookModel.setTradeSuccess(1);
                    } catch (Exception e) {
                        bookModel.setTradeSuccess(0);
                    } finally {
                        returnBooksData.add(bookModel);
                    }
                }
            }
            String a = JSON.toJSONStringWithDateFormat(borrowBooksData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
            String b = JSON.toJSONStringWithDateFormat(returnBooksData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
            //jsonResult.setData(JsonUtil.map2json(map));
            jsonResult.setBorrowBooksData(a);
            jsonResult.setReturnBooksData(b);
            System.out.println(a);
            System.out.println(b);

        } catch (Exception e) {
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
            return jsonResult;
        }
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
    @ResponseBody
    public JsonResult bookCheck(@ModelAttribute("param") ParamModel param, String jsonListStr) {
        Date date = new Date();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setApi_flag(LabConsts.API_FLAG);
        jsonResult.setServer_time(date.getTime() + "");
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime() + "");
            jsonResult.setToken(serverAPIKey);
            //todo 1. 校验 param，没有userId 用空字符串
            String serverAPIKey1 = checkParam(param, "");
            // todo 2.rfids是图书的rfid列表
            BookcaseModel bookcaseModel = bookcaseService.findByBookcaseRfid(param.getBookcaseSN());
            List<BookboxModel> bookboxModels = bookboxService.findByBookcaseId(bookcaseModel.getBookcaseId());
            //根据书籍所属书柜ID和未借出状态统计数据库书籍数量
            List<BookModel> dbBookModels = bookDao.findAllByBookcaseIdAndBooksStatus(bookcaseModel.getBookcaseId(), 0);
            if (dbBookModels == null) {
                //一本书都没有
                dbBookModels = new ArrayList<BookModel>();
            }
            //获取书柜内书籍列表
            List<BookModel> bookModels = getBooksInBookcase(jsonListStr, bookboxModels);
            ;
            //todo 3.判断书柜的书籍和数据库是否一致 ，判断多出书还是少了书籍（单独写一个方法，可以直接调用borrowBook 方法）
            List<BookModel> diff = new ArrayList<BookModel>();
            diff = getDifference(bookModels, dbBookModels);


            //todo 4,不一致的记录到书籍表里
            /*
            在库书籍盘点状态先由定时器全部置1，然后等待各个书柜调用API中reportGridInventoryData接口盘点，
            盘点异常的书籍在该接口中仅进行status置0操作，待全部盘点完成后定时器检测status为0
            的书籍插入bookscheck异常表。
             */
            if (diff.size() > 0) {
                for (BookModel bookModel : diff) {
                    //记录书籍未未盘点状态
                    bookModel.setCheckStatus(0);
                    bookModel.setCheckDate(date);
                    bookService.saveOrUpdate(bookModel);
                }
            }
            //todo 5.返回给书柜盘点是否成功执行
        } catch (Exception e) {
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
    @ResponseBody
    public JsonResult doorOverTime(@ModelAttribute("param") ParamModel param) {
        Date date = new Date();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setApi_flag(LabConsts.API_FLAG);
        jsonResult.setServer_time(date.getTime() + "");
        jsonResult.setRetry_after_seconds(0);
        try {
            //优先生成服务器token
            String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                    concat(date.getTime() + "");
            jsonResult.setToken(serverAPIKey);
            //todo 1. 校验参数
            String serverAPIKey1 = checkParam(param, "");
            //todo 2.记录到database里
            BookcaseModel bookcaseModel = bookcaseService.findByBookcaseRfid(param.getBookcaseSN());
            if (bookcaseModel == null) {
                throw new Exception("未找到未关门书柜");
            }
            RepairBookcaseModel repairBookcaseModel = new RepairBookcaseModel();
            repairBookcaseModel.setBookcaseRfid(bookcaseModel.getBookcaseRfid());
            repairBookcaseModel.setBrokenReason("自动记录：超时5分钟未关闭书柜门");
            repairBookcaseModel.setBrokenTime(date);
            repairBookcaseModel.setUpdateTime(date);
            repairBookcaseModel.setIsRepair(1);//0是1否
            repairBookcaseService.saveOrUpdate(repairBookcaseModel);
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
    @ResponseBody
    public JsonResult malfunction(@ModelAttribute("param") ParamModel param) {
        // todo 先放着，需求待确认
        try {
            //设置书柜故障
//            BookcaseModel bookcaseModel = bookcaseService.find(Integer.valueOf(bookcaseId));
//            if (bookcaseModel == null) {
//                throw new Exception("未找到故障书柜");
//            }
//            bookcaseModel.setBrokenTime(new Date());
//            bookcaseModel.setIsBroken(1);
//            bookcaseService.saveOrUpdate(bookcaseModel);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }


    private boolean verifyTimestamp(int seconds, String timestamp) {
        Date currentTime = new Date();
        long currentTimestamp = currentTime.getTime();
        long oldTimestamp = Long.parseLong(timestamp);
        if (currentTimestamp - oldTimestamp > seconds * 1000) {
            return false;
        }
        return true;
    }


    private String getParamMD5(List paramList) {
        String tempStr = "";
        for (int i = 0; i < paramList.size(); i++) {
            tempStr += paramList.get(i);
        }
        return MD5Utils.md5(tempStr);
    }
}
