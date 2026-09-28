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

public class RateinfoSerializer implements Deserializer {
	public Bean unmarshall(String inScopeEncStyle, QName elementType, Node src, XMLJavaMappingRegistry xjmr,
			SOAPContext ctx) throws IllegalArgumentException {
		Element svcElement = (Element) src;
		Element tempEl = DOMUtils.getFirstChildElement(svcElement);
		RateInfo svc = new RateInfo();

		while (tempEl != null) {
			String tagName = tempEl.getTagName();

			if (tagName.equals("currency")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setCurrency((String) param.getValue());
			} else if (tagName.equals("date")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setDate((String) param.getValue());
			} else if (tagName.equals("rate")) {
				Bean bean = xjmr.unmarshall(inScopeEncStyle, RPCConstants.Q_ELEM_PARAMETER, tempEl, ctx);
				Parameter param = (Parameter) bean.value;

				svc.setRate((String) param.getValue());
			}

			tempEl = DOMUtils.getNextSiblingElement(tempEl);
		}

		return new Bean(CardInfo.class, svc);
	}
}
