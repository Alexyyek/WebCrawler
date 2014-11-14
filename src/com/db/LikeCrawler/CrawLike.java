package com.db.LikeCrawler;

import java.net.UnknownHostException;
import java.text.ParseException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

public class CrawLike extends BreadthCrawler {
	public CrawLike() {
		setUseragent("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:26.0) Gecko/20100101 Firefox/26.0");
		setCookie("gsid_CTandWM=4u6272a017gTDEKOnV68SmjmO1x;");
	}

	public void visit(Page page) {
		Elements divs = page.getDoc().select("div.c");
		for (Element div : divs) {
			try {
				getReviewData(div.text());
				System.out.println(div.text());
			} catch (UnknownHostException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void getReviewData(String text) throws ParseException,
			UnknownHostException {
		String[] diff = text.split(" ");
		String name = diff[0];
		int like;
		for (int i = 1; i < diff.length; i++) {
			if (diff[i].substring(0, 1).equals("ÔÞ")) {
				String str = diff[i].replaceAll("[\\pP¡®¡¯¡°¡±]", "");
				like = Integer.parseInt(str.substring(1));
				setReviewData(name, like);
			}
		}
	}

	public void setReviewData(String name, int like) throws ParseException,
			UnknownHostException {
		getLike gLike = new getLike();
		gLike.updateToDB(name, like);
	}

	public void getPage(int page, String mid, String uid) throws Exception {
		Config.topN = 0;
		CrawLike cLike = new CrawLike();
		cLike.addSeed("http://weibo.cn/comment/" + mid + "?uid=" + uid
				+ "&rl=2#cmtfrm");
		for (int i = 1; i <= page; i++) {
			cLike.addSeed("http://weibo.cn/comment/" + mid + "?uid=" + uid
					+ "&rl=2&page=" + i);
		}
		
		cLike.addRegex(".*");
		cLike.setThreads(10);
		cLike.start(1);
	}
}
