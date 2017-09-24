package com.mxcx.erp.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrJSearch {
	private HttpSolrServer solrServer = null;

	public SolrJSearch(String SOLR_URL) {
		solrServer = new HttpSolrServer(SOLR_URL);
		solrServer.setConnectionTimeout(5000);
	}

	public void getList(String username, Integer STATUS, Integer[] categorys) {
		// categorys = new Integer[2];
		// categorys[0]=1;
		// categorys[1]=2;
		SolrQuery query = new SolrQuery();
		// 主查�?
		query.setQuery("*:*");
		// 采用过滤器查询可以提高�?�?
		query.addFilterQuery("all:" + username);
		// query.addFilterQuery("status:" + STATUS);

		if (categorys != null) {
			if (categorys.length > 1) {
				String categoryString = "";
				for (int category : categorys) {
					categoryString += "category:" + category + " OR";
				}
				query.addFilterQuery(categoryString.substring(0,
						categoryString.length() - 2));
			} else {
				query.addFilterQuery("category:" + categorys[0]);
			}
		}
		QueryResponse response = null;
		try {
			System.out.println(query);
			response = solrServer.query(query);
		} catch (SolrServerException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			solrServer.shutdown();
		}
		SolrDocumentList docs = response.getResults();
		System.out.println("文档个数：" + docs.getNumFound());
		System.out.println("查询时间：" + response.getQTime());
	}

	public void getList1(String username, Integer STATUS, Integer[] categorys) {
		// 这里就是得到HttpSolrServer，你也可以自己封装
		SolrQuery query = new SolrQuery();
		query.setQuery("all:" + username);
		QueryResponse req;
		try {
			req = solrServer.query(query);
			System.out.println(req.getResults().getNumFound());
			long aa = req.getResults().getNumFound();
			query.setStart(1);
			query.setRows((int) aa);
			query.setHighlight(true);// 开启高亮功能
			query.setHighlightSnippets(10);
			query.addHighlightField("pname");// 高亮字段
			query.setHighlightSimplePre("<font color=\"red\">");
			query.setHighlightSimplePost("</font>");
			query.setHighlight(true).setHighlightSnippets(1);
			query.setParam("hl.fl", "all"); // 高亮字段

			req = solrServer.query(query);
			SolrDocumentList list = req.getResults();
			Map<String, Map<String, List<String>>> map = req.getHighlighting();
			for (SolrDocument doc : list) {
				System.out.print(map.get(doc.getFieldValue("id").toString()).get("all") + "  ");
				System.out.println(doc.get("id"));
			}

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String SOLR_URL = "http://127.0.0.1:8983/solr/eztpatient";
		SolrJSearch s = new SolrJSearch(SOLR_URL);
		s.getList1("(刘一  or 二)", 0, null);

	}
}
