package com.cbsinc.cms.card;

import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.RPCConstants;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.Bean;
import org.apache.soap.util.xml.DOMUtils;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.QName;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Paymentinfoserializer implements Deserializer {
	public Bean unmarshall(String inScopeEncStyle, QName elementType, Node src, XMLJavaMappingRegistry xjmr,
			SOAPContext ctx) throws IllegalArgumentException {
		Element svcElement = (Element) src;
		Element tempEl = DOMUtils.getFirstChildElement(svcElement);
		Paymentinfo svc = new Paymentinfo();

		while (tempEl != null) {
			String tagName = tempEl.getTagName();

			if (tagName.equals("ordernumber")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setOrdernumber((String) param.getValue());
			} else if (tagName.equals("total")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setTotal((String) param.getValue());
			} else if (tagName.equals("status")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setStatus((String) param.getValue());
			} else if (tagName.equals("comment")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setComment((String) param.getValue());
			} else if (tagName.equals("response_code")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setResponseCode((String) param.getValue());
			} else if (tagName.equals("country")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCountry((String) param.getValue());
			} else if (tagName.equals("cardholder")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCardholder((String) param.getValue());
			} else if (tagName.equals("cardsubtype")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCardsubtype((String) param.getValue());
			} else if (tagName.equals("cvc2")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCvc2((String) param.getValue());
			} else if (tagName.equals("approvalcode")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setApprovalcode((String) param.getValue());
			} else if (tagName.equals("rate")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setRate((String) param.getValue());
			} else if (tagName.equals("ipaddress")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setIpaddress((String) param.getValue());
			} else if (tagName.equals("recommendation")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setRecommendation((String) param.getValue());
			} else if (tagName.equals("message")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setMessage((String) param.getValue());
			} else if (tagName.equals("date")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setDate((String) param.getValue());
			} else if (tagName.equals("currency")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCurrency((String) param.getValue());
			} else if (tagName.equals("cardtype")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCardtype((String) param.getValue());
			} else if (tagName.equals("cardnumber")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCardnumber((String) param.getValue());
			} else if (tagName.equals("lastname")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setLastname((String) param.getValue());
			} else if (tagName.equals("firstname")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setFirstname((String) param.getValue());
			} else if (tagName.equals("middlename")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setMiddlename((String) param.getValue());
			} else if (tagName.equals("address")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setAddress((String) param.getValue());
			} else if (tagName.equals("email")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setEmail((String) param.getValue());
			} else if (tagName.equals("protocoltypename")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setProtocoltypename((String) param.getValue());
			} else if (tagName.equals("billnumber")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setBillnumber((String) param.getValue());
			} else if (tagName.equals("bankname")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setBankname((String) param.getValue());
			} else if (tagName.equals("error_code")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setErrorCode((String) param.getValue());
			} else if (tagName.equals("error_comment")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setErrorComment((String) param.getValue());
			}

			tempEl = DOMUtils.getNextSiblingElement(tempEl);
		}

		return new Bean(CardInfo.class, svc);
	}
}
