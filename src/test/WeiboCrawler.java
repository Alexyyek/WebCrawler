package test;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboCrawler extends BreadthCrawler {

	private String path = "./download/spam.txt";
	
    public WeiboCrawler() {
        setUseragent("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:26.0) Gecko/20100101 Firefox/26.0");
        setCookie("gsid_CTandWM=4u6272a017gTDEKOnV68SmjmO1x;");
    }

    @Override
    public void visit(Page page) {
        Elements divs = page.getDoc().select("div.c");
        for (Element div : divs) {
        	show(div);
            System.out.println(div.text());
        }
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
		FileWriter writer = new FileWriter(new File(path), true);
		BufferedWriter bWriter = new BufferedWriter(writer);
		bWriter.write(div.text());
		bWriter.write("\r\n");
		bWriter.close();
		writer.close();
	}
	
    public static void main(String[] args) throws IOException, Exception {
        Config.topN = 0;
        WeiboCrawler crawler=new WeiboCrawler();
        
//        crawler.addSeed("http://weibo.cn/comment/BnBTKuClI?uid=1314637182&rl=2#cmtfrm");
        for (int i = 2; i <= 5; i++) {
            crawler.addSeed("http://weibo.cn/1191258123/fans?vt=4&page=" + i);
        }
        crawler.addRegex(".*");
        crawler.setThreads(10);
        crawler.start(1);

    }
}
