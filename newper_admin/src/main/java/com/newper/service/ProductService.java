package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GRank;
import com.newper.dto.ParamMap;
import com.newper.entity.Category;
import com.newper.entity.Company;
import com.newper.entity.GoodsStock;
import com.newper.entity.Product;
import com.newper.mapper.ProductMapper;
import com.newper.repository.CategoryRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.GoodsStockRepo;
import com.newper.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CompanyRepo companyRepo;
    private final GoodsStockRepo goodsStockRepo;

    private final ProductMapper productMapper;

    @Transactional
    public int productSave(ParamMap paramMap, MultipartFile P_THUMB_FILE1, MultipartFile P_THUMB_FILE2, MultipartFile P_THUMB_FILE3, MultipartFile P_THUMB_FILE4, MultipartFile P_THUMB_FILE5, MultipartFile P_THUMB_FILE6){
        Product product = paramMap.mapParam(Product.class);
        System.out.println("p_cost : " + product.getPCost());
        String p_code = productMapper.selectProductByListPcode();
        String code = "product-";
        if (p_code == null) {
            code = code + "001";
        } else {
            int no = Integer.parseInt(p_code.replace(code, "").toString());
            String codeNo = String.format("%03d", no + 1);
            code = code + codeNo;
        }
        product.setPCode(code);

        if(!paramMap.get("P_CATE_IDX").equals("")){
            Category category = categoryRepo.findById(paramMap.getInt("P_CATE_IDX")).get();
            product.setCategory(category);
        }
        if(!paramMap.get("P_CATE_IDX2").equals("")){
            Category brand = categoryRepo.findById(paramMap.getInt("P_CATE_IDX2")).get();
            product.setBrand(brand);
        }
        if(!paramMap.get("P_COM_IDX").equals("")){
            Company store = companyRepo.findById(paramMap.getInt("P_COM_IDX")).get();
            product.setStoreName(store);
        }
        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company manufacture = companyRepo.findById(paramMap.getInt("P_COM_IDX2")).get();
            product.setManufactureName(manufacture);
        }
        if(!paramMap.get("P_COM_IDX3").equals("")){
            Company afterService = companyRepo.findById(paramMap.getInt("P_COM_IDX3")).get();
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
        product.setPCode(ori.getPCode());
        if(!paramMap.get("P_CATE_IDX").equals("")){
            Category category = categoryRepo.findById(paramMap.getInt("P_CATE_IDX")).get();
            product.setCategory(category);
        }
        if(!paramMap.get("P_CATE_IDX2").equals("")){
            Category brand = categoryRepo.findById(paramMap.getInt("P_CATE_IDX2")).get();
            product.setBrand(brand);
        }
        if(!paramMap.get("P_COM_IDX").equals("")){
            Company store = companyRepo.findById(paramMap.getInt("P_COM_IDX")).get();
            product.setStoreName(store);
        }
        if(!paramMap.get("P_COM_IDX2").equals("")){
            Company manufacture = companyRepo.findById(paramMap.getInt("P_COM_IDX2")).get();
            product.setManufactureName(manufacture);
        }
        if(!paramMap.get("P_COM_IDX3").equals("")){
            Company afterService = companyRepo.findById(paramMap.getInt("P_COM_IDX3")).get();
            product.setAfterServiceName(afterService);
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

        productRepo.save(product);


        return product.getPIdx();
    }

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
            Product product = productRepo.findById(paramMap.getInt("P_IDX")).get();
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
        String gs_code = productMapper.selectGoodsStockByListGsCode();

        if(!paramMap.get("P_IDX").equals("")){
            Product product = productRepo.findById(paramMap.getInt("P_IDX")).get();
            goodsStock.setProduct(product);
        }

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

        goodsStock.setGsThumbFile1(thumbFilePath1);
        goodsStock.setGsThumbFile2(thumbFilePath2);
        goodsStock.setGsThumbFile3(thumbFilePath3);

        goodsStock.setGsThumbFileName1(GS_THUMB_FILE_NAME1);
        goodsStock.setGsThumbFileName2(GS_THUMB_FILE_NAME2);
        goodsStock.setGsThumbFileName3(GS_THUMB_FILE_NAME3);

        goodsStock.setProduct(ori.getProduct());
        goodsStock.setSpec(ori.getSpec());
        goodsStock.setGsCode(ori.getGsCode());
        goodsStock.setGsSale(ori.getGsSale());
        goodsStock.setGsRank(ori.getGsRank());
        goodsStock.setGsOption(ori.getGsOption());
        goodsStock.setGsOriginalPrice(ori.getGsOriginalPrice());
        goodsStock.setGsStock(ori.getGsStock());
        goodsStock.setGsOutStock(ori.getGsOutStock());
        goodsStock.setGsSafeStock(ori.getGsSafeStock());

        goodsStockRepo.save(goodsStock);


        return goodsStock.getGsIdx();
    }



}
