package com.newper.constant.basic;

import java.util.HashMap;
import java.util.Map;

/** db조회 변환용. key: column명 (snake_case) , value : enum class명(camelBack) */
public class EnumClasses {

    public Map<String,String> enumClasses(){
        Map<String,String> enumClasses = new HashMap<>();
        enumClasses.put("po_buy_product_type","PType1");
        enumClasses.put("po_sell_channel","Channel");
        enumClasses.put("gs_rank","GRank");

        //51개

        enumClasses.put("cate_display","CateDisplay");
        enumClasses.put("cate_spec","CateSpec");
        enumClasses.put("cate_type","CateType");
        enumClasses.put("cc_cal_type","CcCalType");
        enumClasses.put("cc_cycle","CcCycle");
        enumClasses.put("cc_fee_type","CcFeeType");
        enumClasses.put("cc_state","CcState");
        enumClasses.put("cc_type","CcType");
        enumClasses.put("cf_type","CfType");
        enumClasses.put("cg_state","CgState");
        enumClasses.put("channel","Channel");
        enumClasses.put("ci_insurance_state","CiInsuranceState");
        enumClasses.put("ci_type","CiType");
        enumClasses.put("com_state","ComState");
        enumClasses.put("com_type","ComType");
        enumClasses.put("cpg_discount_type","CpgDiscountType");
        enumClasses.put("cpg_state","CpgState");
        enumClasses.put("cpg_type","CpgType");
        enumClasses.put("cr_type","CrType");
        enumClasses.put("ct_type","CtType");
        enumClasses.put("cu_gender","CuGender");
        enumClasses.put("cu_rate","CuRate");
        enumClasses.put("cu_state","CuState");
        enumClasses.put("ggt_type","GgtType");
        enumClasses.put("giftg_state","GiftgState");
        enumClasses.put("gift_state","GiftState");
        enumClasses.put("g_rank","GRank");
        enumClasses.put("g_state","GState");
        enumClasses.put("g_stock_state","GStockState");
        enumClasses.put("hw_state","HwState");
        enumClasses.put("ig_state","IgState");
        enumClasses.put("loc_form","LocForm");
        enumClasses.put("loc_state","LocState");
        enumClasses.put("loc_type","LocType");
        enumClasses.put("menu_type","MenuType");
        enumClasses.put("o_location","OLocation");
        enumClasses.put("pe_state","PeState");
        enumClasses.put("pn_process","PnProcess");
        enumClasses.put("po_state","PoState");
        enumClasses.put("po_type","PoType");
        enumClasses.put("p_state","PState");
        enumClasses.put("p_type1","PType1");
        enumClasses.put("p_type2","PType2");
        enumClasses.put("p_type3","PType3");
        enumClasses.put("shop_state","ShopState");
        enumClasses.put("shop_type","ShopType");
        enumClasses.put("s_state","SState");
        enumClasses.put("s_type","SType");
        enumClasses.put("tf_type","TfType");
        enumClasses.put("u_state","UState");
        enumClasses.put("u_type","UType");
        enumClasses.put("wh_state","WhState");

        return enumClasses;
    }
}
