package netplus.mall.api;

public class Urls {
    public static class BasicData{
        public static final String getClassifyList = "/api/mall/basicData/getClassifyList/";
        public static final String addClassifyData = "/api/mall/basicData/addClassifyData/";
        public static final String modifyClassifyData = "/api/mall/basicData/modifyClassifyData/";

        public static final String getMaterialList = "/api/mall/basicData/getMaterialList/";
        public static final String getSkuMaterialList = "/api/mall/basicData/getSkuMaterialList/";
        public static final String modifyMaterialData = "/api/mall/basicData/modifyMaterialData/";
        public static final String importErpCla = "/api/mall/basicData/importErpCla/";
        public static final String importTwoLevelCla = "/api/mall/basicData/importTwoLevelCla/";
        public static final String importThrplatProduct = "/api/mall/basicData/importThrplatProduct/";
        public static final String getCategoryByGoodsno = "/api/mall/basicData/getCategoryByGoodsno/";
        public static final String getAllMatrlList = "/api/mall/basicData/getAllMatrlList/";
        public static final String getMaterialInfo = "/api/mall/basicData/getMaterialInfo/";
        public static final String getCategoryNameList = "/api/mall/basicData/getCategoryNameList/";
        public static final String getOneLevelClaNameList = "/api/mall/basicData/getOneLevelClaNameList/";
        public static final String getTwoLevelClaNameList = "/api/mall/basicData/getTwoLevelClaNameList/";
        public static final String exportBasicDataClassifyList="/api/mall/basicData/exportBasicDataClassifyList/";
        public static final String getMatrlNoByGoodNo="/api/mall/basicData/getMatrlNoByGoodNo/";
        public static final String exportExcel="/api/mall/basicData1/exportExcel/";
        public static final String getBasicPicLib="/api/mall/basicData/getPicInfoList/";
        public static final String createBasicPicLib="/api/mall/basicData/CreatePicInfoList/";
        public static final String delBasicPic="/api/mall/basicData/delBasicPic/";


        public static final String syncBasicData="/api/mall/basicData/syncBasicData/";
        public static final String getMatrlByIdsAndSupplierNo="/api/mall/basicData/getMatrlByIdsAndSupplierNo/";
        public static final String getCheckOrderConfig="/api/mall/basicData/getCheckOrderConfig/";
        public static final String getCostDept="/api/mall/basicData/getCostDept/";
        public static final String getMatrlQualityByMatrlId="/api/mall/basicData/getMatrlQualityByMatrlId/";

    }

    public static class GoodsGroup{
        public static final String getGoodsGroupList = "/api/mall/goodsGroup/getGoodsGroupList/";
        public static final String getGroupGoodList = "/api/mall/goodsGroup/getGroupGoodList/";
        public static final String deleteGoodsGroup = "/api/mall/goodsGroup/deleteGoodsGroup/";
        public static final String deleteGoodsFromGroup = "/api/mall/goodsGroup/deleteGoodsFromGroup/";
        public static final String batchDeleteGoods = "/api/mall/goodsGroup/batchDeleteGoods/";
        public static final String createGroup = "/api/mall/goodsGroup/createGroup/";

    }
    public static class GoodsListed{
        public static final String exportGroupGoodsUpdate="/api/mall/GoodsListed/exportGroupGoodsUpdate/";
        public static final String getGoodsListedList = "/api/mall/GoodsListed/getGoodsListedList/";
        public static final String getTabCount = "/api/mall/GoodsListed/getTabCount/";
        public static final String getShelfCount = "/api/mall/GoodsListed/getShelfCount/";
        public static final String getGoodsDetail = "/api/mall/GoodsListed/getGoodsDetail/";
        public static final String getIndexGoodsList = "/api/mall/GoodsListed/getIndexGoodsList/";
        public static final String getSiteTop = "/api/mall/GoodsListed/getSiteTop/";
        public static final String batchUpdatePrice = "/api/mall/GoodsListed/batchUpdatePrice/";
        public static final String getImportGoodTemp = "/api/mall/GoodsListed/getImportGoodTemp/";
        public static final String importGoodInfo = "/api/mall/GoodsListed/importGoodInfo/";
        public static final String batchApproval = "/api/mall/GoodsListed/batchApproval/";
        public static final String batchMatchPic = "/api/mall/GoodsListed/batchMatchPic/";
        public static final String getUserViewHistory = "/api/mall/GoodsListed/getUserViewHistory/";
        public static final String addViewHistory = "/api/mall/GoodsListed/addViewHistory/";
        public static final String batchInsert = "/api/mall/GoodsListed/batchInsert/";
        public static final String exportGoodsListedList= "/api/mall/GoodsListed/exportGoodsListedList/";
        public static final String exportNewGoodsListedList= "/api/mall/GoodsListed/exportNewGoodsListedList/";
        public static final String exportCheckedGoodsListedList= "/api/mall/GoodsListed/exportCheckedGoodsListedList/";
        public static final String updateZkhGoodPrice= "/api/mall/GoodsListed/updateZkhGoodPrice/";
        public static final String updateZkhGoodQty= "/api/mall/GoodsListed/updateZkhGoodQty/";
        public static final String updateZkhGoodDetail= "/api/mall/GoodsListed/updateZkhGoodDetail/";
        public static final String updateGoodInvalid= "/api/mall/GoodsListed/updateGoodInvalid/";
        public static final String SetGoodRank= "/api/mall/GoodsListed/SetGoodRank/";
        public static final String batchUpdateApply="/api/mall/GoodsListed/batchUpdateApply/";
        public static final String batchConfirm="/api/mall/GoodsListed/batchConfirm/";
        public static final String exportConfirm="/api/mall/GoodsListed/exportConfirm/";
        public static final String getGoodBySupplierNo="/api/mall/GoodsListed/getGoodBySupplierNo/";
        public static final String updateZkhPic="/api/mall/GoodsListed/updateZkhPic/";
        public static final String updateZkhPriceAndDetail="/api/mall/GoodsListed/updateZkhPriceAndDetail/";
        public static final String priceCompare="/api/mall/GoodsListed/priceCompare/";
        public static final String delInvalidGood="/api/mall/GoodsListed/delInvalidGood/";
        public static final String export="/api/mall/GoodsListed/export/";


    }


    /*商品审核*/
    public static class GoodsAudit{
        public static final String batchAudit = "/api/mall/GoodsListed/batchAudit/";

    }
    /*
    收货人管理
     */
    public static final String commodityHarves = "/api/mall/commodityHarves/";
    /*
    收货人信息添加
     */
    public static final String addInformation = "/api/mall/addInformation/";
    /*
    收货人信息更改
     */
    public static final String updateInformation = "/api/mall/updateInformation/";

    /*
    收货人信息更改
     */
    public static final String deleteByCode = "/api/mall/deleteByCode/";
    /*商品图片管理*/
    public static class GoodsPicture{
        public static final String getGoodsPictureList = "/api/mall/GoodsPicture/getGoodPicList/";
        public static final String addGoodsPictureList = "/api/mall/GoodsPicture/addGoodsPictureList/";
    }

    public static class ShoppingCart {
        public static final String addShoppingCartFromOrder = "/api/mall/addShoppingCartFromOrder/";
        public static final String addShoppingCart = "/api/mall/addShoppingCart/";
        public static final String erpAddShoppingCart = "/api/mall/erpAddShoppingCart/";
        public static final String importShoppingCart = "/api/mall/importShoppingCart/";
        public static final String getShoppingCartCount = "/api/mall/getShoppingCartCount/";
        public static final String getMyShoppingCartList = "/api/mall/getMyShoppingCartList/";
        public static final String exportMyShoppingCartList="/api/mall/exportMyShoppingCartList/";
        public static final String delMyShoppingCart = "/api/mall/delMyShoppingCart/";
        public static final String exportErrInfo = "/api/mall/exportErrInfo/";
        public static final String updateProject = "/api/mall/updateProject/";
        public static final String batchUpdateProject = "/api/mall/batchUpdateProject/";
        public static final String updateDevice = "/api/mall/updateDevice/";
        public static final String updatePic = "/api/mall/updatePic/";
        public static final String getProject = "/api/mall/getProject/";
        public static final String getDevice = "/api/mall/getDevice/";
        public static final String checkQty = "/api/mall/checkQty/";
        public static final String updateShoppingCartRemark = "/api/mall/updateShoppingCartRemark/";
        public static final String updateDeviceSimpleNo = "/api/mall/updateDeviceSimpleNo/";




    }

    public static class Order {
        public static final String createOrder = "/api/mall/createOrder/";
        public static final String getOrderList = "/api/mall/getOrderList/";
        public static final String getOrderAndDetailList = "/api/mall/getOrderAndDetailList/";
        public static final String getMyWaitingTakeOrder = "/api/mall/getMyWaitingTakeOrder/";
        public static final String exportOrder = "/api/mall/exportOrder/";
        public static final String exportOrderDetail = "/api/mall/exportOrderDetail/";
        public static final String getOrderInfo = "/api/mall/getOrderInfo/";
        public static final String getTabCount = "/api/mall/getTabCount/";
        public static final String receivingOrder = "/api/mall/receivingOrder/";
        public static final String afterApproval = "/api/mall/afterApproval/";
        public static final String getApproveDetail = "/api/mall/getApproveDetail";
        public static final String syncOrderDetailSchedule = "/api/mall/syncOrderDetailSchedule/";
        public static final String viewApproveProgress = "/api/mall/viewApproveProgress/";
        public static final String getLineList = "/api/mall/getLineList/";
        public static final String orderDetailCancel = "/api/mall/orderDetailCancel/";
        public static final String syncInvoiceOrderDetailSchedule = "/api/mall/syncInvoiceOrderDetailSchedule/";
        public static final String getDeliveryAppointmentUrl = "/api/mall/getDeliveryAppointmentUrl/";
        public static final String getSprInfo = "/api/mall/getSprInfo/";
        public static final String invalidOrder = "/api/mall/invalidOrder/";
        public static final String orderNotReceiveWarn = "/api/mall/orderNotReceiveWarn/";
    }

    public static class GoodsEdit {
        public static final String getSupplierGoodEditInfo = "/api/mall/getSupplierGoodEditInfo/";
        public static final String save = "/api/mall/good/save/";
    }

    public static class PicLib {
        public static final String getPicLibList = "/api/mall/picLib/getPicLibList/";
        public static final String updatePicLib = "/api/mall/picLib/updatePicLib/";
        public static final String delPicLib = "/api/mall/picLib/delPicLib/";
        public static final String createPicLib = "/api/mall/picLib/createPicLib/";
        public static final String initBasicPic = "/api/mall/picLib/initBasicPic/";
        public static final String getPicByMatrlTmAndSupplierNo = "/api/mall/picLib/getPicByMatrlTmAndSupplierNo/";
        public static final String getPicByMatrlTm = "/api/mall/picLib/getPicByMatrlTm/";
    }

    public static class GoodsCollect {
        public static final String delMyCollect = "/api/mall/goodsCollect/delMyCollect/";
        public static final String addMyCollect = "/api/mall/goodsCollect/addMyCollect/";
        public static final String batchAddMyCollect = "/api/mall/goodsCollect/batchAddMyCollect/";
        public static final String getMyCollectList = "/api/mall/goodsCollect/getMyCollectList/";
        public static final String getMyCollectCount = "/api/mall/goodsCollect/getMyCollectCount/";
        public static final String updateCollectRemark = "/api/mall/goodsCollect/updateCollectRemark/";
    }
    public static class Mall {
        public static final String search = "/api/mall/search/";
        public static final String getAllCategory = "/api/mall/getAllCategory/";
        public static final String getGoodsHistory = "/api/mall/getGoodsHistory/";
    }

    public static class Advertising {
        public static final String listAdvertising = "/api/mall/advertising/list/";
        public static final String getPublishAdvertisingCount = "/api/mall/advertising/publishCount/";
        public static final String addAdvertising = "/api/mall/advertising/add/";
        public static final String publishAdvertising = "/api/mall/advertising/publish/";
        public static final String cancelPublishAdvertising = "/api/mall/advertising/cancelPublish/";
        public static final String removeAdvertising = "/api/mall/advertising/remove/";
        public static final String advertisingLeftMoveOne = "/api/mall/advertising/move/left/";
        public static final String advertisingRightMoveOne = "/api/mall/advertising/move/right/";
        public static final String getPublishAdvertising = "/api/mall/advertising/publishAdvertising/";
    }

    public static class GoodsComment {
        public static final String createComment = "/api/mall/goodsComment/createComment/";
        public static final String getCommentByOrderInfo = "/api/mall/goodsComment/getCommentByOrderInfo/";
        public static final String getCommentsByGoodsInfo = "/api/mall/goodsComment/getCommentsByGoodsInfo/";
    }

    public static class InitMatrlImg{
        public static final String initImage = "/api/mall/InitMatrlImg/initImage/";
        public static final String initBasicData3 = "/api/mall/InitMatrlImg/initBasicData3/";
        public static final String initErrorFormatImg = "/api/mall/InitMatrlImg/initErrorFormatImg/";
        public static final String initErrorFormatBasicData3 = "/api/mall/InitMatrlImg/initErrorFormatBasicData3/";
    }

    public static class GoodsInventory{
        public static final String getPage = "/api/mall/GoodsInventory/getPage/";
        public static final String getMallGoodsInventoryDetailPage = "/api/mall/GoodsInventory/getMallGoodsInventoryDetailPage/";
    }


    public static class Count{
        public static final String getCountPonoInfo = "/api/mall/count/getCountPonoInfo/";
        public static final String getCountGoodInfo = "/api/mall/count/getCountGoodInfo/";
        public static final String getPonoDetailInfo = "/api/mall/count/getPonoDetailInfo/";
        public static final String exportCountPonoInfo = "/api/mall/count/exportCountPonoInfo/";
        public static final String exportCountGoodInfo = "/api/mall/count/exportCountGoodInfo/";
        public static final String exportCountPonoDetailInfo = "/api/mall/count/exportCountPonoDetailInfo/";

        public static final String getTabCount = "/api/mall/count/getTabCount/";


    }

    public static class FutureGood{
        public static final String batchImport = "/api/mall/FutureGood/batchImport/";
    }



}
