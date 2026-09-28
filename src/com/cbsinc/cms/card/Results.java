package com.cbsinc.cms.card;

import java.net.URL;
import java.util.Vector;

import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.ArraySerializer;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.transport.http.SOAPHTTPConnection;
import org.apache.soap.util.xml.QName;

public class Results {
	public static void main(String[] args) throws Exception {

		String encodingStyleURI = Constants.NS_URI_SOAP_ENC;

		URL url = new URL("http://secure.assist.ru/results/results.cfm?format=5");

		String OBJECT_URI = "http://www.assist.ru/type/";

		System.out.println(encodingStyleURI);
		System.out.println(Constants.NS_URI_SOAP_ENC);
		System.out.println(Constants.NS_URI_CURRENT_SCHEMA_XSI);
		System.out.println(Constants.NS_URI_CURRENT_SCHEMA_XSD);

		SOAPMappingRegistry smr = new SOAPMappingRegistry();

		ArraySerializer sd = new ArraySerializer();
		smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName(OBJECT_URI, "return"), null, null, sd);

		Paymentinfoserializer sd1 = new Paymentinfoserializer();
		smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName(OBJECT_URI, "SOAPStruct"), Paymentinfo.class, null, sd1);

		// create the transport and set parameters
		SOAPHTTPConnection st = new SOAPHTTPConnection();

		// Build the call.
		Call call = new Call();
		call.setTargetObjectURI("http://www.assist.ru/message/");
		call.setSOAPTransport(st);
		call.setSOAPMappingRegistry(smr);

		call.setMethodName("GetPaymentsResult");
		call.setEncodingStyleURI(encodingStyleURI);

		Vector params = new Vector();
		params.addElement(new Parameter("shop_id", Integer.class, "84473", null));
		params.addElement(new Parameter("login", String.class, "gvidon", null));
		params.addElement(new Parameter("password", String.class, "231003", null));
		params.addElement(new Parameter("shopordernumber", String.class, "193", null));
		params.addElement(new Parameter("success", String.class, "2", null));
		params.addElement(new Parameter("startday", String.class, "24", null));
		params.addElement(new Parameter("startmonth", String.class, "01", null));
		params.addElement(new Parameter("startyear", String.class, "2006", null));
		params.addElement(new Parameter("endday", String.class, "23", null));
		params.addElement(new Parameter("endmonth", String.class, "01", null));
		params.addElement(new Parameter("endyear", String.class, "2006", null));
		params.addElement(new Parameter("meantype", String.class, "0", null));
		params.addElement(new Parameter("paymenttype", String.class, "16", null));
		params.addElement(new Parameter("english", String.class, "0", null));

		call.setParams(params);

		// make the call: note that the action URI is empty because the
		// XML-SOAP rpc router does not need this. This may change in the
		// future.
		Response resp = call.invoke(/* router URL */url, /* actionURI */"");

		// Check the response.
		if (resp.generatedFault()) {
			Fault fault = resp.getFault();
			System.out.println("Fuult: ");
			System.out.println("  Fault Code   = " + fault.getFaultCode());
			System.out.println("  Fault String = " + fault.getFaultString());
		} else {
			Parameter result = resp.getReturnValue();

			System.out.println(result.getName());

			Paymentinfo[] test = (Paymentinfo[]) result.getValue();

			for (int i = 0; i < test.length; i++) {
				System.out.println("payment");
				System.out.println("==" + test[i].getOrdernumber());
				System.out.println("==" + test[i].getResponseCode());
				System.out.println("==" + test[i].getRecommendation());
				System.out.println("==" + test[i].getMessage());
				System.out.println("==" + test[i].getComment());
				System.out.println("==" + test[i].getDate());
				System.out.println("==" + test[i].getTotal());
				System.out.println("==" + test[i].getCurrency());
				System.out.println("==" + test[i].getCardtype());
				System.out.println("==" + test[i].getCardnumber());
				System.out.println("==" + test[i].getLastname());
				System.out.println("==" + test[i].getFirstname());
				System.out.println("==" + test[i].getMiddlename());
				System.out.println("==" + test[i].getAddress());
				System.out.println("==" + test[i].getEmail());
				System.out.println("==" + test[i].getCountry());
				System.out.println("==" + test[i].getRate());
				System.out.println("==" + test[i].getApprovalcode());
				System.out.println("==" + test[i].getCardsubtype());
				System.out.println("==" + test[i].getCvc2());
				System.out.println("==" + test[i].getCardholder());
				System.out.println("==" + test[i].getIpaddress());
				System.out.println("==" + test[i].getProtocoltypename());
				System.out.println("==" + test[i].getBillnumber());
				System.out.println("==" + test[i].getBankname());
				System.out.println("==" + test[i].getStatus());
				System.out.println("==" + test[i].getErrorCode());
				System.out.println("==" + test[i].getErrorComment());
				System.out.println("");
			}

		}
	}
}
