package com.smart.ap.common.component;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

@Component
public class MarshalComponent {

	/**
	 * Object to XML
	 *
	 * @param obj
	 * @return
	 */
	public StringWriter marshaller(Object obj) {
		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();

			// 보기 좋게 출력해주는 옵션
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


	        // Write XML to StringWriter
	        marshaller.marshal(obj, stringWriter);
			// 표준 출력으로 결과를 보여준다.
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return stringWriter;
	}

	/**
	 * XML to Object
	 * @param obj
	 * @return
	 */
	public Object unMarshaller(Object obj) {
		Object stockPrice = null;
		try {
			String xml = "";
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass()); // JAXB Context 생성
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller(); // Unmarshaller Object 생성
			stockPrice = (Object) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메소드 호출
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return stockPrice;
	}

}
