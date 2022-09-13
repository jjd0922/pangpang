package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.CateType;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

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

            StreamResult result = new StreamResult(new FileOutputStream(new File("D://test2.xml")));
            transformer.transform(source, result);





        }catch (Exception e) {

        }


        return "";
    }
}
