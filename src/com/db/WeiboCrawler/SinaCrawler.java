package com.db.WeiboCrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

public class SinaCrawler extends BreadthCrawler {

	private String path = "./download/weibo.txt";
	private File file = new File(path);
	private static String uidOneTime;
	private static String midOneTime;
	private static String textOneTime;

	public SinaCrawler() {
		setUseragent("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:26.0) Gecko/20100101 Firefox/26.0");
		setCookie("gsid_CTandWM=4uBQ655b19hbTZYOZv3w8mjmO1x;");
	}

	@Override
	public void visit(Page page) {
		Elements divs = (Elements) page.getDoc().select("div.c");
		for (Element div : divs) {
			// show(div);
			String text = div.text();
			try {
				getReviewData(text);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(div.text());
		}
	}

	public void getReviewData(String text) throws ParseException,
			UnknownHostException {
		String[] diff = text.split(" ");
		if (diff.length >= 9) {
			if (diff[2].equals(" 举报")) {
				setReviewData(diff);
			}
		}
	}

	public void setReviewData(String[] diff) throws ParseException,
			UnknownHostException {
		ReviewerData rData = new ReviewerData();
		rData.setUid(uidOneTime);
		rData.setMid(midOneTime);
		rData.setText(textOneTime);
		rData.setName(diff[0]);
		rData.setComment(diff[1].trim());
		int like = Integer.parseInt(diff[4].trim().substring(2, 3));
		rData.setLike(like);
		rData.setTime(getDate(diff));
		getMongoStatus gStatus = new getMongoStatus();
		gStatus.updateToDB(diff[0],rData);
	}

	@SuppressWarnings("deprecation")
	public Date getDate(String[] diff) throws ParseException {
		SimpleDateFormat sFormat = new SimpleDateFormat("MM月dd日HH:mm");
		StringBuilder date = new StringBuilder();
		if (diff[7].equals("今天")) {
			Date now = new Date();
			date.append(now.getMonth() + 1 + "月" + now.getDate() + "日");
		} else {
			date.append(diff[7].trim());
		}
		date.append(diff[8].trim().substring(0, 5));
		Date now = sFormat.parse(date.toString());
		return now;
	}

	public void show(Element div) {
		try {
			print(div);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(Element div) throws IOException {
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter bWriter = new BufferedWriter(writer);
		bWriter.write(div.text());
		bWriter.write("\r\n");
		bWriter.close();
		writer.close();
	}

	public void getSeed(String Uid, String Mid, String text, int Comment_Count)
			throws Exception {
		Config.topN = 0;
		SinaCrawler wCrawler = new SinaCrawler();
		SinaCrawler.uidOneTime = Uid;
		SinaCrawler.midOneTime = Mid;
		SinaCrawler.textOneTime = text;

//		wCrawler.addSeed("http://weibo.cn/comment/" + Mid + "?uid=" + Uid
//				+ "&rl=1#cmtfrm");
		for (int i = 2; i <= (Comment_Count / 10) + 1; i++) {
			wCrawler.addSeed("http://weibo.cn/comment/" + Mid + "?uid=" + Uid
					+ "&rl=1&page=" + i);
		}
		wCrawler.addRegex(".*");
		wCrawler.setThreads(10);
		wCrawler.start(1);
	}
}
