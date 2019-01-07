/**
 * https://github.com/yasserg/crawler4j 의 샘플 코드를 약간 수정한 것임
 */
package kr.ac.sungkyul.Controller.Impl;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import kr.ac.sungkyul.Utils.BasicCrawler;

public class BasicCrawlController {

	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "C:/Crawl";
		int numberOfCrawlers = 10000;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(10);
		config.setMaxDepthOfCrawling(32766);
		config.setMaxPagesToFetch(1000000000);
		config.setIncludeBinaryContentInCrawling(false);
		config.setResumableCrawling(false);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed("http://www.mangoplate.com");
		BasicCrawler.configure(crawlStorageFolder);
		controller.start(BasicCrawler.class, numberOfCrawlers);
	}
}