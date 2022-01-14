package com.smart.ap.common.component;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpClientComponent {

	private RequestConfig requestConfig = RequestConfig.custom()
										.setConnectionRequestTimeout(20 * 1000)
										.setSocketTimeout(20 * 1000)
										.setConnectTimeout(20 * 1000)
										.build();

	/**
	 * HttpClient Get
	 *
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public Map<String, Object> get(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			List<NameValuePair> paramList = convertParam(params);

			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			URI uri = new URIBuilder(domain + url).addParameters(paramList).build();
			HttpGet httpGet = new HttpGet(uri);

			for (String key : header.keySet()) {
				httpGet.setHeader(key, header.get(key).toString());
			}

			HttpResponse response = httpClient.execute(httpGet);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * HttpClient Post(parameters)
	 *
	 * @param url
	 * @param contentType
	 * @param data
	 * @return
	 */
	public Map<String, Object> post(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			HttpPost httpPost = new HttpPost(domain + url);

			for (String key : header.keySet()) {
				httpPost.setHeader(key, header.get(key).toString());
			}

			List<NameValuePair> paramList = convertParam(params);
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPost);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * HttpClient put(parameters)
	 *
	 * @param url
	 * @param contentType
	 * @param data
	 * @return
	 */
	public Map<String, Object> put(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			HttpPut httpPut = new HttpPut(domain + url);

			for (String key : header.keySet()) {
				httpPut.setHeader(key, header.get(key).toString());
			}

			List<NameValuePair> paramList = convertParam(params);
			httpPut.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPut);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * HttpClient Delete
	 *
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public Map<String, Object> delete(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			List<NameValuePair> paramList = convertParam(params);

			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			URI uri = new URIBuilder(domain + url).addParameters(paramList).build();
			HttpDelete httpDelete = new HttpDelete(uri);

			for (String key : header.keySet()) {
				httpDelete.setHeader(key, header.get(key).toString());
			}

			HttpResponse response = httpClient.execute(httpDelete);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * map to NameValuePairList
	 *
	 * @param param
	 * @return
	 */
	public List<NameValuePair> convertParam(Map<String, Object> param) {
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		for (String key : param.keySet()) {
			data.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
		}
		return data;
	}

}
