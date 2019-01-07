/**
 * https://github.com/yasserg/crawler4j 의 샘플 코드를 약간 수정한 것임
 */
package kr.ac.sungkyul.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class BasicCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
    private static String storageDir;
    public static void configure(String dir) {
    	storageDir = dir;
    }
    
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            return false;
        }
        return href.contains("www.mangoplate.com/restaurants/");
    }
    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        System.err.println("Docid: " + docid);
        System.err.println("URL: " + url);
        System.err.println("Domain: " + domain);
        System.err.println("Sub-domain: " + subDomain);
        System.err.println("Path: " + path);
        System.err.println("Parent page: " + parentUrl);
        System.err.println("Anchor text: " + anchor);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.err.println("Text length: " + text.length());
            System.err.println("Html length: " + html.length());
            System.err.println("Number of outgoing links: " + links.size());
            
            //save the html file
            saveFile(html, docid, htmlParseData.getTitle().replaceAll("\\p{Punct}", ""), "html");
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
        	System.err.println("Response headers:");
            for (Header header : responseHeaders) {
            	System.err.println(String.format("\t%s: %s", header.getName(), header.getValue()));
            }
        }

        System.err.println("=============");
    }
    
    public void saveFile(String content, int docId, String title, String ext) {
    	String fname = storageDir + File.separator + docId + "_" + title + "." + ext;
    	BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname), "UTF8"));
	    	writer.write(content);
	    	writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
