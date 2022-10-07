package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.Address;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.OrdersMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.repository.*;
import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepo ordersRepo;
    private final OrdersAddressRepo ordersAddressRepo;
    private final CustomerRepo customerRepo;
    private final ShopProductOptionRepo shopProductOptionRepo;
    private final OrdersGsRepo ordersGsRepo;
    private final PaymentRepo paymentRepo;

    private final ProductRepo productRepo;
    private final ProcessNeedRepo processNeedRepo;
    private final ProcessSpecRepo processSpecRepo;

    private final CheckGoodsRepo checkGoodsRepo;
    private final GoodsRepo goodsRepo;
    private final DeliveryNumRepo deliveryNumRepo;

    private final ProcessMapper processMapper;
    private final ChecksMapper checksMapper;
    private final AfterServiceRepo afterServiceRepo;
    private final OrderGsDnRepo orderGsDnRepo;
    private final CompanyRepo companyRepo;

    @Transactional
    public String sabangOrder(String startDate, String endDate){

        try {
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);

            Element exInformation = doc.createElement("SABANG_ORDER_LIST");
            doc.appendChild(exInformation);
            Element header = doc.createElement("HEADER");
            Element data = doc.createElement("DATA");

            Element SEND_COMPAYNY_ID = doc.createElement("SEND_COMPAYNY_ID");
            Element SEND_AUTH_KEY = doc.createElement("SEND_AUTH_KEY");
            Element SEND_DATE = doc.createElement("SEND_DATE");
            SEND_COMPAYNY_ID.appendChild(doc.createTextNode("juncmall12"));
            SEND_AUTH_KEY.appendChild(doc.createTextNode("JJx6FM6VHPJP6TGFZuFCNdFT2MEMbx6A0"));
            SEND_DATE.appendChild(doc.createTextNode(formatDate));

            header.appendChild(SEND_COMPAYNY_ID);
            header.appendChild(SEND_AUTH_KEY);
            header.appendChild(SEND_DATE);

            exInformation.appendChild(header);

            Element ORD_ST_DATE = doc.createElement("ORD_ST_DATE");
            Element ORD_ED_DATE = doc.createElement("ORD_ED_DATE");
            Element ORD_FIELD = doc.createElement("ORD_FIELD");
            Element JUNG_CHK_YN2 = doc.createElement("JUNG_CHK_YN2");
            Element ORDER_ID = doc.createElement("ORD_ID");
            Element MALL_ID = doc.createElement("MALL_ID");
            Element ORDER_STATUS = doc.createElement("ORDER_STATUS");
            Element LANG = doc.createElement("LANG");
            Element PARTNER_ID = doc.createElement("PARTNER_ID");
            Element MALL_USER_ID = doc.createElement("MALL_USER_ID");
            Element DPARTNER_ID = doc.createElement("DPARTNER_ID");
            ORD_ST_DATE.appendChild(doc.createTextNode(startDate));
            ORD_ED_DATE.appendChild(doc.createTextNode(endDate));
            ORD_FIELD.appendChild(doc.createTextNode("IDX|ORDER_ID|MALL_ID|MALL_USER_ID|MALL_USER_ID2|ORDER_STATUS|USER_ID|USER_NAME|USER_TEL|USER_CEL|USER_EMAIL|RECEIVE_TEL|RECEIVE_CEL|RECEIVE_EMAIL|DELV_MSG|RECEIVE_NAME|RECEIVE_ZIPCODE|RECEIVE_ADDR|TOTAL_COST|PAY_COST|ORDER_DATE|PARTNER_ID|DPARTNER_ID|MALL_PRODUCT_ID|PRODUCT_ID|SKU_ID|P_PRODUCT_NAME|P_SKU_VALUE|PRODUCT_NAME|SALE_COST|MALL_WON_COST|WON_COST|SKU_VALUE|SALE_CNT|DELIVERY_METHOD_STR|DELV_COST|COMPAYNY_GOODS_CD|SKU_ALIAS|BOX_EA|JUNG_CHK_YN|MALL_ORDER_SEQ|MALL_ORDER_ID|ETC_FIELD3|ORDER_GUBUN|P_EA|REG_DATE|ORDER_ETC_1|ORDER_ETC_2|ORDER_ETC_3|ORDER_ETC_4|ORDER_ETC_5|ORDER_ETC_6|ORDER_ETC_7|ORDER_ETC_8|ORDER_ETC_9|ORDER_ETC_10|ORDER_ETC_11|ORDER_ETC_12|ORDER_ETC_13|ORDER_ETC_14|ord_field2|copy_idx|GOODS_NM_PR|GOODS_KEYWORD|ORD_CONFIRM_DATE|RTN_DT|CHNG_DT|DELIVERY_CONFIRM_DATE|CANCEL_DT|CLASS_CD1|CLASS_CD2|CLASS_CD3|CLASS_CD4|BRAND_NM|DELIVERY_ID|INVOICE_NO|HOPE_DELV_DATE|FLD_DSP|INV_SEND_MSG|MODEL_NO|SET_GUBUN|ETC_MSG|DELV_MSG1|MUL_DELV_MSG|BARCODE|INV_SEND_DM|DELIVERY_METHOD_STR2|FREE_GIFT"));

            data.appendChild(ORD_ST_DATE);
            data.appendChild(ORD_ED_DATE);
            data.appendChild(ORD_FIELD);
            data.appendChild(JUNG_CHK_YN2);
            data.appendChild(ORDER_ID);
            data.appendChild(MALL_ID);
            data.appendChild(ORDER_STATUS);
            data.appendChild(LANG);
            data.appendChild(PARTNER_ID);
            data.appendChild(MALL_USER_ID);
            data.appendChild(DPARTNER_ID);


            exInformation.appendChild(data);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
            transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new FileOutputStream(new File("D://order.xml")));
            transformer.transform(source, result);

            File file = new File("D://order.xml");

            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileXml = "xml/"+file.getName()+"_"+now;
            String loc= NewperStorage.uploadFile(AdminBucket.OPEN, fileXml, new FileInputStream(file), file.length(), Files.probeContentType(file.toPath()));

            Response response = null;

            try {
                OkHttpClient client1 = new OkHttpClient.Builder().build();
                FormBody.Builder fb = new FormBody.Builder();

                Request req = new Request.Builder().url("http://r.sabangnet.co.kr/RTL_API/xml_order_info.html?xml_url="+loc).post(fb.build()).build();
                response = client1.newCall(req).execute();
                String str = response.body().string().toString();
                JSONObject json = XML.toJSONObject(str);

                JSONObject obj1 = (JSONObject)json.get("SABANG_ORDER_LIST");
                JSONArray array = (JSONArray)obj1.get("DATA");
                int[] ak = {19,100,200,300,400};


                System.out.println(array.length());
                for(int i=0; i<ak.length; i++){
                    JSONObject obj = (JSONObject)array.get(ak[i]);
                    System.out.println(obj);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (response != null)
                        response.close();
                } catch (Exception ee) {
                }
            }


        }catch (Exception e) {

        }

        return "";
    }



    /**주문통합관리 등록*/
    @Transactional
    public Long orderSave(ParamMap paramMap){
        System.out.println(paramMap.getMap());
        if(paramMap.get("O_SHOP_IDX").equals("")){
            paramMap.remove("O_SHOP_IDX");
        }
        OrderAddress orderAddress = paramMap.mapParam(OrderAddress.class);
        Address address = paramMap.mapParam(Address.class);
        orderAddress.setAddress(address);
        orderAddress.setAdEntrance("");
        ordersAddressRepo.save(orderAddress);
        Orders orders = paramMap.mapParam(Orders.class);
        paramMap.remove("O_IDX");
        orders.setOrderAddress(orderAddress);
        orders.setPayment(null);
        if(paramMap.get("CU_IDX").equals("")){
            orders.setCustomer(null);
        }else{
            Customer customer = customerRepo.getReferenceById(paramMap.getLong("CU_IDX"));
            orders.setCustomer(customer);
        }
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        orders.setOCode("code"+formatDate);
        String o_date = paramMap.get("O_DATE")+"";
        if(!o_date.equals("")){
            LocalDate oDate = LocalDate.parse(o_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime oTime = LocalTime.now();
            orders.setODate(oDate);
            orders.setOTime(oTime);
        }
        orders.setOMemo("");
        ordersRepo.save(orders);
        Long o_idx = orders.getOIdx();
        int price = 0;
        int delivery = 0;
        int mileage = 0;
        for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if(entry.getKey().contains("OG_COUPON_")) {
                String spoIdx = entry.getKey().replace("OG_COUPON_", "");
                int spo_idx = Integer.parseInt(spoIdx);
                ShopProductOption shopProductOption = shopProductOptionRepo.getReferenceById(spo_idx);
                OrderGs orderGs = paramMap.mapParam(OrderGs.class);
//                orderGs.setOrders(orders);
                orderGs.setShopProductOption(shopProductOption);

                orderGs.setOgPrice(shopProductOption.getSpoPrice());
                orderGs.setOgPoint(Integer.parseInt(paramMap.onlyNumber("OG_POINT_"+spo_idx)));
                orderGs.setOgMileage(Integer.parseInt(paramMap.onlyNumber("OG_MILEAGE_"+spo_idx)));
                orderGs.setOgCouponPrice(Integer.parseInt(paramMap.onlyNumber("OG_COUPON_"+spo_idx)));

                price = price + shopProductOption.getSpoPrice();
                delivery = delivery + shopProductOption.getGoodsStock().getProduct().getCompanyDelivery().getCdFee();
                ordersGsRepo.save(orderGs);

                mileage = mileage + (int)((shopProductOption.getSpoPrice()*shopProductOption.getShopProductAdd().getShopProduct().getSpPercent())/100);
            }
        }

        Payment payment = paramMap.mapParam(Payment.class);
        payment.setPayTotal(0);
        payment.setPayPrice(price);
        payment.setPayDelivery(delivery);
        payment.setPayMileage(mileage);
        payment.setPayJson(null);
        paymentRepo.save(payment);

        orders.setPayment(payment);
        ordersRepo.save(orders);



        return o_idx;
    }

    /**주문통합관리 상세 수정*/
    @Transactional
    public Long orderUpdate(ParamMap paramMap){
        OrderAddress oriOrderAddress = ordersRepo.getReferenceById(paramMap.getLong("O_IDX")).getOrderAddress();
        OrderAddress orderAddress = paramMap.mapParam(OrderAddress.class);
        Address address = paramMap.mapParam(Address.class);
        orderAddress.setAdIdx(oriOrderAddress.getAdIdx());
        orderAddress.setAddress(address);
        orderAddress.setAdEntrance(oriOrderAddress.getAdEntrance());

        ordersAddressRepo.save(orderAddress);
        return orderAddress.getAdIdx();
    }


    /** 송장등록 */
    @Transactional
    public String insertInvoice(ParamMap paramMap) {
        List<Integer> list = paramMap.getList("ogIdxs[]");
        int cnt = 0;
        System.out.println(list);
        for(int i=0; i<list.size(); i++){
            OrderGs orderGs = ordersGsRepo.findById(Long.parseLong(list.get(i)+"")).get();
//            if(orderGs.getDeliveryNum()==null){
//                DeliveryNum dn = DeliveryNum.builder().build();
//                dn.setRandomInvoice(12);
//                dn.setDnState(DnState.REQUEST);
//
//                /** DN_COMPANY -> DN_COM_IDX */
////                dn.setDnCompany("우체국");
//
//                dn.setDnJson(null);
//                dn.setCreatedDate(LocalDate.now());
//
//                deliveryNumRepo.save(dn);
//
//                orderGs.setDeliveryNum(dn);
//                ordersGsRepo.save(orderGs);
//                cnt++;
//            }

        }

        return cnt+" 건 등록 완료";
    }



    /** AS리포트 등록 **/
    @Transactional
    public void asCheckReport(ParamMap paramMap) {
        Goods goods = goodsRepo.findById(Long.parseLong(paramMap.getString("gIdx"))).get();
        CheckGoods checkGoods = null;
        if (paramMap.getIntZero("cgsIdx") == 0) {
            int cgsCount = checksMapper.selectCheckGoodsCount(goods.getGIdx(), CgsType.AS.name());
            checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .cgsType(CgsType.AS)
                    .cgsJson(new HashMap<>())
                    .cgsCount(cgsCount + 1)
                    .build();
            checkGoodsRepo.save(checkGoods);
        } else {
            checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();
        }

        Map<String, Object> cgsJson = checkGoods.getCgsJson();

        List<Long> psCost = paramMap.getListLong("psCost");
        cgsJson.put("psCost", psCost);

        if (paramMap.get("pSpec1") != null) {
            List<String> pSpec1 = paramMap.getList("pSpec1");
            cgsJson.put("pSpec1", pSpec1);
        }

        if (paramMap.get("pSpec2") != null) {
            List<String> pSpec2 = paramMap.getList("pSpec2");
            cgsJson.put("pSpec2", pSpec2);
        }

        checkGoods.setCgsJson(cgsJson);
        checkGoodsRepo.save(checkGoods);

        paramMap.put("cgsIdx", checkGoods.getCgsIdx());

        // 공정 필요 생성
        saveProcessNeed(paramMap, PnType.PAINT);
        saveProcessNeed(paramMap, PnType.FIX);
        saveProcessNeed(paramMap, PnType.PROCESS);


    }

    @Transactional
    public void saveProcessNeed(ParamMap paramMap, PnType pnType) {
        String type = pnType.name().toLowerCase();
        int idx = paramMap.getIntZero(type+"Idx");
        String content = paramMap.getString(type+"Content");
        int cost = paramMap.getInt(type+"Cost");

        if (idx == 0 && !content.replaceAll(" ","").equals("") && cost != 0) {
            int pnCount = processMapper.selectProcessNeedCount(paramMap.getLong("gIdx"), pnType.name());
            ProcessNeed processNeed = ProcessNeed
                    .builder()
                    .goods(goodsRepo.getReferenceById(paramMap.getLong("gIdx")))
                    .pnType(pnType)
                    .pnContent(content)
                    .pnCount(pnCount + 1)
                    .pnExpectedCost(cost)
                    .pnRealCost(0)
                    .pnJson(new HashMap<>())
                    .pnProcess(PnProcess.BEFORE)
                    .pnState(PnState.NEED)
                    .build();
            processNeedRepo.save(processNeed);

            Map<String, Object> pnJson = new HashMap<>();

            List<Long> psCost = paramMap.getListLong("psCost");
            pnJson.put("psCost", psCost);

            if (paramMap.get("pSpec1") != null) {
                List<String> pSpec1 = paramMap.getList("pSpec1");
                pnJson.put("pSpec1", pSpec1);
            }

            if (paramMap.get("pSpec2") != null) {
                List<String> pSpec2 = paramMap.getList("pSpec2");
                pnJson.put("pSpec2", pSpec2);
            }

            processNeed.setPnJson(pnJson);
            processNeed.setCheckGoods(checkGoodsRepo.getReferenceById(paramMap.getInt("cgsIdx")));


            if (pnType.equals(PnType.PROCESS)) {
                saveProcessSpec(paramMap, processNeed);
            }
            processNeedRepo.save(processNeed);
        } else {
            ProcessNeed processNeed =  processNeedRepo.findById(idx).get();
            processNeed.setPnContent(content);
            processNeed.setPnExpectedCost(cost);

            Map<String, Object> pnJson = processNeed.getPnJson();

            List<Long> psCost = paramMap.getListLong("psCost");
            pnJson.put("psCost", psCost);

            if (paramMap.get("pSpec1") != null) {
                List<String> pSpec1 = paramMap.getList("pSpec1");
                pnJson.put("pSpec1", pSpec1);
            }

            if (paramMap.get("pSpec2") != null) {
                List<String> pSpec2 = paramMap.getList("pSpec2");
                pnJson.put("pSpec2", pSpec2);
            }

            if (pnType.equals(PnType.PROCESS)) {
                saveProcessSpec(paramMap, processNeed);
            }

            processNeedRepo.save(processNeed);
        }
    }

    /** 가공 내역 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap, ProcessNeed processNeed) {
        List<ProcessSpec> processSpecList = processSpecRepo.findByProcessNeed(processNeed);
        List<Long> psCost = paramMap.getListLong("psCost");
        if (processSpecList.size() == 0) {
            for (int i = 0; i < psCost.size(); i++) {
                ProcessSpec processSpec = ProcessSpec
                        .builder()
                        .processNeed(processNeed)
                        .psType(PsType.EXPECTED)
                        .psCost(psCost.get(i).intValue())
                        .build();
                processSpecRepo.save(processSpec);
            }
        } else {
            for (int i = 0; i < processSpecList.size(); i++) {
                processSpecList.get(i).setPsCost(psCost.get(i).intValue());
            }
        }
    }


    /** AS 불가 처리 **/
    @Transactional
    public void asImpossible(ParamMap paramMap) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
    }

    /** AS상세 사유 등록 */
    @Transactional
    public void saveAsReason(ParamMap paramMap) {
        AfterService afterService = afterServiceRepo.findById(paramMap.getLong("asIdx")).get();
        Map<String, Object> asJson = afterService.getAsJson();
        asJson.put("asReason", paramMap.getString("asReason"));
        afterService.setAsJson(asJson);
        afterServiceRepo.save(afterService);
    }

    /** 회수송장생성 */
    @Transactional
    public void saveDeliveryNumAs(ParamMap paramMap) {
        List<Long> ogIdx = paramMap.getListLong("ogIdx[]");
        List<Long> asIdx = paramMap.getListLong("asIdx[]");

        for (int i = 0; i < ogIdx.size(); i++) {
            AfterService afterService = afterServiceRepo.findById(asIdx.get(i)).get();
            OrderGs orderGs = ordersGsRepo.findById(ogIdx.get(i)).get();

            DeliveryNum deliveryNum = DeliveryNum
                    .builder()
                    .dnNum("TEST")
                    .dnState(DnState.REQUEST)
                    .company(companyRepo.getReferenceById(6))
                    .dnSender(DnSender.COMPANY)
                    .build();
            deliveryNumRepo.save(deliveryNum);

            afterService.setDeliveryNum(deliveryNum);
            afterServiceRepo.save(afterService);

            OrderGsDn orderGsDn = OrderGsDn
                    .builder()
                    .orderGs(orderGs)
                    .deliveryNum(deliveryNum)
                    .ogdnType(OgdnType.AS_IN)
                    .build();

            orderGsDnRepo.save(orderGsDn);
        }
    }
}
