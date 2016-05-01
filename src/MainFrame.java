import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame {
	 JTextField textField;
	 JTable table1;
	 JTable table;
	 JLabel analysis;
	   public MainFrame() {
		      // Get the content-pane of this JFrame, which is a java.awt.Container
		      // All operations, such as setLayout() and add() operate on the content-pane 
		      Container cp = this.getContentPane(); 
		      FlowLayout layout = new FlowLayout();
		      cp.setLayout(layout);
		      JLabel label = new JLabel("Stock symbol to check:");
		      cp.add(label);
		      textField = new JTextField(10);
		      textField.setSize(500, 20);
		      textField.setMinimumSize(new Dimension(50, 20));
		      textField.setToolTipText("Ticker symbol (i.e. AAPL)");
		      cp.add(textField);
		      JButton  CheckButton= new JButton("Check");

				//Creates a table
				
				String[] columnNames = {"Indicator",
		                "Value"};

				String[] columnNames1 = {"Article title",
		        "Date published","URL"};
				
				table = new JTable();
				DefaultTableModel contactTableModel = (DefaultTableModel) table
				            .getModel();
				contactTableModel = new DefaultTableModel() {
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				table.setModel(contactTableModel);
				contactTableModel.setColumnIdentifiers(columnNames);
			    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				JScrollPane scrollPane = new JScrollPane(table);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(160);
				table.getColumnModel().getColumn(1).setPreferredWidth(90);
				//table.setFillsViewportHeight(true);
				table1 = new JTable();
				
				DefaultTableModel contactTableModel1 = (DefaultTableModel) table1
			            .getModel();
				contactTableModel1 = new DefaultTableModel() {
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				table1.setModel(contactTableModel1);
				contactTableModel1.setColumnIdentifiers(columnNames1);
				//table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table1.setPreferredSize(new Dimension(520,400));
				table1.getColumnModel().getColumn(0).setPreferredWidth(400);
				table1.getColumnModel().getColumn(1).setPreferredWidth(120);
				table1.addMouseListener(new MouseAdapter() {
				    public void mousePressed(MouseEvent me) {
				        JTable table =(JTable) me.getSource();
				        Point p = me.getPoint();
				        int row = table.rowAtPoint(p);
				        if (me.getClickCount() == 2) {
				        	Object selectedObject = (Object) table1.getModel().getValueAt(row, 2);
				        	try{
				        	URL url = new URL(selectedObject.toString());
				        	 openWebpage(url.toURI());
				        	}catch(Exception ex)
				        	{
				        		ex.printStackTrace();
				        	}
				            // your valueChanged overridden method 
				        }
				    }
				});
				
				JScrollPane scrollPane1 = new JScrollPane(table1);
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout());
				scrollPane.setPreferredSize(new Dimension(250, 300));
				scrollPane1.setPreferredSize(new Dimension(520, 300));
				panel.add(scrollPane);
				panel.add(scrollPane1);
				cp.add(CheckButton);
				cp.add(panel);
		      CheckButton.addActionListener(new ActionListener() {
		          public void actionPerformed(ActionEvent ae){
		              String ticker = textField.getText();
		              DataCollector dc = new DataCollector();
		              Map <String, String> data = dc.getData(ticker);
		              DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		              tableModel.setRowCount(0);
		              String[] dataA = new String[2];
		              dataA[0] = "Name";
		              dataA[1] = data.get("Name").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Symbol";
		              dataA[1] = data.get("symbol").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Stock Exchange";
		              dataA[1] = data.get("StockExchange").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Market Capitalization (Millions)";
		              dataA[1] = data.get("MarketCap").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Ask";
		              dataA[1] = data.get("Ask").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Bid";
		              dataA[1] = data.get("Bid").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Average Day Volume";
		              dataA[1] = data.get("AverageDayVolume").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Book Value";
		              dataA[1] = data.get("BookValue").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "P/E Ratio";
		              dataA[1] = data.get("PERatio2").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "P/E Ratio RT";
		              dataA[1] = data.get("PERatioRT").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Earnings Per Share";
		              dataA[1] = data.get("EarningsPerShare").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Price/Book ratio";
		              dataA[1] = data.get("priceBook").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Price/Sales ratio";
		              dataA[1] = data.get("PriceSales").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Dividend Yield";
		              dataA[1] = data.get("DividendYield").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Price Change RT";
		              dataA[1] = data.get("PriceChangeRT").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Price Change Percent";
		              dataA[1] = data.get("PriceChangePercent").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "52 week low";
		              dataA[1] = data.get("52weekLow").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "52 week high";
		              dataA[1] = data.get("52weekhigh").replace("\"", "");
		              tableModel.addRow(dataA);
		              dataA[0] = "Change From 52 week low";
		              dataA[1] = data.get("ChangeFromYearLow").replace("\"", "");
		              tableModel.addRow(dataA);
		              tableModel.fireTableDataChanged();
		              String prediction = dc.Classify();
		              Color c = Color.gray;
		              String text = "";
		              if(prediction.equals("Good"))
		              {
		            	  text = "Buy";
		            	  c = Color.GREEN;
		              }
		              else{
		            	  text = "Sell";
		            	  c = Color.RED;
		              }
		              analysis.setText(text);
		              analysis.setBackground(c);
		              analysis.setOpaque(true);
		              LinkedList<NewsItem> news = dc.obtainNews(ticker);
		              System.out.println("Success!");
		              tableModel = (DefaultTableModel) table1.getModel();
		              tableModel.setRowCount(0);
		              for(int i = 0;i<news.size();i++)
		              {
		            	  NewsItem ni = news.get(i);
		            	  String[] dataB = new String[3];
		            	  dataB[0] = ni.Title;
		            	  dataB[1] = ni.DatePublished;
		            	  dataB[2] = ni.URL;
			              tableModel.addRow(dataB);
		            	  
		              }
		              
		              tableModel.fireTableDataChanged();
		          }
		         });
		      	JLabel analText = new JLabel("Machine learning analyst says:");
		   		analysis = new JLabel("");
		   		analysis.setBackground(Color.gray);
		   		analysis.setOpaque(true);
		   		cp.add(analText);
		   		cp.add(analysis);
		   }
	   
	   public static void openWebpage(URI uri) {
		    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		        try {
		            desktop.browse(uri);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}
	   		
}
