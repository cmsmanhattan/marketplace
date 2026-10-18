package com.cbsinc.cms;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.PageXsltView;
import com.cbsinc.cms.annotations.Scope;
import com.cbsinc.cms.annotations.Type;

@PageModel(Id = "purchasesBeanId", scope = Scope.SESSION)
@PageXsltView(jspName = "Purchases.jsp", xsltName = "purchases.xsl", responseType = Type.XML)
public class PurchasesBean extends OrderListBean {

	private static final long serialVersionUID = 1L;

}
