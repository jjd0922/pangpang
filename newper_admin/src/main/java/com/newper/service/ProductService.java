package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GRank;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.entity.GoodsStock;
import com.newper.entity.Product;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.ProductMapper;
import com.newper.repository.CategoryRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.GoodsStockRepo;
import com.newper.repository.ProductRepo;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CompanyRepo companyRepo;
    private final GoodsStockRepo goodsStockRepo;

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Transactional
    public int productSave(ParamMap paramMap, MultipartFile P_THUMB_FILE1, MultipartFile P_THUMB_FILE2, MultipartFile P_THUMB_FILE3, MultipartFile P_THUMB_FILE4, MultipartFile P_THUMB_FILE5, MultipartFile P_THUMB_FILE6){
        Product product = paramMap.mapParam(Product.class);
        if(!paramMap.get("P_CATE_IDX").equals("")){
            Category category = categoryRepo.getReferenceById(paramMap.getInt("P_CATE_IDX"));
            product.setCategory(category);
        }
        if(!paramMap.get("P_CATE_IDX2").equals("")){
            Category brand = categoryRepo.getReferenceById(paramMap.getInt("P_CATE_IDX2"));
            product.setBrand(brand);
        }
        if(!paramMap.get("P_COM_IDX").equals("")){
            Company store = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX"));
            product.setStoreName(store);
        }
/*        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company manufacture = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX2"));
            product.setManufactureName(manufacture);
        }*/
        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company afterService = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX2"));
            product.setAfterServiceName(afterService);
        }

        String thumbFilePath1="";
        String thumbFilePath2="";
        String thumbFilePath3="";
        String thumbFilePath4="";
        String thumbFilePath5="";
        String thumbFilePath6="";

        String P_THUMB_FILE_NAME1="";
        String P_THUMB_FILE_NAME2="";
        String P_THUMB_FILE_NAME3="";
        String P_THUMB_FILE_NAME4="";
        String P_THUMB_FILE_NAME5="";
        String P_THUMB_FILE_NAME6="";

        if(P_THUMB_FILE1.getSize()!=0){
            thumbFilePath1 = Common.uploadFilePath(P_THUMB_FILE1, "product/thumbnail1/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME1= P_THUMB_FILE1.getOriginalFilename();
        }
        if(P_THUMB_FILE2.getSize()!=0){
            thumbFilePath2 = Common.uploadFilePath(P_THUMB_FILE2, "product/thumbnail2/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME2= P_THUMB_FILE2.getOriginalFilename();
        }
        if(P_THUMB_FILE3.getSize()!=0){
            thumbFilePath3 = Common.uploadFilePath(P_THUMB_FILE3, "product/thumbnail3/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME3= P_THUMB_FILE3.getOriginalFilename();
        }
        if(P_THUMB_FILE4.getSize()!=0){
            thumbFilePath4 = Common.uploadFilePath(P_THUMB_FILE4, "product/thumbnail4/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME4= P_THUMB_FILE4.getOriginalFilename();
        }
        if(P_THUMB_FILE5.getSize()!=0){
            thumbFilePath5 = Common.uploadFilePath(P_THUMB_FILE5, "product/thumbnail5/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME5= P_THUMB_FILE5.getOriginalFilename();
        }
        if(P_THUMB_FILE6.getSize()!=0){
            thumbFilePath6 = Common.uploadFilePath(P_THUMB_FILE6, "product/thumbnail6/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME6= P_THUMB_FILE6.getOriginalFilename();
        }

        product.setPThumbFile1(thumbFilePath1);
        product.setPThumbFile2(thumbFilePath2);
        product.setPThumbFile3(thumbFilePath3);
        product.setPThumbFile4(thumbFilePath4);
        product.setPThumbFile5(thumbFilePath5);
        product.setPThumbFile6(thumbFilePath6);

        product.setPThumbFileName1(P_THUMB_FILE_NAME1);
        product.setPThumbFileName2(P_THUMB_FILE_NAME2);
        product.setPThumbFileName3(P_THUMB_FILE_NAME3);
        product.setPThumbFileName4(P_THUMB_FILE_NAME4);
        product.setPThumbFileName5(P_THUMB_FILE_NAME5);
        product.setPThumbFileName6(P_THUMB_FILE_NAME6);

        productRepo.saveAndFlush(product);


        return product.getPIdx();
    }

    /**상품 수정*/
    @Transactional
    public int productUpdate(ParamMap paramMap, MultipartFile P_THUMB_FILE1, MultipartFile P_THUMB_FILE2, MultipartFile P_THUMB_FILE3, MultipartFile P_THUMB_FILE4, MultipartFile P_THUMB_FILE5, MultipartFile P_THUMB_FILE6){
        Product product = paramMap.mapParam(Product.class);
        Product ori = productRepo.findById(paramMap.getInt("P_IDX")).get();

        ori.setPComManufacturer(product.getPComManufacturer());
        ori.setPName(product.getPName());
        ori.setPState(product.getPState());
        ori.setPModel(product.getPModel());
        ori.setPUseStock(product.isPUseStock());
        ori.setPType1(product.getPType1());
        ori.setPType2(product.getPType2());
        ori.setPType3(product.getPType3());
        ori.setPDelType(product.getPDelType());
        ori.setPContent1(product.getPContent1());
        ori.setPContent2(product.getPContent2());
        ori.setPContent3(product.getPContent3());
        ori.setPMemo(product.getPMemo());
        ori.setPTag(product.getPTag());
        ori.setPBlogUrl(product.getPBlogUrl());
        ori.setPPriceUrl(product.getPPriceUrl());
        ori.setPYoutubeUrl(product.getPYoutubeUrl());
        ori.setPCost(product.getPCost());
        ori.setPRetailPrice(product.getPRetailPrice());
        ori.setPSellPrice(product.getPSellPrice());
        ori.setPDelPrice(product.getPDelPrice());
        ori.setPDelCompany(product.getPDelCompany());
        ori.setPDelTogether(product.getPDelTogether());
        ori.setPFreeInterest(product.isPFreeInterest());
        ori.setPNaver(product.getPNaver());
        ori.setPInfo(product.getPInfo());
        ori.setPOption(product.getPOption());


        if(!paramMap.get("P_CATE_IDX").equals("")){
            Category category = categoryRepo.getReferenceById(paramMap.getInt("P_CATE_IDX"));
            ori.setCategory(category);
        }
        if(!paramMap.get("P_CATE_IDX2").equals("")){
            Category brand = categoryRepo.getReferenceById(paramMap.getInt("P_CATE_IDX2"));
            ori.setBrand(brand);
        }
        if(!paramMap.get("P_COM_IDX").equals("")){
            Company store = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX"));
            ori.setStoreName(store);
        }
/*        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company manufacture = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX2"));
            ori.setManufactureName(manufacture);
        }*/
        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company afterService = companyRepo.getReferenceById(paramMap.getInt("P_COM_IDX2"));
            ori.setAfterServiceName(afterService);
        }

        String thumbFilePath1=ori.getPThumbFile1();
        String thumbFilePath2=ori.getPThumbFile2();
        String thumbFilePath3=ori.getPThumbFile3();
        String thumbFilePath4=ori.getPThumbFile4();
        String thumbFilePath5=ori.getPThumbFile5();
        String thumbFilePath6=ori.getPThumbFile6();

        String P_THUMB_FILE_NAME1=ori.getPThumbFileName1();
        String P_THUMB_FILE_NAME2=ori.getPThumbFileName2();
        String P_THUMB_FILE_NAME3=ori.getPThumbFileName3();
        String P_THUMB_FILE_NAME4=ori.getPThumbFileName4();
        String P_THUMB_FILE_NAME5=ori.getPThumbFileName5();
        String P_THUMB_FILE_NAME6=ori.getPThumbFileName6();

        if(P_THUMB_FILE1.getSize()!=0){
            thumbFilePath1 = Common.uploadFilePath(P_THUMB_FILE1, "product/thumbnail1/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME1= P_THUMB_FILE1.getOriginalFilename();
        }
        if(P_THUMB_FILE2.getSize()!=0){
            thumbFilePath2 = Common.uploadFilePath(P_THUMB_FILE2, "product/thumbnail2/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME2= P_THUMB_FILE2.getOriginalFilename();
        }
        if(P_THUMB_FILE3.getSize()!=0){
            thumbFilePath3 = Common.uploadFilePath(P_THUMB_FILE3, "product/thumbnail3/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME3= P_THUMB_FILE3.getOriginalFilename();
        }
        if(P_THUMB_FILE4.getSize()!=0){
            thumbFilePath4 = Common.uploadFilePath(P_THUMB_FILE4, "product/thumbnail4/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME4= P_THUMB_FILE4.getOriginalFilename();
        }
        if(P_THUMB_FILE5.getSize()!=0){
            thumbFilePath5 = Common.uploadFilePath(P_THUMB_FILE5, "product/thumbnail5/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME5= P_THUMB_FILE5.getOriginalFilename();
        }
        if(P_THUMB_FILE6.getSize()!=0){
            thumbFilePath6 = Common.uploadFilePath(P_THUMB_FILE6, "product/thumbnail6/", AdminBucket.OPEN);
            P_THUMB_FILE_NAME6= P_THUMB_FILE6.getOriginalFilename();
        }

        product.setPThumbFile1(thumbFilePath1);
        product.setPThumbFile2(thumbFilePath2);
        product.setPThumbFile3(thumbFilePath3);
        product.setPThumbFile4(thumbFilePath4);
        product.setPThumbFile5(thumbFilePath5);
        product.setPThumbFile6(thumbFilePath6);

        product.setPThumbFileName1(P_THUMB_FILE_NAME1);
        product.setPThumbFileName2(P_THUMB_FILE_NAME2);
        product.setPThumbFileName3(P_THUMB_FILE_NAME3);
        product.setPThumbFileName4(P_THUMB_FILE_NAME4);
        product.setPThumbFileName5(P_THUMB_FILE_NAME5);
        product.setPThumbFileName6(P_THUMB_FILE_NAME6);

        productRepo.save(ori);


        return ori.getPIdx();
    }

    /**재고상품 저장*/
    @Transactional
    public int goodsStockSave(ParamMap paramMap, MultipartFile GS_THUMB_FILE1, MultipartFile GS_THUMB_FILE2, MultipartFile GS_THUMB_FILE3){
        GoodsStock goodsStock = paramMap.mapParam(GoodsStock.class);
        String gs_code = productMapper.selectGoodsStockByListGsCode();
        String code = "goodsStock-";
        if (gs_code == null) {
            code = code + "001";
        } else {
            int no = Integer.parseInt(gs_code.replace(code, "").toString());
            String codeNo = String.format("%03d", no + 1);
            code = code + codeNo;
        }
        goodsStock.setGsCode(code);

        if(!paramMap.get("P_IDX").equals("")){
            Product product = productRepo.getReferenceById(paramMap.getInt("P_IDX"));
            goodsStock.setProduct(product);
        }

        // 등급 추가 후 설정
        goodsStock.setGsRank(GRank.A1);
        // 스펙 추가후 설정
        goodsStock.setSpec(null);

        //할인전 기준 가격
        goodsStock.setGsOriginalPrice(0);

        //가용재고
        goodsStock.setGsStock(0L);
        //출고재고
        goodsStock.setGsOutStock(0L);


        String thumbFilePath1="";
        String thumbFilePath2="";
        String thumbFilePath3="";


        String GS_THUMB_FILE_NAME1="";
        String GS_THUMB_FILE_NAME2="";
        String GS_THUMB_FILE_NAME3="";


        if(GS_THUMB_FILE1.getSize()!=0){
            thumbFilePath1 = Common.uploadFilePath(GS_THUMB_FILE1, "product/goodsStock/thumbnail1/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME1= GS_THUMB_FILE1.getOriginalFilename();
        }
        if(GS_THUMB_FILE2.getSize()!=0){
            thumbFilePath2 = Common.uploadFilePath(GS_THUMB_FILE2, "product/goodsStock/thumbnail2/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME2= GS_THUMB_FILE2.getOriginalFilename();
        }
        if(GS_THUMB_FILE3.getSize()!=0){
            thumbFilePath3 = Common.uploadFilePath(GS_THUMB_FILE3, "product/goodsStock/thumbnail3/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME3= GS_THUMB_FILE3.getOriginalFilename();
        }

        goodsStock.setGsThumbFile1(thumbFilePath1);
        goodsStock.setGsThumbFile2(thumbFilePath2);
        goodsStock.setGsThumbFile3(thumbFilePath3);

        goodsStock.setGsThumbFileName1(GS_THUMB_FILE_NAME1);
        goodsStock.setGsThumbFileName2(GS_THUMB_FILE_NAME2);
        goodsStock.setGsThumbFileName3(GS_THUMB_FILE_NAME3);


        goodsStockRepo.saveAndFlush(goodsStock);


        return goodsStock.getGsIdx();
    }

    @Transactional
    public int goodsStockUpdate(ParamMap paramMap, MultipartFile GS_THUMB_FILE1, MultipartFile GS_THUMB_FILE2, MultipartFile GS_THUMB_FILE3){
        GoodsStock goodsStock = paramMap.mapParam(GoodsStock.class);
        GoodsStock ori = goodsStockRepo.findById(paramMap.getInt("GS_IDX")).get();

        String thumbFilePath1=ori.getGsThumbFile1();
        String thumbFilePath2=ori.getGsThumbFile2();
        String thumbFilePath3=ori.getGsThumbFile3();


        String GS_THUMB_FILE_NAME1=ori.getGsThumbFileName1();
        String GS_THUMB_FILE_NAME2=ori.getGsThumbFileName2();
        String GS_THUMB_FILE_NAME3=ori.getGsThumbFileName3();


        if(GS_THUMB_FILE1.getSize()!=0){
            thumbFilePath1 = Common.uploadFilePath(GS_THUMB_FILE1, "product/goodsStock/thumbnail1/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME1= GS_THUMB_FILE1.getOriginalFilename();
        }
        if(GS_THUMB_FILE2.getSize()!=0){
            thumbFilePath2 = Common.uploadFilePath(GS_THUMB_FILE2, "product/goodsStock/thumbnail2/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME2= GS_THUMB_FILE2.getOriginalFilename();
        }
        if(GS_THUMB_FILE3.getSize()!=0){
            thumbFilePath3 = Common.uploadFilePath(GS_THUMB_FILE3, "product/goodsStock/thumbnail3/", AdminBucket.OPEN);
            GS_THUMB_FILE_NAME3= GS_THUMB_FILE3.getOriginalFilename();
        }

        ori.setGsThumbFile1(thumbFilePath1);
        ori.setGsThumbFile2(thumbFilePath2);
        ori.setGsThumbFile3(thumbFilePath3);

        ori.setGsThumbFileName1(GS_THUMB_FILE_NAME1);
        ori.setGsThumbFileName2(GS_THUMB_FILE_NAME2);
        ori.setGsThumbFileName3(GS_THUMB_FILE_NAME3);

        ori.setGsContent(goodsStock.getGsContent());
        ori.setGsName(goodsStock.getGsName());
        ori.setGsPrice(goodsStock.getGsPrice());




        goodsStockRepo.save(ori);


        return ori.getGsIdx();
    }

    @Transactional
    /**사방넷 상품전송*/
    public String sabang(ParamMap paramMap){
        String	code = "";
        String gsIdxs = paramMap.get("gsIdxs")+"";
        System.out.println(gsIdxs);
        int cnt = 0;
        String[] gsIdx = gsIdxs.split(",");

        String ORIGIN_STR = paramMap.get("ORIGIN")+"";
        String STATUS_STR = paramMap.get("STATUS")+"";
        String DELV_COST_STR = paramMap.onlyNumber("DELV_COST")+"";
        String GOODS_PRICE_STR = paramMap.onlyNumber("GOODS_PRICE")+"";
        String DELV_TYPE_STR = paramMap.get("DELV_TYPE")+"";
        String GOODS_COST_STR = paramMap.onlyNumber("GOODS_COST")+"";
        String GOODS_CONSUMER_PRICE_STR = paramMap.onlyNumber("GOODS_CONSUMER_PRICE")+"";

        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        try {
        System.out.println("length : "+gsIdx.length);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(true);

        Element exInformation = doc.createElement("SABANG_GOODS_REGI");
        doc.appendChild(exInformation);
        Element header = doc.createElement("HEADER");


        Element SEND_COMPAYNY_ID = doc.createElement("SEND_COMPAYNY_ID");
        Element SEND_AUTH_KEY = doc.createElement("SEND_AUTH_KEY");
        Element SEND_DATE = doc.createElement("SEND_DATE");
        Element SEND_GOODS_CD_RT = doc.createElement("SEND_GOODS_CD_RT");
        Element RESULT_TYPE = doc.createElement("RESULT_TYPE");
        SEND_COMPAYNY_ID.appendChild(doc.createTextNode("juncmall12"));
        SEND_AUTH_KEY.appendChild(doc.createTextNode("JJx6FM6VHPJP6TGFZuFCNdFT2MEMbx6A0"));
        SEND_DATE.appendChild(doc.createTextNode(formatDate));
        SEND_GOODS_CD_RT.appendChild(doc.createTextNode("Y"));
        RESULT_TYPE.appendChild(doc.createTextNode("XML"));

        header.appendChild(SEND_COMPAYNY_ID);
        header.appendChild(SEND_AUTH_KEY);
        header.appendChild(SEND_DATE);
        header.appendChild(SEND_GOODS_CD_RT);
        header.appendChild(RESULT_TYPE);

        exInformation.appendChild(header);



            for(int i=0; i<gsIdx.length; i++){
                Element data = doc.createElement("DATA");
                System.out.println("idx : " + gsIdx[i]);
                int gs_idx = Integer.parseInt(gsIdx[i]);
                GoodsStock goodsStock = goodsStockRepo.getReferenceById(gs_idx);
                Product product = goodsStock.getProduct();
                Category brand = product.getBrand();
                Category cate = product.getCategory();





                    Element GOODS_NM = doc.createElement("GOODS_NM");
                    Element GOODS_KEYWORD = doc.createElement("GOODS_KEYWORD");
                    Element MODEL_NM = doc.createElement("MODEL_NM");
                    Element MODEL_NO = doc.createElement("MODEL_NO");
                    Element BRAND_NM = doc.createElement("BRAND_NM");
                    Element COMPAYNY_GOODS_CD = doc.createElement("COMPAYNY_GOODS_CD");
                    Element GOODS_SEARCH = doc.createElement("GOODS_SEARCH");
                    Element GOODS_GUBUN = doc.createElement("GOODS_GUBUN");
                    Element CLASS_CD1 = doc.createElement("CLASS_CD1");
                    Element CLASS_CD2 = doc.createElement("CLASS_CD2");
                    Element CLASS_CD3 = doc.createElement("CLASS_CD3");
                    Element CLASS_CD4 = doc.createElement("CLASS_CD4");
                    Element MAKER = doc.createElement("MAKER");
                    Element ORIGIN = doc.createElement("ORIGIN");
                    Element MAKE_YEAR = doc.createElement("MAKE_YEAR");
                    Element MAKE_DM = doc.createElement("MAKE_DM");
                    Element GOODS_SEASON = doc.createElement("GOODS_SEASON");
                    Element SEX = doc.createElement("SEX");
                    Element STATUS = doc.createElement("STATUS");
                    Element DELIV_ABLE_REGION = doc.createElement("DELIV_ABLE_REGION");
                    Element TAX_YN = doc.createElement("TAX_YN");
                    Element DELV_TYPE = doc.createElement("DELV_TYPE");
                    Element DELV_COST = doc.createElement("DELV_COST");
                    Element BANPUM_AREA = doc.createElement("BANPUM_AREA");
                    Element GOODS_COST = doc.createElement("GOODS_COST");
                    Element GOODS_PRICE = doc.createElement("GOODS_PRICE");
                    Element GOODS_CONSUMER_PRICE = doc.createElement("GOODS_CONSUMER_PRICE");
                    Element CHAR_1_NM = doc.createElement("CHAR_1_NM");
                    Element CHAR_1_VAL = doc.createElement("CHAR_1_VAL");
                    Element CHAR_2_NM = doc.createElement("CHAR_2_NM");
                    Element CHAR_2_VAL = doc.createElement("CHAR_2_VAL");
                    Element IMG_PATH = doc.createElement("IMG_PATH");
                    Element IMG_PATH1 = doc.createElement("IMG_PATH1");
                    Element IMG_PATH2 = doc.createElement("IMG_PATH2");
                    Element IMG_PATH3 = doc.createElement("IMG_PATH3");
                    Element IMG_PATH4 = doc.createElement("IMG_PATH4");
                    Element IMG_PATH5 = doc.createElement("IMG_PATH5");
                    Element IMG_PATH6 = doc.createElement("IMG_PATH6");
                    Element IMG_PATH7 = doc.createElement("IMG_PATH7");
                    Element IMG_PATH8 = doc.createElement("IMG_PATH8");
                    Element IMG_PATH9 = doc.createElement("IMG_PATH9");
                    Element IMG_PATH10 = doc.createElement("IMG_PATH10");
                    Element IMG_PATH11 = doc.createElement("IMG_PATH11");
                    Element IMG_PATH12 = doc.createElement("IMG_PATH12");
                    Element IMG_PATH13 = doc.createElement("IMG_PATH13");
                    Element IMG_PATH14 = doc.createElement("IMG_PATH14");
                    Element IMG_PATH15 = doc.createElement("IMG_PATH15");
                    Element IMG_PATH16 = doc.createElement("IMG_PATH16");
                    Element IMG_PATH17 = doc.createElement("IMG_PATH17");
                    Element IMG_PATH18 = doc.createElement("IMG_PATH18");
                    Element IMG_PATH19 = doc.createElement("IMG_PATH19");
                    Element IMG_PATH20 = doc.createElement("IMG_PATH20");
                    Element IMG_PATH21 = doc.createElement("IMG_PATH21");
                    Element IMG_PATH22 = doc.createElement("IMG_PATH22");
                    Element IMG_PATH23 = doc.createElement("IMG_PATH23");
                    Element IMG_PATH24 = doc.createElement("IMG_PATH24");
                    Element GOODS_REMARKS = doc.createElement("GOODS_REMARKS");
                    Element CERTNO = doc.createElement("CERTNO");
                    Element AVLST_DM = doc.createElement("AVLST_DM");
                    Element AVLED_DM = doc.createElement("AVLED_DM");
                    Element ISSUEDATE = doc.createElement("ISSUEDATE");
                    Element CERTDATE = doc.createElement("CERTDATE");
                    Element CERT_AGENCY = doc.createElement("CERT_AGENCY");
                    Element MATERIAL = doc.createElement("MATERIAL");
                    Element STOCK_USE_YN = doc.createElement("STOCK_USE_YN");
                    Element OPT_TYPE = doc.createElement("OPT_TYPE");
                    Element PROP_EDIT_YN = doc.createElement("PROP_EDIT_YN");
                    Element PROP1_CD = doc.createElement("PROP1_CD");
                    Element PROP_VAL1 = doc.createElement("PROP_VAL1");
                    Element PROP_VAL2 = doc.createElement("PROP_VAL2");
                    Element PROP_VAL3 = doc.createElement("PROP_VAL3");
                    Element PROP_VAL4 = doc.createElement("PROP_VAL4");
                    Element PROP_VAL5 = doc.createElement("PROP_VAL5");
                    Element PROP_VAL6 = doc.createElement("PROP_VAL6");
                    Element PROP_VAL7 = doc.createElement("PROP_VAL7");
                    Element PROP_VAL8 = doc.createElement("PROP_VAL8");
                    Element PROP_VAL9 = doc.createElement("PROP_VAL9");
                    Element PROP_VAL10 = doc.createElement("PROP_VAL10");
                    Element PROP_VAL11 = doc.createElement("PROP_VAL11");
                    Element PROP_VAL12 = doc.createElement("PROP_VAL12");
                    Element PROP_VAL13 = doc.createElement("PROP_VAL13");
                    Element PROP_VAL14 = doc.createElement("PROP_VAL14");
                    Element PROP_VAL15 = doc.createElement("PROP_VAL15");
                    Element PROP_VAL16 = doc.createElement("PROP_VAL16");
                    Element PROP_VAL17 = doc.createElement("PROP_VAL17");
                    Element PROP_VAL18 = doc.createElement("PROP_VAL18");
                    Element PROP_VAL19 = doc.createElement("PROP_VAL19");
                    Element PROP_VAL20 = doc.createElement("PROP_VAL20");
                    Element PROP_VAL21 = doc.createElement("PROP_VAL21");
                    Element PROP_VAL22 = doc.createElement("PROP_VAL22");
                    Element PROP_VAL23 = doc.createElement("PROP_VAL23");
                    Element PROP_VAL24 = doc.createElement("PROP_VAL24");
                    Element PROP_VAL25 = doc.createElement("PROP_VAL25");
                    Element PROP_VAL26 = doc.createElement("PROP_VAL26");
                    Element PROP_VAL27 = doc.createElement("PROP_VAL27");
                    Element PROP_VAL28 = doc.createElement("PROP_VAL28");
                    Element PROP_VAL29 = doc.createElement("PROP_VAL29");
                    Element PROP_VAL30 = doc.createElement("PROP_VAL30");
                    Element PROP_VAL31 = doc.createElement("PROP_VAL31");
                    Element PROP_VAL32 = doc.createElement("PROP_VAL32");
                    Element PROP_VAL33 = doc.createElement("PROP_VAL33");
                    Element PACK_CODE_STR = doc.createElement("PACK_CODE_STR");
                    Element GOODS_NM_EN = doc.createElement("GOODS_NM_EN");
                    Element GOODS_NM_PR = doc.createElement("GOODS_NM_PR");
                    Element GOODS_REMARKS2 = doc.createElement("GOODS_REMARKS2");
                    Element GOODS_REMARKS3 = doc.createElement("GOODS_REMARKS3");
                    Element GOODS_REMARKS4 = doc.createElement("GOODS_REMARKS4");
                    Element IMPORTNO = doc.createElement("IMPORTNO");
                    Element GOODS_COST2 = doc.createElement("GOODS_COST2");
                    Element ORIGIN2 = doc.createElement("ORIGIN2");
                    Element EXPIRE_DM = doc.createElement("EXPIRE_DM");
                    Element SUPPLY_SAVE_YN =doc.createElement("SUPPLY_SAVE_YN");
                    Element DESCRITION = doc.createElement("DESCRITION");


                    GOODS_NM.appendChild(doc.createTextNode(goodsStock.getProduct().getPName()));
                    GOODS_KEYWORD.appendChild(doc.createTextNode(goodsStock.getProduct().getPName()));
                    MODEL_NM.appendChild(doc.createTextNode(goodsStock.getProduct().getPModel()));
                    if(brand!=null){
                        BRAND_NM.appendChild(doc.createTextNode(brand.getCateName()));
                    }
                    COMPAYNY_GOODS_CD.appendChild(doc.createTextNode(goodsStock.getGsCode()));
                    GOODS_GUBUN.appendChild(doc.createTextNode("3"));
                    if(cate!=null){
                        Map<String, Object> category = categoryMapper.selectCategoryDetail(cate.getCateIdx());
                        if(category!=null){
                            if(Integer.parseInt(category.get("CATE_DEPTH")+"")==1){
                                CLASS_CD1.appendChild(doc.createTextNode(category.get("ori_cate_name")+""));
                            }else if (Integer.parseInt(category.get("CATE_DEPTH")+"")==2){
                                CLASS_CD1.appendChild(doc.createTextNode(category.get("per_cate_name1")+""));
                                CLASS_CD2.appendChild(doc.createTextNode(category.get("ori_cate_name")+""));
                            }else if(Integer.parseInt(category.get("CATE_DEPTH")+"")==3){
                                CLASS_CD1.appendChild(doc.createTextNode(category.get("per_cate_name2")+""));
                                CLASS_CD2.appendChild(doc.createTextNode(category.get("per_cate_name1")+""));
                                CLASS_CD3.appendChild(doc.createTextNode(category.get("ori_cate_name")+""));
                            }
                        }
                    }
                    MAKER.appendChild(doc.createTextNode(""));
                    ORIGIN.appendChild(doc.createTextNode(ORIGIN_STR));
                    GOODS_SEASON.appendChild(doc.createTextNode("7"));
                    SEX.appendChild(doc.createTextNode("4"));
                    STATUS.appendChild(doc.createTextNode(STATUS_STR));
                    TAX_YN.appendChild(doc.createTextNode("1"));
                    GOODS_COST.appendChild(doc.createTextNode(GOODS_COST_STR));
                    GOODS_PRICE.appendChild(doc.createTextNode(GOODS_PRICE_STR));
                    GOODS_CONSUMER_PRICE.appendChild(doc.createTextNode(GOODS_CONSUMER_PRICE_STR));
                    IMG_PATH.appendChild(doc.createTextNode(product.getPThumbFile1()+""));
                    IMG_PATH1.appendChild(doc.createTextNode(product.getPThumbFile1()+""));
                    IMG_PATH2.appendChild(doc.createTextNode(product.getPThumbFile2()+""));
                    IMG_PATH3.appendChild(doc.createTextNode(product.getPThumbFile3()+""));
                    IMG_PATH4.appendChild(doc.createTextNode(product.getPThumbFile4()+""));
                    IMG_PATH5.appendChild(doc.createTextNode(product.getPThumbFile5()+""));
                    IMG_PATH6.appendChild(doc.createTextNode(product.getPThumbFile6()+""));
                    IMG_PATH7.appendChild(doc.createTextNode(goodsStock.getGsThumbFile1()+""));
                    IMG_PATH8.appendChild(doc.createTextNode(goodsStock.getGsThumbFile2()+""));
                    IMG_PATH9.appendChild(doc.createTextNode(goodsStock.getGsThumbFile3()+""));
                    GOODS_REMARKS.appendChild(doc.createTextNode(Common.summernoteContent(goodsStock.getGsContent()).replace("<p><img src=\"","").replace("\">","").replace("</p>","").trim()+"]]>"));

                DELV_TYPE.appendChild(doc.createTextNode(DELV_TYPE_STR));
                DELV_COST.appendChild(doc.createTextNode(DELV_COST_STR));

                    STOCK_USE_YN.appendChild(doc.createTextNode("Y"));
                    CHAR_1_NM.appendChild(doc.createTextNode("단품"));
                    CHAR_1_VAL.appendChild(doc.createTextNode("단품^^999 "));

                    data.appendChild(GOODS_NM);
                    data.appendChild(GOODS_KEYWORD);
                    data.appendChild(MODEL_NM);
                    data.appendChild(MODEL_NO);
                    data.appendChild(BRAND_NM);
                    data.appendChild(COMPAYNY_GOODS_CD);
                    data.appendChild(GOODS_SEARCH);
                    data.appendChild(GOODS_GUBUN);
                    data.appendChild(CLASS_CD1);
                    data.appendChild(CLASS_CD2);
                    data.appendChild(CLASS_CD3);
                    data.appendChild(CLASS_CD4);
                    data.appendChild(MAKER);
                    data.appendChild(ORIGIN);
                    data.appendChild(MAKE_YEAR);
                    data.appendChild(MAKE_DM);
                    data.appendChild(GOODS_SEASON);
                    data.appendChild(SEX);
                    data.appendChild(STATUS);
                    data.appendChild(DELIV_ABLE_REGION);
                    data.appendChild(TAX_YN);
                    data.appendChild(DELV_TYPE);
                    data.appendChild(DELV_COST);
                    data.appendChild(BANPUM_AREA);
                    data.appendChild(GOODS_COST);
                    data.appendChild(GOODS_PRICE);
                    data.appendChild(GOODS_CONSUMER_PRICE);
                    data.appendChild(CHAR_1_NM);
                    data.appendChild(CHAR_1_VAL);
                    data.appendChild(CHAR_2_NM);
                    data.appendChild(CHAR_2_VAL);
                    data.appendChild(IMG_PATH);
                    data.appendChild(IMG_PATH1);
                    data.appendChild(IMG_PATH2);
                    data.appendChild(IMG_PATH3);
                    data.appendChild(IMG_PATH4);
                    data.appendChild(IMG_PATH5);
                    data.appendChild(IMG_PATH6);
                    data.appendChild(IMG_PATH7);
                    data.appendChild(IMG_PATH8);
                    data.appendChild(IMG_PATH9);
                    data.appendChild(IMG_PATH10);
                    data.appendChild(IMG_PATH11);
                    data.appendChild(IMG_PATH12);
                    data.appendChild(IMG_PATH13);
                    data.appendChild(IMG_PATH14);
                    data.appendChild(IMG_PATH15);
                    data.appendChild(IMG_PATH16);
                    data.appendChild(IMG_PATH17);
                    data.appendChild(IMG_PATH18);
                    data.appendChild(IMG_PATH19);
                    data.appendChild(IMG_PATH20);
                    data.appendChild(IMG_PATH21);
                    data.appendChild(IMG_PATH22);
                    data.appendChild(IMG_PATH23);
                    data.appendChild(IMG_PATH24);
                    data.appendChild(GOODS_REMARKS);
                    data.appendChild(CERTNO);
                    data.appendChild(AVLST_DM);
                    data.appendChild(AVLED_DM);
                    data.appendChild(ISSUEDATE);
                    data.appendChild(CERTDATE);
                    data.appendChild(CERT_AGENCY);
                    data.appendChild(MATERIAL);
                    data.appendChild(STOCK_USE_YN);
                    data.appendChild(OPT_TYPE);
                    data.appendChild(PROP_EDIT_YN);
                    data.appendChild(PROP1_CD);
                    data.appendChild(PROP_VAL1);
                    data.appendChild(PROP_VAL2);
                    data.appendChild(PROP_VAL3);
                    data.appendChild(PROP_VAL4);
                    data.appendChild(PROP_VAL5);
                    data.appendChild(PROP_VAL6);
                    data.appendChild(PROP_VAL7);
                    data.appendChild(PROP_VAL8);
                    data.appendChild(PROP_VAL9);
                    data.appendChild(PROP_VAL10);
                    data.appendChild(PROP_VAL11);
                    data.appendChild(PROP_VAL12);
                    data.appendChild(PROP_VAL13);
                    data.appendChild(PROP_VAL14);
                    data.appendChild(PROP_VAL15);
                    data.appendChild(PROP_VAL16);
                    data.appendChild(PROP_VAL17);
                    data.appendChild(PROP_VAL18);
                    data.appendChild(PROP_VAL19);
                    data.appendChild(PROP_VAL20);
                    data.appendChild(PROP_VAL21);
                    data.appendChild(PROP_VAL22);
                    data.appendChild(PROP_VAL23);
                    data.appendChild(PROP_VAL24);
                    data.appendChild(PROP_VAL25);
                    data.appendChild(PROP_VAL26);
                    data.appendChild(PROP_VAL27);
                    data.appendChild(PROP_VAL28);
                    data.appendChild(PROP_VAL29);
                    data.appendChild(PROP_VAL30);
                    data.appendChild(PROP_VAL31);
                    data.appendChild(PROP_VAL32);
                    data.appendChild(PROP_VAL33);
                    data.appendChild(PACK_CODE_STR);
                    data.appendChild(GOODS_NM_EN);
                    data.appendChild(GOODS_NM_PR);
                    data.appendChild(GOODS_REMARKS2);
                    data.appendChild(GOODS_REMARKS3);
                    data.appendChild(GOODS_REMARKS4);
                    data.appendChild(IMPORTNO);
                    data.appendChild(GOODS_COST2);
                    data.appendChild(ORIGIN2);
                    data.appendChild(EXPIRE_DM);
                    data.appendChild(SUPPLY_SAVE_YN);
                    data.appendChild(DESCRITION);




                    exInformation.appendChild(data);



            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
            transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new FileOutputStream(new File("D://product.xml")));

            transformer.transform(source, result);

            File file = new File("D://product.xml");

            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileXml = "xml/"+file.getName()+"_"+now;
            String loc=NewperStorage.uploadFile(AdminBucket.OPEN, fileXml, new FileInputStream(file), file.length(), Files.probeContentType(file.toPath()));

            Response response = null;

            try {
                OkHttpClient client1 = new OkHttpClient.Builder().build();
                FormBody.Builder fb = new FormBody.Builder();

                Request req = new Request.Builder().url("http://r.sabangnet.co.kr/RTL_API/xml_goods_info.html?xml_url="+loc).post(fb.build()).build();
                response = client1.newCall(req).execute();
                String str = response.body().string().toString();
                JSONObject json = XML.toJSONObject(str);
                JSONObject sabang_result = json.getJSONObject("SABANG_RESULT");
                JSONArray data = sabang_result.getJSONArray("DATA");

                for(int i=0; i<data.length(); i++) {
                    JSONObject json_product = data.getJSONObject(i);
                    if(json_product.get("RESULT").equals("SUCCESS")){
                        cnt++;
                        String GS_CODE = json_product.getString("COMPAYNY_GOODS_CD");
                        String GS_SABANG = json_product.getString("PRODUCT_ID");
                        GoodsStock goodsStock = goodsStockRepo.findGoodsStockByGsCode(GS_CODE);
                        goodsStock.setGsSabang(GS_SABANG);
                        goodsStockRepo.save(goodsStock);
                    }else{
                        code+=json_product.getString("COMPAYNY_GOODS_CD")+",";
                    }
                }
                System.out.println(data);

            } catch (Exception e) {

            }finally {
                try {
                    if (response != null)
                        response.close();
                } catch (Exception ee) {
                }
            }

        }catch (Exception e) {
        }
        String res = "";
        res = cnt + " 건 등록성공\n";
        if(code.trim().length()>0){
            String codes[] = code.split(",");

            if(codes.length>0){
                res=res+"등록실패 재고코드 :\n";
                for(int y=0; y<codes.length; y++){
                    res+=codes[y]+"\n";
                }
            }
        }

        return res;
    }
    @Transactional
    public String naver(ParamMap paramMap){
        String clientId = "ckW6gv_5uIKK7mfPoWnI"; //애플리케이션 클라이언트 아이디
        String clientSecret = "w3FFz6H5L1"; //애플리케이션 클라이언트 시크릿

        String name = paramMap.getString("P_NAME");
        String model = paramMap.getString("P_MODEL");

        String text = model;
        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/shop.json?query=" + text;    // JSON 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        HttpURLConnection con = null;

        try{
            URL url = new URL(apiURL);
            con = (HttpURLConnection)url.openConnection();
        }catch (MalformedURLException e){
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        }catch (IOException e){
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }

        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
                try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                    StringBuilder responseBody = new StringBuilder();


                    String line;
                    while ((line = lineReader.readLine()) != null) {
                        responseBody.append(line);
                    }


                    return responseBody.toString();
                } catch (IOException e) {
                    throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
                }
            } else { // 오류 발생
                InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
                try (BufferedReader lineReader = new BufferedReader(streamReader)) {
                    StringBuilder responseBody = new StringBuilder();


                    String line;
                    while ((line = lineReader.readLine()) != null) {
                        responseBody.append(line);
                    }


                    return responseBody.toString();
                } catch (IOException e) {
                    throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }



}
