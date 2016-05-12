# FinAnalyzer
**Motto:** Making investing easy!

**Short description:** Tool for technical analysis of financial data about companies indexed on the stockmarket using machine learning

##Introduction

FinAnalyzer is a tool and library that is supposed to make investment easy. However, it should not be used as only tool for financial analysis and we are not liable of any profit or loss made by users (see also disclaimer section). The aim of the tool is to provide easy access to the openly available information about companies indexed on the stock market and to provide an platform for analysis of these financial information. Two areas of analysis are identified - **technical** and **event** based. 

Under technical analysis we consider technical indicators and values such as price, financial ratios (PE ration, Book/Price ration, etc.)

Under event driven analysis we identify events that are publicized by news articles. 

Currently, the tool focuses on long term investment and price changes. 

Analysis of technical indicator and prediction whether the price will grow (at least 10% over one year period) can be done by machine learning and I have already published a research on it (http://arxiv.org/abs/1603.00751).

Analysis of news can be also important part of the decision and it could be useful to analyze automatically sentiment of the titles.  

![alt tag](https://github.com/nikolamilosevic86/FinAnalyzer/blob/master/img/Screen1.png)
##How it works?

At the moment the FinAnalizer obtains data from the API provided by Yahoo! Finance. News titles are obtained by using rss feed from the Yahoo! Finance as well. To the input field should be imputed symbol of the company at the stockmarket (in the future, company name will be possible as well). 

We have automatic analyst of technical indicators that is based on Random Forests machine learning algorithm. Analyst based on 8 indicators and machine learning should be able to predict whether a price will move up at least 10% (he will say "Buy" in that case) over the period of one year in 71% of cases. Indicators that are used by analyst are:
* Market Capitalization
* Book Value
* Dividend Yield
* Earnings Per Share
* P/E Ratio
* Price/Book Ratio
* Price/Sales Ratio
* Current stock price 

As we said we obtain news from the feed at Yahoo! Finance. They can be clicked and browser will fully open them.

##As library
Most of the actions application can perform can be used in library settings. Class DataCollector provides means for collecting currently 20 values:
* Company Name (Name)
* Symbol (symbol)
* Stock exchange on which it is traded (StockExchange)
* Market Capitalization (MarketCap)
* Ask (Ask)
* Bid(Bid)
* Average daily volume (AverageDayVolume)
* P/E Ratio (PERatio2)
* Real time P/E Ratio (PERatioRT)
* Dividend Yield (DividendYield)
* Price/Book ratio (priceBook)
* Earnings per share (EarningsPerShare)
* Price/Sales ratio (PriceSales)
* Book Value (BookValue)
* 52 weeks low price (52weekLow)
* 52 week high price ("52weekhigh")
* Change from 52 week low (ChangeFromYearLow)
* Price change in percentages (PriceChangePercent)
* Price change real time (PriceChangeRT)

The values are returned as HashMap from the function with the following signature: 
`public Map<String, String> getData(String ticker)`

News can be retrieved using the following function:
`public LinkedList<NewsItem> obtainNews(String ticker)`

At the end classification using our model can be done using following function after the data is obtained using getData:
`public String Classify()`

##Mailing list
- To post on your mailing list, simply send email to finanalyzer@freelists.org.

- Subscribers can join your list by sending email to finanalyzer-request@freelists.org with 'subscribe' in the Subject field OR by visiting your list page at http://www.freelists.org/list/finanalyzer. 

- Once subscribed, please introduce yourself and your interests.


##Reference
Milosevic, Nikola. "Equity forecast: Predicting long term stock price movement using machine learning." arXiv preprint arXiv:1603.00751 (2016). http://arxiv.org/abs/1603.00751

##Contact
If you have any questions regarding the project, paper or would like to contribute, do not hesitate to contact me over the email: nikola.milosevic86 [at] gmail [dot] com.

##Disclaimer
The information appearing within this software do not constitute financial advice and are provided for general information purposes only. No warranty, whether express or implied is given in relation to such materials. We can not be held liable for any technical, financial or other errors or omissions within the information provided withing this software. We accept no liability whatsoever for any loss or damage arising from the use of this software or our web content, whether or not such loss or damage is caused by reason of negligence.



