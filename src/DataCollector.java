import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weka.classifiers.misc.InputMappedClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DataCollector {
	Map<String, String> map;
	InputMappedClassifier classifier = new InputMappedClassifier();

	public DataCollector() {
		map = new HashMap<String, String>();
		try{
		classifier.setModelPath("Models/RandomForestsTechAnalysis.model");
		classifier.setTrim(true);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public Map<String, String> getData(String ticker) {
		try {
			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ ticker + "&f=b4c1jj1j5k2r2yaba2enrsp6xkp5");
			Scanner s = new Scanner(url.openStream());

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				map.put("BookValue", values[0]);
				map.put("PriceChangeRT",values[1]);
				map.put("52weekLow",values[2]); // 52 week low
				double val = 0;
				if(!values[3].equals("N/A")){
				String letter = values[3].substring(values[3].length()-1);
				String value = values[3].substring(0,values[3].length()-1);
				val = Float.parseFloat(value);
				if(letter.equals("B"))
				{
					val = val*1000;
				}
				}
				map.put("MarketCap",val+"");
				map.put("ChangeFromYearLow",values[4]);
				map.put("PriceChangePercent",values[5]);
				map.put("PERatioRT",values[6]);
				map.put("DividendYield",values[7]);
				map.put("Ask", values[8]);
				map.put("Bid",values[9]);
				map.put("AverageDayVolume",values[10]);
				map.put("EarningsPerShare" ,values[11]);
				map.put("Name",values[12]);
				map.put("PERatio2",values[13]);
				map.put("symbol",values[14]);
				map.put("priceBook",values[15]);
				map.put("StockExchange", values[16]);
				map.put("52weekhigh",values[17]); // 52 week high
				map.put("PriceSales",values[18]); // add this to database
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return map;
	}
	
	public String Classify(){
		
		String prediction = "";
		try{
		Instances ins = null;		
		Attribute BookValue = new Attribute("book_value");
		Attribute MarketCap = new Attribute("market_cap");
		Attribute DividendYield = new Attribute("DIVIDEND_YIELD");
		Attribute EarningsPerShare = new Attribute("BEST_EPS");
		Attribute PERatio = new Attribute("PE_Ratio");
		Attribute priceBook = new Attribute(
				"PX_TO_BOOK_RATIO");
		Attribute PriceSales = new Attribute("PX_TO_SALES_RATIO");
		Attribute historyPrice = new Attribute(
				"history_price");
		
		FastVector fvClassVal = new FastVector(2);
		// AdverseEvent,InclusionExclusion,DontCare,BaselineCharacteristic
		fvClassVal.addElement("Good");
		fvClassVal.addElement("Bad");
		Attribute ClassAttribute = new Attribute("label", fvClassVal);
		// Declare the feature vector
		FastVector fvWekaAttributes = new FastVector(9);

		fvWekaAttributes.addElement(BookValue);
		fvWekaAttributes.addElement(MarketCap);
		fvWekaAttributes.addElement(DividendYield);
		fvWekaAttributes.addElement(EarningsPerShare);
		fvWekaAttributes.addElement(PERatio);
		fvWekaAttributes.addElement(priceBook);
		fvWekaAttributes.addElement(PriceSales);
		fvWekaAttributes.addElement(historyPrice);
		fvWekaAttributes.addElement(ClassAttribute);
		Instances Instances = new Instances("Rel", fvWekaAttributes, 0);

		Instance iExample = new DenseInstance(9);
		Attribute attribute = (Attribute) fvWekaAttributes.elementAt(0);
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), map.get("BookValue").equals("N/A")?-9999:Float.parseFloat(map.get("BookValue")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(1),
				map.get("MarketCap").equals("N/A")?-9999:Float.parseFloat(map.get("MarketCap")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(2),
				map.get("DividendYield").equals("N/A")?-9999:Float.parseFloat(map.get("DividendYield")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(3),
				map.get("EarningsPerShare").equals("N/A")?-9999:Float.parseFloat(map.get("EarningsPerShare")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(4),
				map.get("PERatio2").equals("N/A")?-9999:Float.parseFloat(map.get("PERatio2")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(5),
				map.get("priceBook").equals("N/A")?-9999:Float.parseFloat(map.get("priceBook")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(6),
				map.get("PriceSales").equals("N/A")?-9999:Float.parseFloat(map.get("PriceSales")));
		iExample.setValue((Attribute) fvWekaAttributes.elementAt(7),
				map.get("Ask").equals("N/A")?-9999:Float.parseFloat(map.get("Ask")));
		Instances.add(iExample);
		Instances.setClassIndex(8);
		Instance inst = Instances.firstInstance();

		double result = classifier.classifyInstance(Instances
				.firstInstance());
		Instances.firstInstance().setClassValue(result);
		prediction = Instances.firstInstance().classAttribute()
				.value((int) result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return  prediction;
	}
	
	public LinkedList<NewsItem> obtainNews(String ticker){
		
		LinkedList<NewsItem> newslist = new LinkedList<NewsItem>();
		try{
		String urlString = "https://feeds.finance.yahoo.com/rss/2.0/headline?lang=en-US&s="+ticker;
		URL url = new URL(urlString);
		Scanner s = new Scanner(url.openStream());
		String xml = "";
		while(s.hasNextLine()){
			xml+=s.nextLine();
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    Document doc = builder.parse(is);
		NodeList items = doc.getElementsByTagName("item");
		for(int i=0;i<items.getLength();i++)
		{
			NodeList children = items.item(i).getChildNodes();
			NewsItem item = new NewsItem();
			for(int j = 0;j<children.getLength();j++)
			{
				switch(j){
				case 9:
					item.Title = children.item(j).getTextContent();
					break;
				case 5:
					item.URL =  children.item(j).getTextContent();
					break;
				case 1://description
					item.Description = children.item(j).getTextContent();
					break;
				case 3://guid
					break;
				case 7:
					item.DatePublished =  children.item(j).getTextContent();
					break;
				}
			}
			newslist.add(item);
		}
		
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return newslist;
	}

}
